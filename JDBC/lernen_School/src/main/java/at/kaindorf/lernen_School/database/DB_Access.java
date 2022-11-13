package at.kaindorf.lernen_School.database;

import at.kaindorf.lernen_School.pojos.Gender;
import at.kaindorf.lernen_School.pojos.Student;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class DB_Access {
//    OTHER OBJECTS
    private DB_Database database = DB_Database.getInstance();
    private Connection connection = database.getConnection();

//   SINGLETON
    private static DB_Access theInstance;

    private DB_Access() {
        loadPreparedStatements();
    }

    public static DB_Access getInstance(){
        if(theInstance == null){
            theInstance = new DB_Access();
        }
        return theInstance;
    }
//    //////////////////////
    private PreparedStatement addClassStmt;
    private PreparedStatement addStudentStmt;
    private PreparedStatement getClassIdStmt;
    private PreparedStatement getStudentFromClassStmt;

    private void loadPreparedStatements(){
        String addClassString =     "INSERT INTO public.grade (classname)\n" +
                                    "VALUES (?);";

        String addStudentString =   "INSERT INTO public.student (catno, firstname, lastname, gender, dateofbirth, classid)\n" +
                                    "VALUES (?, ?, ?, ?, ?, ?);";

        String getClassIdString =   "SELECT classid\n" +
                                    "FROM grade\n" +
                                    "WHERE classname = ?;";

        String getStudentFromClassString =  "SELECT *\n" +
                                            "FROM student\n" +
                                            "WHERE classid = ?;";

        try {
            addClassStmt = connection.prepareStatement(addClassString);
            addStudentStmt = connection.prepareStatement(addStudentString);
            getClassIdStmt = connection.prepareStatement(getClassIdString);
            getStudentFromClassStmt = connection.prepareStatement(getStudentFromClassString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//  CONNECT
    public void connect() throws SQLException {
        database.connect();
    }

//    IMPORT DATA
    public void importDataFromFile() throws SQLException {
        deleteAllStudents();
        deleteAllClasses();

        Path path = Paths.get("src/main/resources/Students_3xHIF_2022.csv");
        List<String> lines = new ArrayList<>();

        try {
            lines = Files.readAllLines(path)
                    .stream()
                    .skip(1)
                    .toList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, List<Student>> classMap = new TreeMap<>();

        for(String line : lines){
//            3BHIF;TAUCHER;Markus;m;2005-04-15
            String[] tokens = line.split(";");
            String classname = tokens[0];
            String lastname = tokens[1];
            String firstname = tokens[2];
            Gender gender = Gender.valueOf(tokens[3]);
            LocalDate datOfBirth = LocalDate.parse(tokens[4], Student.DTF_IN);

            if(classMap.containsKey(classname)){
                classMap.get(classname).add(new Student(classname, firstname, lastname, gender, datOfBirth));
            }else{
                ArrayList studentList = new ArrayList();
                studentList.add(new Student(classname, firstname, lastname, gender, datOfBirth));
                classMap.put(classname, studentList);
            }
        }

        classMap.forEach((s, students) -> students.sort(Comparator.comparing(Student::getLastname).thenComparing(Student::getFirstname)));

//        ADD STUDENTS TO DATABASE

        for(Map.Entry<String, List<Student>> entry : classMap.entrySet()){
//            ADD CLASS
            addClassStmt.setString(1, entry.getKey());
            addClassStmt.executeUpdate();

//            GET CLASSID
            getClassIdStmt.setString(1, entry.getKey());
            ResultSet resultSet = getClassIdStmt.executeQuery();
            resultSet.next();
            int classId = resultSet.getInt("classid");

            int catNo = 1;
            for(Student student : entry.getValue()){
//                "INSERT INTO public.student (catno, firstname, lastname, gender, dateofbirth, classid)\n" +
//                                    "VALUES (?, ?, ?, ?, TO_DATE(?, ?), ?);";
                addStudentStmt.setInt(1, catNo);
                addStudentStmt.setString(2, student.getFirstname());
                addStudentStmt.setString(3, student.getLastname());
                addStudentStmt.setString(4, student.getGender().name());
                addStudentStmt.setDate(5, Date.valueOf(student.getDateOfBirth()));
                addStudentStmt.setInt(6, classId);

                addStudentStmt.executeUpdate();
                catNo++;
            }
        }
    }


//    DELETE ALL STUDENTS
    public void deleteAllStudents() throws SQLException {
        Statement statement = database.getStatement();
        String sqlString = "DELETE FROM student;";

        statement.execute(sqlString);

        database.releaseStatement(statement);
    }
//    DELETE ALL CLASSES
    public void deleteAllClasses() throws SQLException {
        Statement statement = database.getStatement();
        String sqlString = "DELETE FROM grade;";

        statement.execute(sqlString);

        database.releaseStatement(statement);
    }


//    GET ALL CLASSNAMES
    public List<String> getAllClassnames() throws SQLException {
        Statement statement = database.getStatement();
        String sqlString =  "SELECT classname\n" +
                            "FROM grade\n" +
                            "ORDER BY classname;";

        ResultSet resultSet = statement.executeQuery(sqlString);
        List<String> classNames = new ArrayList<>();

        while (resultSet.next()){
            classNames.add(resultSet.getString("classname"));
        }

        database.releaseStatement(statement);
        return classNames;
    }

//    GET ALL STUDENTS
    public List<Student> getStudentsFromClass(String className) throws SQLException {
        List<Student> students = new ArrayList<>();

        getClassIdStmt.setString(1, className);
        ResultSet resultSet = getClassIdStmt.executeQuery();
        resultSet.next();
        int classId = resultSet.getInt("classid");

        getStudentFromClassStmt.setInt(1, classId);
        ResultSet resultSetStudents = getStudentFromClassStmt.executeQuery();
        while(resultSetStudents.next()){
            int studentId = resultSetStudents.getInt("student_id");
            int catNo = resultSetStudents.getInt("catno");
            String firstname = resultSetStudents.getString("firstname");
            String lastname = resultSetStudents.getString("lastname");
            Gender gender = Gender.valueOf(resultSetStudents.getString("gender"));
            LocalDate dateOfBirth = resultSetStudents.getDate("dateofbirth").toLocalDate();
            int classID = resultSetStudents.getInt("classid");

//            Student(int studentId, int classId, String classname, int catNo, String firstname, String lastname, Gender gender, ...
            students.add(new Student(studentId, classID, className, catNo, firstname, lastname, gender, dateOfBirth));
        }
        students.sort(Comparator.comparing(Student::getLastname).thenComparing(Student::getFirstname));
        return students;
    }

//    ADD STUDENT
    public void addStudent(Student student) throws SQLException {
//        GET CLASS ID
        getClassIdStmt.setString(1, student.getClassname());
        ResultSet resultSet = getClassIdStmt.executeQuery();
        resultSet.next();
        int classId = resultSet.getInt("classid");

//        CORRECT CAT NOs
        List<Student> students = getStudentsFromClass(student.getClassname());
        students.add(student);
        students.sort(Comparator.comparing(Student::getLastname).thenComparing(Student::getFirstname));
        int catNo = students.size();

        if(students.indexOf(student)+1 < students.size()){
            catNo = students.get(students.indexOf(student)+1).getCatNo();

            Statement statement = database.getStatement();

            for(Student subStudent : students.subList(students.indexOf(student), students.size())){
                String sqlString =  "UPDATE student\n" +
                                    "SET catno = " + (subStudent.getCatNo()+1) + "\n" +
                                    "WHERE student_id = " + subStudent.getStudentId() + ";";
                statement.executeUpdate(sqlString);

            }
            database.releaseStatement(statement);
        }


//        ADD STUDENT
//        INSERT INTO public.student (catno, firstname, lastname, gender, dateofbirth, classid)\n" +
//        "VALUES (?, ?, ?, ?, ?, ?);";
        addStudentStmt.setInt(1, catNo);
        addStudentStmt.setString(2, student.getFirstname());
        addStudentStmt.setString(3, student.getLastname());
        addStudentStmt.setString(4, student.getGender().name());
        addStudentStmt.setDate(5, Date.valueOf(student.getDateOfBirth()));
        addStudentStmt.setInt(6, classId);
        addStudentStmt.executeUpdate();
    }


//    EXPORT DATA
    public void exportDatabaseToFile() throws SQLException, IOException {
        Path path = Paths.get("src/main/resources/export.csv");
        List<String> lines = new ArrayList<>();

        Statement statement = database.getStatement();
        String sqlstring = "SELECT student_id, classname, g.classid, catno, firstname, lastname, gender, dateofbirth\n" +
                "FROM student INNER JOIN grade g on g.classid = student.classid\n" +
                "ORDER BY classname, catno;";

        ResultSet resultSet = statement.executeQuery(sqlstring);

        while(resultSet.next()){
            int studentId = resultSet.getInt("student_id");
            String classname = resultSet.getString("classname");
            int classID = resultSet.getInt("classid");
            int catNo = resultSet.getInt("catno");
            String firstname = resultSet.getString("firstname");
            String lastname = resultSet.getString("lastname");
            Gender gender = Gender.valueOf(resultSet.getString("gender"));
            LocalDate dateOfBirth = resultSet.getDate("dateofbirth").toLocalDate();
            Student s = new Student(studentId, classID, classname, catNo, firstname, lastname, gender, dateOfBirth);
            lines.add(s.toCSV());
        }

        database.releaseStatement(statement);

        Files.write(path, lines);
    }
}
