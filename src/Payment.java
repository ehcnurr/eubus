public abstract class Payment {
    private String type = "";
    private double amount = 0;

    public Payment(String type) {
        this.type = type;
    }
    public String getType () {
        return type;
    }

    public abstract void calculateAmount();

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}
