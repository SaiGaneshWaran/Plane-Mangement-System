import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class Ticket {
    private String row;
    private int seat;
    private int price;
    private Person person;

    public Ticket(String row, int seat, int price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    public String getRow() {
        return this.row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public int getSeat() {
        return this.seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void fullInfo() {
        this.person.infoPerson();
        System.out.println("row: " + this.row);
        System.out.println("seat: " + this.seat);
        System.out.println("price: " + this.price);
        System.out.println("person: " + String.valueOf(this.person));
    }

    public void save() {
        String fileName = this.row + this.seat + ".txt";

        try {
            FileWriter info = new FileWriter(fileName);
            info.write("Row: " + this.row + "\n");
            info.write("Seat: " + this.seat + "\n");
            info.write("Price: " + this.price + "\n");
            info.write("Person Information:\n");
            info.write("Name: " + this.person.getName() + "\n");
            info.write("Surname: " + this.person.getSurname() + "\n");
            info.write("Email: " + this.person.getEmail() + "\n");
            info.close();
            System.out.println("Ticket information saved to " + fileName);
        } catch (IOException var3) {
            System.out.println("error occurs while saving the information of ticket");
        }

    }

    public void DeleteFile() {
        String FileName = this.row + this.seat + ".txt";
        File file = new File(FileName);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println(FileName + " FILE : SUCCESSFULLY DELETED");
            } else {
                System.out.println(FileName + " FILE : DELETE UNSUCCESSFUL");
            }
        } else {
            System.out.println(FileName + " FILE DO NOT EXISTS");
        }

    }
}

