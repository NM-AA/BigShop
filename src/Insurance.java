public class Insurance extends Product{
    protected final int insuranceUnitPrice = 120;
    protected final float insuranceTax = 1;
    protected final float insuranceDiscount = 0.2f;

    public Insurance(int amount){
        this.unitPrice = getInsuranceUnitPrice();
        this.amount = amount;
        this.tax = getInsuranceTax();
        this.priceWithoutDiscount = getUnitPrice() * getAmount();
        this.numberOfDiscounts = 0;
        setFinalPrice();
    }

    public void setFinalPrice() {
        this.finalPrice = getPriceWithoutDiscount() - (getNumberOfDiscounts() *
                getUnitPrice() * getInsuranceDiscount());
    }

    public void setAmount(int amount){
        this.amount = amount;
        this.setPriceWithoutDiscount(this.getUnitPrice() * this.getAmount());
    }

    public int getInsuranceUnitPrice() {
        return insuranceUnitPrice;
    }

    public float getInsuranceTax() {
        return insuranceTax;
    }

    public float getInsuranceDiscount() {
        return insuranceDiscount;
    }

}
