package at.kaindorf.lernen_School.GUI;

import at.kaindorf.lernen_School.database.DB_Access;
import at.kaindorf.lernen_School.pojos.Gender;
import at.kaindorf.lernen_School.pojos.Student;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class StudentGUI {

    public static void main(String[] args) {
        DB_Access dba = DB_Access.getInstance();
        Scanner scanner = new Scanner(System.in);


        while (true){

            System.out.println(
                    "\n---------------------------\n" +
                            "(1) connect to the database\n" +
                            "(2) import data\n" +
                            "(3) get all classnames\n" +
                            "(4) students of one class or all classes\n" +
                            "(5)  insert a new student\n" +
                            "(6)  export\n");

            int selected = 0;

            try{
                selected = scanner.nextInt();
            } catch (Exception e){
                e.printStackTrace();
            }



            try {
                switch (selected){
                    case 1: dba.connect();break;
                    case 2: dba.importDataFromFile();break;
                    case 3: dba.getAllClassnames().forEach(System.out::println);break;
                    case 4: dba.getStudentsFromClass("3DHIF").forEach(System.out::println);break;
                    case 5: dba.addStudent(new Student("3DHIF", "GG", "GG", Gender.m, LocalDate.now()));break;
                    case 6: dba.exportDatabaseToFile();break;

                    default:
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
