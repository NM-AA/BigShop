public class SIM extends Product{
    protected final int SIMUnitPrice = 20;
    protected final float SIMTax = 1.12f;

    public SIM(int amount){
        this.unitPrice = getSIMUnitPrice();
        this.amount = amount;
        this.tax = getSIMTax();
        this.priceWithoutDiscount = getUnitPrice() * getAmount();
        this.numberOfDiscounts = getAmount() / 2;
        setFinalPrice();
    }

    public void setFinalPrice() {
        this.finalPrice = (getPriceWithoutDiscount() - (getNumberOfDiscounts() *
                getUnitPrice())) * getSIMTax();
    }

    public void setAmount(int amount){
        this.amount = amount;
        this.setPriceWithoutDiscount(this.getUnitPrice() * this.getAmount());
        this.setNumberOfDiscounts((this.getAmount() / 2));
    }

    public int getSIMUnitPrice() {
        return SIMUnitPrice;
    }

    public float getSIMTax() {
        return SIMTax;
    }
}
