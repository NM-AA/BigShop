public class WiredEarphones extends Product{
    protected final int wiredUnitPrice = 30;
    protected final float wiredTax = 1.12f;

    public WiredEarphones(int amount){
        this.unitPrice = getWiredUnitPrice();
        this.amount = amount;
        this.tax = getWiredTax();
        this.priceWithoutDiscount = getUnitPrice() * getAmount();
        this.numberOfDiscounts = 0;
        setFinalPrice();
    }

    public void setFinalPrice() {
        this.finalPrice = (getPriceWithoutDiscount() - (getNumberOfDiscounts() *
                getUnitPrice())) * getWiredTax();
    }

    public void setAmount(int amount){
        this.amount = amount;
        this.setPriceWithoutDiscount(this.getUnitPrice() * this.getAmount());
    }

    public int getWiredUnitPrice() {
        return wiredUnitPrice;
    }

    public float getWiredTax() {
        return wiredTax;
    }

}
