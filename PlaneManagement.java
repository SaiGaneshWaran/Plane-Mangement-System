
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class PlaneManagement {
    private static Ticket[] tickets = new Ticket[52];
    private static int[][] seats;

    public PlaneManagement() {
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println();
        System.out.println("                 Initial Seating Plan           ");
        System.out.println();
        SeatingPlan();
        System.out.println();
        System.out.println("Welcome to the Plane Management application");
        System.out.println();
        char letter = 'Y';


        do {
            starprinting();
            System.out.println("*             MENU OPTIONS                 *");
            starprinting();
            System.out.println();
            String[] options = new String[]{"1)Buy a seat", "2)Cancel a seat", "3)Find first available seat", "4)Show seating plan", "5)Print tickets information and total sales", "6)Search ticket", "0)Quit"};

            for (int i = 0; i < options.length; ++i) {
                System.out.println(options[i]);
            }

            System.out.println();
            starprinting();
            System.out.println("Please select an option:");
            char select = input.next().charAt(0);
            switch (select) {
                case '0':
                    break;
                case '1':
                    buy_seat();
                    break;
                case '2':
                    cancel_seat();
                    break;
                case '3':
                    find_first_available();
                    break;
                case '4':
                    show_seating_plan();
                    break;
                case '5':
                    print_tickets_info();
                    break;
                case '6':
                    search_ticket();
                    break;
                default:
                    System.out.println("Invalid option. Please select a valid option.");
            }

            System.out.println("Do you want to continue (Y/N)?");
            letter = input.next().charAt(0);
        } while (letter == 'Y' || letter == 'y');

    }

    private static void buy_seat() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter person information:");
        System.out.println("Enter the first name");
        String name=input.next();
        System.out.println("Enter the sur name");
        String surname=input.next();
        System.out.println("Enter the email");
        String email=input.next();

        boolean valid_email = false;

        // Validates email
        while (!valid_email) {
            if (email.isEmpty()) {
                System.out.println("\t\t▶ Email has to be entered.");
                // Ask for email input again
                email = input.next();  // Assuming 'input' is your Scanner object
            } else {
                if (!email.contains("@") || !email.contains("mail.")) {
                    System.out.println(" Invalid email. Email should contain '@' , 'mail.' ");
                    // Ask for email input again
                    System.out.println("Enter email address properly");
                    email = input.next();  // Assuming 'input' is your Scanner object
                } else {
                    valid_email = true;
                }
            }
        }
        int price = 0;

        char rowLetter;
        do {
            System.out.println("Enter row letter (A, B, C, or D): ");
            rowLetter = input.next().toUpperCase().charAt(0);
            if (rowLetter < 'A' || rowLetter > 'D') {
                System.out.println("Invalid row letter. Please enter A, B, C, or D");
            }
        } while (rowLetter < 'A' || rowLetter > 'D');

        int seatNumber;
        do {
            System.out.println("Enter seat number: ");
            seatNumber = input.nextInt();
            if (seatNumber < 1 || seatNumber > seats[rowIndex(rowLetter)].length) {
                System.out.println("Invalid seat number");
            }
        } while (seatNumber < 1 || seatNumber > seats[rowIndex(rowLetter)].length);

        String fileName = "" + rowLetter + String.valueOf(seatNumber) + ".txt";
        File file = new File(fileName);
        if (file.exists()) {
            System.out.println("Sorry, the seat " + rowLetter + seatNumber + " has already been sold.");
        } else {
            if (seats[rowIndex(rowLetter)][seatNumber - 1] == 0) {
                if (rowLetter >= 'A' && rowLetter <= 'D' && seatNumber >= 1 && seatNumber <= 5) {
                    price = 200;
                } else if (rowLetter >= 'A' && rowLetter <= 'D' && seatNumber >= 6 && seatNumber <= 9) {
                    price = 150;
                } else {
                    price = 180;
                }

                System.out.println("" + rowLetter + seatNumber + " price equal to " + price);
                System.out.println("Are you confirm to buy  " + rowLetter + seatNumber);
                System.out.println("if yes press y,if no press any other character");
                char option = input.next().charAt(0);
                if (option == 'Y' || option == 'y') {
                    seats[rowIndex(rowLetter)][seatNumber - 1] = 1;
                    System.out.println("" + rowLetter + seatNumber + " seat successfully booked");
                    Person person = new Person(name, surname, email);
                    Ticket ticket = new Ticket(String.valueOf(rowLetter), seatNumber, price, person);
                    ticket.save();
                    SoldTickets(ticket);
                }
            } else {
                System.out.println("" + rowLetter + seatNumber + " seat is not available right now");
            }

            show_seating_plan();
        }
    }

    private static void cancel_seat() {
        Scanner input = new Scanner(System.in);
        int price = 0;
        String name = null;
        String surname = null;
        String email = null;

        char rowLetter;
        do {
            System.out.println("Enter row letter (A, B, C, or D): ");
            rowLetter = input.next().toUpperCase().charAt(0);
            if (rowLetter < 'A' || rowLetter > 'D') {
                System.out.println("Invalid row letter. Please enter A, B, C, or D");
            }
        } while (rowLetter < 'A' || rowLetter > 'D');

        int seatnumber;
        do {
            System.out.println("Enter seat number: ");
            seatnumber = input.nextInt();
            if (seatnumber < 1 || seatnumber > seats[rowIndex(rowLetter)].length) {
                System.out.println("Invalid seat number");
            }
        } while (seatnumber < 1 || seatnumber > seats[rowIndex(rowLetter)].length);

        if (seats[rowIndex(rowLetter)][seatnumber - 1] == 1) {
            seats[rowIndex(rowLetter)][seatnumber - 1] = 0;
            System.out.println("" + rowLetter + seatnumber + " seat booking cancelled");

            for (int i = 0; i < tickets.length; ++i) {
                if (tickets[i] != null && tickets[i].getRow().equals(String.valueOf(rowLetter)) && tickets[i].getSeat() == seatnumber) {
                    removeSoldTickets(tickets[i]);
                    break;
                }
            }
        } else {
            System.out.println("" + rowLetter + seatnumber + " seat is not booked yet so cannot be cancel");
        }

        System.out.println("press y if you want to continue");
        Person person = new Person((String) name, (String) surname, (String) email);
        Ticket ticket = new Ticket(String.valueOf(rowLetter), seatnumber, price, person);
        ticket.DeleteFile();
        show_seating_plan();
    }

    private static void find_first_available() {
        for (int i = 0; i < seats.length; ++i) {
            for (int j = 0; j < seats[i].length; ++j) {
                if (seats[i][j] == 0) {
                    char rowLetter = (char) (65 + i);
                    int seatNumber = j + 1;
                    String fileName = "" + rowLetter + String.valueOf(seatNumber) + ".txt";
                    File file = new File(fileName);
                    if (!file.exists()) {
                        System.out.println("First available seat: " + rowLetter + seatNumber);
                        return;
                    }
                }
            }
        }

        System.out.println("No available seats.");
        System.out.println("press y if you want to continue");
    }

    private static void show_seating_plan() {
        for (int i = 0; i < seats.length; ++i) {
            char rowLetter = (char) (65 + i);
            System.out.print("" + rowLetter + ": ");

            for (int j = 0; j < seats[i].length; ++j) {
                int seatNumber = j + 1;
                String fileName = "" + rowLetter + String.valueOf(seatNumber) + ".txt";
                File file = new File(fileName);
                if (file.exists()) {
                    System.out.print("X ");
                } else if (seats[i][j] == 1) {
                    System.out.print("X ");
                } else {
                    System.out.print("0 ");
                }
            }

            System.out.println();
        }

        System.out.println("press y if you want to continue");
    }

    private static void print_tickets_info() {
        double totalAmount = 0.0;
        System.out.println();
        System.out.println("Tickets and Its information that sold during this session ");
        System.out.println();

        for (int i = 0; i < seats.length; ++i) {
            char rowLetter = (char) (65 + i);

            for (int j = 0; j < seats[i].length; ++j) {
                int seatNumber = j + 1;
                String fileName = "" + rowLetter + String.valueOf(seatNumber) + ".txt";
                File file = new File(fileName);
                if (file.exists()) {
                    try {
                        Scanner scanner = new Scanner(file);

                        try {
                            while (scanner.hasNextLine()) {
                                System.out.println(scanner.nextLine());
                            }

                            Ticket[] var9 = tickets;
                            int var10 = var9.length;

                            for (int var11 = 0; var11 < var10; ++var11) {
                                Ticket ticket = var9[var11];
                                if (ticket != null && ticket.getRow().equals(String.valueOf(rowLetter)) && ticket.getSeat() == seatNumber) {
                                    totalAmount += (double) ticket.getPrice();
                                    break;
                                }
                            }

                            System.out.println();
                        } catch (Throwable var14) {
                            try {
                                scanner.close();
                            } catch (Throwable var13) {
                                var14.addSuppressed(var13);
                            }

                            throw var14;
                        }

                        scanner.close();
                    } catch (IOException var15) {
                        System.out.println("some error occurred");
                    }
                }
            }
        }

        System.out.println("total amount during this session:  " + totalAmount);
    }

    private static void search_ticket() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter row letter (A, B, C, or D): ");
        char rowLetter = input.next().toUpperCase().charAt(0);
        System.out.println("Enter seat number: ");
        int seatNumber = input.nextInt();
        String fileName = "" + rowLetter + String.valueOf(seatNumber) + ".txt";
        File ticketFile = new File(fileName);
        if (ticketFile.exists()) {
            System.out.println("Someone has booked this ticket. Below is the information:");

            try {
                Scanner scanner = new Scanner(ticketFile);

                try {
                    while (scanner.hasNextLine()) {
                        System.out.println(scanner.nextLine());
                    }
                } catch (Throwable var9) {
                    try {
                        scanner.close();
                    } catch (Throwable var8) {
                        var9.addSuppressed(var8);
                    }

                    throw var9;
                }

                scanner.close();
            } catch (IOException var10) {
                System.out.println("some error occurred");
            }
        } else {
            System.out.println("This seat is available.");
        }

    }

    private static void SeatingPlan() {
        char rowLetter = 'A';
        int seatnumber = 1;
        seats = new int[4][];
        seats[0] = new int[14];
        seats[1] = new int[12];
        seats[2] = new int[12];
        seats[3] = new int[14];

        int i;
        for (i = 1; i <= 10; ++i) {
            System.out.print("     " + i);
        }

        System.out.print("    11");
        System.out.print("    12");
        System.out.print("    13");
        System.out.print("    14");
        System.out.println();

        for (i = 0; i < seats.length; ++i) {
            System.out.print("" + rowLetter + "    ");

            for (int j = 0; j < seats[i].length; ++j) {
                System.out.print(seats[i][j] + "     ");
            }

            System.out.println();
            ++rowLetter;
        }

    }

    private static int rowIndex(char rowLetter) {
        return rowLetter - 65;
    }

    private static void starprinting() {
        for (int i = 0; i < 44; ++i) {
            System.out.print("*");
        }

        System.out.println();
    }

    private static void SoldTickets(Ticket ticket) {
        boolean isAdded = false;

        for (int i = 0; i < tickets.length; ++i) {
            if (tickets[i] == null) {
                tickets[i] = ticket;
                System.out.println("Ticket added to the array successfully");
                isAdded = true;
                break;
            }
        }

        if (!isAdded) {
            System.out.println("Failed to add ticket. Sold tickets array may be full.");
        }

    }

    private static void removeSoldTickets(Ticket ticket) {
        boolean isRemoved = false;

        for (int i = 0; i < tickets.length; ++i) {
            if (tickets[i] == ticket) {
                tickets[i] = null;
                System.out.println("Ticket removed from the array succesfully");
                isRemoved = true;
                break;
            }
        }

        if (!isRemoved) {
            System.out.println("Failed to remove ticket. Ticket not found in sold tickets array.");
        }

    }
}
