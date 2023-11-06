package coffeemachineobject1;

import java.sql.*;
import java.util.List;

public class CoffeeMachineDao {
    private String dbName;

    private Connection conn;

    public CoffeeMachineDao(String dbName) {
        this.dbName = dbName;

        try {
            conn = DriverManager.getConnection("jdbc:h2:" + dbName);

            String sqlCreateTable ="CREATE TABLE IF NOT EXISTS transaction (\n" +
            "id integer PRIMARY KEY auto_increment, \n" +
            "transaction text NOT NULL\n)";

            Statement st = conn.createStatement();
            st.execute(sqlCreateTable);

        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        try {

            String sqlCreateTable ="CREATE TABLE IF NOT EXISTS coffee_type (\n" +
                    "id integer PRIMARY KEY auto_increment, \n" +
                    "name text NOT NULL,\n" +
                    "water text NOT NULL,\n"+
                    "milk text NOT NULL,\n"+
                    "coffee_beans text NOT NULL,\n"+
                    "price text NOT NULL\n)";

            Statement st = conn.createStatement();
            st.execute(sqlCreateTable);

        } catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    public void addTransactionLog(List<Transaction> transactionList){
        String insertSql = "INSERT INTO transaction (transaction) VALUES (?) ";

        try {
            PreparedStatement ps = conn.prepareStatement(insertSql);

            for (Transaction t : transactionList) {
               // ps.setInt(1, t.getId());
                ps.setString(1,t.getTransaction());

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addCoffeeType(List<CoffeeType> cofeeTypesList){
        String insertSql = "INSERT INTO coffee_type (name, water, milk, coffee_beans, price) VALUES (?,?,?,?,?) ";

        try {
            PreparedStatement ps = conn.prepareStatement(insertSql);

            for (CoffeeType ct : cofeeTypesList) {
              //  ps.setInt(1, ct.getId());
                ps.setString(1,ct.getName());
                ps.setInt(2,ct.getWaterNeeded());
                ps.setInt(3,ct.getMilkNeeded());
                ps.setInt(4,ct.getCoffeeBeansNeeded());
                ps.setInt(5,ct.getPrice());

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
