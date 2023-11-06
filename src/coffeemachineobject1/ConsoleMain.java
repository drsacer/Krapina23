package coffeemachineobject1;


import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class ConsoleMain {
    static Scanner sc = new Scanner(System.in);
    static String action = "";
    static int typeOfCoffeeChoice;
    static String fileName = "src/coffeemachineobject1/doc/coffee_machine_status.txt";
    static String password = "admin";
    static String username = "admin";


    public static void main(String[] args) throws FileNotFoundException {

        CoffeeMachineDao cmDao = new CoffeeMachineDao("./coffeemachine.h2");

        CoffeeMachine cm = new CoffeeMachine(400, 540, 120, 9, 550);
        cm.loadFromFile(fileName);

        cmDao.addCoffeeType(cm.getCoffeeTypes());

        System.out.println(cm);

        while (!action.equals("exit")) {
            System.out.println("Write action \nbuy, login, exit");
            System.out.print("Enter:");
            action = sc.next();
            switch (action) {
                case "buy":
                    System.out.println("What do you want to buy?"); // 1 - espresso, 2 - latte, 3 - cappuccino, 4 - back - to main menu: ");
                    List<CoffeeType> coffeeTypes = cm.getCoffeeTypes();
                    for (int i = 0; i < cm.getCoffeeTypes().size(); i++) {
                        System.out.println(i + 1 + "- " + coffeeTypes.get(i).getName());
                    }
                    typeOfCoffeeChoice = sc.nextInt();
                    if (typeOfCoffeeChoice <= coffeeTypes.size()) {
                        cm.buyCoffee(typeOfCoffeeChoice);
                    } else {
                        System.out.println("Wrong enter\n");
                    }
                    break;

                case "login":  //ADMIN
                    System.out.println("Enter username: ");
                    String inputUsername = sc.next();
                    System.out.println("Enter password: ");
                    String inputPass = sc.next();
                    if (!password.equals(inputPass) || !username.equals(inputUsername)) {
                        System.out.println("Wrong username or password\n");
                    } else {
                        String ch = "";
                        while (!ch.equals("exit")) {
                            System.out.println("Write action:");
                            System.out.println(" *** fill, remaining, take, password, log, exit ***");
                            System.out.print("Enter:");
                            ch = sc.next();

                            switch (ch) {
                                case "fill":
                                    System.out.println("Write how many ml of water you want to add:");
                                    int water = sc.nextInt();
                                    System.out.println("Write how many ml of milk you want to add:");
                                    int milk = sc.nextInt();
                                    System.out.println("Write how many grams of coffee beans you want to add:");
                                    int coffeeBeans = sc.nextInt();
                                    System.out.println("\"Write how many disposable cups you want to add: ");
                                    int cup = sc.nextInt();
                                    cm.fill(water, milk, coffeeBeans, cup);
                                    break;

                                case "take":
                                    cm.take();
                                    break;

                                case "remaining":
                                    System.out.println(cm);
                                    break;

                                case"password":
                                    boolean digit = false;
                                    System.out.println("Enter new password: ");
                                    String newPass = sc.next();

                                    if(newPass.length() < 8) {
                                        System.out.println("Short password");
                                    }
                                    else  {
                                        for (int i = 0; i < newPass.length(); i++) {
                                            if (Character.isDigit(newPass.charAt(i))){
                                                digit = true;
                                                break;
                                            }
                                        }
                                    }
                                    if (digit){
                                        password = newPass;
                                        System.out.println("Pasword changed");
                                    }
                                    else{
                                        System.out.println("Password has to contain one digit at least");
                                    }
                                    break;

                                case "log":
                                    List<Transaction> transactions = cm.getTransactionsLog();
                                    for (Transaction t : transactions) {
                                        System.out.println("Transaction: " + t.getTransaction());
                                    }

                                case "exit":
                                    //cm.saveToFile(fileName);

                                    break;
                            }
                        }
                    }

                case "exit":
                    cm.saveToFile(fileName);
                    cmDao.addTransactionLog(cm.getTransactionsLog());

                    break;

                default:
                    System.out.println("No such option");
            }
        }
    }
}

