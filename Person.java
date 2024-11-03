public class Person {
    private String name;
    private String surname;
    private String email;

    public Person(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void infoPerson() {
        System.out.println("name: " + this.name);
        System.out.println("surname: " + this.surname);
        System.out.println("email: " + this.email);
    }

    public String toString() {
        return this.name + " " + this.surname;
    }
}
