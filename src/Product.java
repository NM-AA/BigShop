public class Product {
    protected int unitPrice = 0;
    protected int amount = 0;
    protected float tax = 0;
    protected int priceWithoutDiscount = 0;
    protected int numberOfDiscounts = 0;
    protected float finalPrice = 0;

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public int getPriceWithoutDiscount() {
        return (this.getUnitPrice() * this.getAmount());
    }

    public void setPriceWithoutDiscount(int priceWithoutDiscount) {
        this.priceWithoutDiscount = priceWithoutDiscount;
    }

    public int getNumberOfDiscounts() {
        return numberOfDiscounts;
    }

    public void setNumberOfDiscounts(int numberOfDiscounts) {
        this.numberOfDiscounts = numberOfDiscounts;
    }

    public float getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

}
