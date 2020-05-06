public class WirelessEarphones extends Product{
    protected final int wirelessUnitPrice = 50;
    protected final float wirelessTax = 1.12f;

    public WirelessEarphones(int amount){
        this.unitPrice = getWirelessUnitPrice();
        this.amount = amount;
        this.tax = getWirelessTax();
        this.priceWithoutDiscount = getUnitPrice() * getAmount();
        this.numberOfDiscounts = 0;
        setFinalPrice();
    }

    public void setFinalPrice() {
        this.finalPrice = (getPriceWithoutDiscount() - (getNumberOfDiscounts() *
                getUnitPrice())) * getWirelessTax();
    }

    public void setAmount(int amount){
        this.amount = amount;
        this.setPriceWithoutDiscount(this.getUnitPrice() * this.getAmount());
    }

    public int getWirelessUnitPrice() {
        return wirelessUnitPrice;
    }

    public float getWirelessTax() {
        return wirelessTax;
    }

}
