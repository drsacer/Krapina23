package db;

import lists.bank.Bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankConsoleMain {
    static Connection conn = null;
    static Scanner s = new Scanner(System.in);
    static List<Client> clientList = new ArrayList<>();


    public static void main(String[] args) {

        conn = makeDBConnection("bank_db");

        ClientRepository br = new ClientRepository(conn);
        br.createTable();

        Bank adiko = new Bank("Adiko", "Banfica");
        System.out.println("Bank " + adiko.getName() + " is generated\n");

        int choice = 0;

        while (choice != 5) {
            System.out.println("1 - Create - add the new client");
            System.out.println("2 - Read - print all clients");
            System.out.println("3 - Update - update the client by OIB");
            System.out.println("4 - Delete -  delete the client");
            System.out.println("5 - Exit");
            System.out.print("Enter:");

            choice = s.nextInt();
            System.out.println();

            switch (choice){

                case 1:
                    Client newClient = new Client();

                    System.out.println("First name:");
                    newClient.setFirstName(s.next());
                    System.out.println("Last name:");
                    newClient.setLastName(s.next());
                    System.out.println("Address:");
                    newClient.setAddress(s.next());
                    System.out.println("OIB:");
                    newClient.setOib(s.next());

                    br.insertClient(newClient);
                    break;

                case 2:
                    clientList = br.getListOfALlClients();
                    for (Client c: clientList) {
                        System.out.println(c.getFirstName() + " " + c.getLastName() + " " + c.getAddress()+" " + c.getOib());
                    }
                    System.out.println();
                    break;

                case 3:
                    System.out.println("OIB: ");
                    String oib = s.next();
                    Client c = findClient(oib);

                    if (c == null){
                        System.out.println("Client doesnt exists");
                    }
                    else {
                       // br.updateClient(c);
                        System.out.println("Updated");
                    }
                    break;

                case 4:
                    System.out.println("OIB: ");
                    String o = s.next();
                    Client clientToDelete = findClient(o);

                    if (clientToDelete == null){
                        System.out.println("Client doesnt exists");
                    }
                    else {
                        br.delete(clientToDelete);
                    }
            }
        }
    }

    public static Connection makeDBConnection(String fileName) {
        try {
            return DriverManager.getConnection("jdbc:h2:./" + fileName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Client findClient(String oib){
        for (Client c : clientList){
            if (c.getOib().equals(oib)){
                return c;
            }
        }
        return null;
    }
}
