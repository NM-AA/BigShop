public class PhoneCase extends Product{
    protected final int caseUnitPrice = 10;
    protected final float caseTax = 1.12f;

    public PhoneCase(int amount){
        this.unitPrice = getCaseUnitPrice();
        this.amount = amount;
        this.tax = getCaseTax();
        this.priceWithoutDiscount = getUnitPrice() * getAmount();
        this.numberOfDiscounts = getAmount() / 4;
        setFinalPrice();
    }

    public void setFinalPrice() {
        this.finalPrice = (getPriceWithoutDiscount() - (getNumberOfDiscounts() *
                getUnitPrice())) * getCaseTax();
    }

    public void setAmount(int amount) {
        this.amount = amount;
        this.setPriceWithoutDiscount(this.getUnitPrice() * this.getAmount());
        this.setNumberOfDiscounts(this.getAmount() / 4);
        }

    public int getCaseUnitPrice() {
        return caseUnitPrice;
    }

    public float getCaseTax() {
        return caseTax;
    }

}
