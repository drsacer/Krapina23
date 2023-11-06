package coffeemachineobject1;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CoffeeMachine {
    public static Scanner sc = new Scanner(System.in);
    private int water;
    private int milk;
    private int coffeeBeans;
    private int cups;
    private int money;
    private List <CoffeeType> coffeeTypes = new ArrayList<>();
    private List<Transaction> transactionsLog = new ArrayList<>();

    Date date = new Date(System.currentTimeMillis());
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");



    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;

        coffeeTypes.add(new CoffeeType("Espresso", 350, 0,16,4));
        coffeeTypes.add(new CoffeeType("Latte",350, 75,20,7));
        coffeeTypes.add(new CoffeeType("Capuccino",200, 100,12,6));
    }

    public List<Transaction> getTransactionsLog() {
        return transactionsLog;
    }

    public List<CoffeeType> getCoffeeTypes() {
        return coffeeTypes;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getWater(){
        return water;
    }
    public void setWater(int water){
        this.water=water;
    }

    public int getMilk() {
        return milk;
    }

    public void setMilk(int milk) {
        this.milk = milk;
    }

    public int getCoffeeBeans() {
        return coffeeBeans;
    }

    public void setCoffeeBeans(int coffeeBeans) {
        this.coffeeBeans = coffeeBeans;
    }

    public int getCups() {
        return cups;
    }

    public void setCups(int cups) {
        this.cups = cups;
    }
    public boolean isEnoughResources(int coffeType){
        if(water >= coffeeTypes.get(coffeType-1).getWaterNeeded() && milk>= coffeeTypes.get(coffeType-1).getMilkNeeded() && coffeeBeans >= coffeeTypes.get(coffeType-1).getCoffeeBeansNeeded() && cups > 0){
            return true;
        }
        else return false;
    }

    public void buyCoffee(int coffeeType){

        if(isEnoughResources((coffeeType))) {
            System.out.println("I have enough resources, making you " + this.coffeeTypes.get(coffeeType-1).getName() + "\n");

            this.water -= this.coffeeTypes.get(coffeeType-1).getWaterNeeded();
            this.milk -= this.coffeeTypes.get(coffeeType-1).getMilkNeeded();
            this.coffeeBeans -= this.coffeeTypes.get(coffeeType-1).getCoffeeBeansNeeded();
            this.money += this.coffeeTypes.get(coffeeType-1).getPrice();
            this.cups -= 1;

            transactionsLog.add(new Transaction("Time: " + simpleDateFormat.format(date) + "\nBought: " + coffeeTypes.get(coffeeType-1).getName()));

        } else {
            System.out.println("Sorry, not enough " + calculateWhichIngredientMissed(this.coffeeTypes.get(coffeeType-1)) + "\n");
            transactionsLog.add(new Transaction("Time: " + simpleDateFormat.format(date)+ "\n" + "Not enough " + calculateWhichIngredientMissed(this.coffeeTypes.get(coffeeType-1)) + " for " + coffeeTypes.get(coffeeType-1).getName()));
        }
    }

    public void take(){
        System.out.println("I gave you $" + this.money + "\n");
        transactionsLog.add(new Transaction("Time: " + simpleDateFormat.format(date) + "\nWhitdraw " + money + "$"));
        setMoney(0);
    }

    public String calculateWhichIngredientMissed(CoffeeType coffeeType){
        String ingredient = "";
        if(water < coffeeType.getWaterNeeded()) {
            ingredient = "water";
        }
        else if (milk < coffeeType.getMilkNeeded()) {
            ingredient = "milk" ;
        }
        else if (coffeeBeans < coffeeType.getCoffeeBeansNeeded()) {
            ingredient = "coffee beans" ;
        }
        return ingredient;
    }

    public void fill(int water, int milk, int coffeeBeans, int cups){
        setWater(getWater() + water);
        setMilk(getMilk() + milk);
        setCoffeeBeans(getCoffeeBeans() + coffeeBeans);
        setCups(getCups() + cups);

        transactionsLog.add(new Transaction("Time: " + simpleDateFormat.format(date) + "\nFilled: water: " + water + ", milk: " + milk + ", coffeeBeans:" + coffeeBeans + ", cups: " + cups));
    }

    // **** UPIS U DATOTEKU I ČITANJE IZ NJE ****

    public  void loadFromFile(String fileName) throws FileNotFoundException {

        FileReader reader = new FileReader(fileName);
        Scanner readFromFile = new Scanner(reader);

        // FILE : voda,mlijeko,kava,šalice,novac

        readFromFile.nextLine(); // preskače zaglavlje
        readFromFile.useDelimiter(",");

        setWater(readFromFile.nextInt());
        setMilk(readFromFile.nextInt());
        setCoffeeBeans(readFromFile.nextInt());
        setCups(readFromFile.nextInt());
        setMoney(readFromFile.nextInt());
    }

    public  void saveToFile(String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write("Voda,Mlijeko,Kava,Šalica,Novac\n");
            writer.write(getWater() + "," + getMilk() + "," + getCoffeeBeans() + "," + getCups() + "," + getMoney());
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "CoffeeMachine{ water=" + water +
                ", milk=" + milk +
                ", coffeeBeans=" + coffeeBeans +
                ", cups=" + cups +
                ", money=" + money +
                '}' + "\n";
    }
}

