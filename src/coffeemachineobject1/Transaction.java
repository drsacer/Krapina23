package coffeemachineobject1;

public class Transaction {

   /* public class TransactionLog {
        private Date datetime;
        private CoffeeType coffeeType;
        private String action;*/

    private int id;

    private String transaction;

    public Transaction(String transaction ) {
        this.transaction = transaction;
    }

    public String getTransaction() {
        return transaction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*  @Override
    public String toString() {
        return "Transaction{" +
                "transaction='" + transaction + '\'' +
                '}' + "\n";
    }*/
}
