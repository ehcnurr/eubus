public class BusFarePayment extends Payment{
    public BusFarePayment(String type) {
        super(type);
    }

    public void calculateAmount() {
        if (getType().equals("guest")) {
            setAmount(12.00);
        } else if (getType().equals("student/staff")) {
            setAmount(10.00);
        }
    }
}
