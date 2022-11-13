package at.kaindorf.lernen_School.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class Student {
    private int studentId;
    private int classId;
    private String classname;
    private int catNo;
    private String firstname;
    private String lastname;
    private Gender gender;
    private LocalDate dateOfBirth;
    public static DateTimeFormatter DTF_IN = DateTimeFormatter.ofPattern("yyyy-MM-dd");

//            3BHIF;TAUCHER;Markus;m;2005-04-15


    public Student(String classname, String firstname, String lastname, Gender gender, LocalDate dateOfBirth) {
        this.classname = classname;
        this.firstname = firstname;
        this.lastname = lastname.charAt(0) + lastname.substring(1).toLowerCase();
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }





    public String toCSV(){
        return studentId + ";" + classname + ";" + catNo + ";" + firstname + ";" + lastname + ";" + gender.name() + ";" + dateOfBirth.format(DTF_IN);
    }
}
