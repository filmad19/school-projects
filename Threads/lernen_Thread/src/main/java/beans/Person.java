package beans;

public class Person {
    private String firstname;
    private String lastname;
    private String password;
    private String hash;

    public Person(String line) {
        String[] tokens = line.split(",");
        this.firstname = tokens[0];
        this.lastname = tokens[1];
        this.password = tokens[2];
        this.hash = tokens[3];
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}
