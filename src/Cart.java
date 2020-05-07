import java.io.FileWriter;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.io.File;
import java.io.IOException;

public class Cart {
    private static boolean loop = true;
    private static String validInput = "";
    private static int currentItemAmount = 0;
    private static SIM sim = null;
    private static PhoneCase phoneCase = null;
    private static Insurance insurance = null;
    private static WiredEarphones wiredEarphones = null;
    private static WirelessEarphones wirelessEarphones = null;
    private static final int limitSIM = 10;

    public static int checkAmountOfSIM(){
        if (getSim() == null) {
            return 0;
        }
        return getSim().getAmount();
    }

    public static int checkAmountOfPhoneCases(){
        if (getPhoneCase() == null) {
            return 0;
        }
        return getPhoneCase().getAmount();
    }

    public static int checkAmountOfWiredEarphones(){
        if (getWiredEarphones() == null) {
            return 0;
        }
        return getWiredEarphones().getAmount();
    }

    public static int checkAmountOfWirelessEarphones(){
        if (getWirelessEarphones() == null) {
            return 0;
        }
        return getWirelessEarphones().getAmount();
    }

    public static int checkAmountOfInsurance(){
        if (getInsurance() == null) {
            return 0;
        }
        return getInsurance().getAmount();
    }

    public static void addSIM (int toBeAdded){
        int currentSIMAmount = checkAmountOfSIM();
        if (0 == currentSIMAmount){
            setSim(new SIM(toBeAdded));
        } else {
            getSim().setAmount(currentSIMAmount+toBeAdded);
        }
        getSim().setFinalPrice();
    }

    public static void addPhoneCase (int toBeAdded){
        int currentPhoneCaseAmount = checkAmountOfPhoneCases();
        if (0 == currentPhoneCaseAmount){
            setPhoneCase(new PhoneCase(toBeAdded));
        } else {
            getPhoneCase().setAmount(currentPhoneCaseAmount+toBeAdded);
        }
        getPhoneCase().setFinalPrice();
    }

    public static void addWiredEarphones (Scanner scanner, int toBeAdded){
        int currentWiredEarphonesAmount = checkAmountOfWiredEarphones();
        if (0 == currentWiredEarphonesAmount){
            setWiredEarphones(new WiredEarphones(toBeAdded));
        } else {
            getWiredEarphones().setAmount(currentWiredEarphonesAmount+toBeAdded);
        }
        checkInsuranceEarphonesDiscount(scanner);
        getWiredEarphones().setFinalPrice();
    }

    public static void addWirelessEarphones (Scanner scanner, int toBeAdded){
        int currentWirelessEarphonesAmount = checkAmountOfWirelessEarphones();
        if (0 == currentWirelessEarphonesAmount){
            setWirelessEarphones(new WirelessEarphones(toBeAdded));
        } else {
            getWirelessEarphones().setAmount(currentWirelessEarphonesAmount+toBeAdded);
        }
        checkInsuranceEarphonesDiscount(scanner);
        getWirelessEarphones().setFinalPrice();
    }

    public static void addInsurance (int toBeAdded){
        int currentInsuranceAmount = checkAmountOfInsurance();
        if (0 == currentInsuranceAmount){
            setInsurance(new Insurance(toBeAdded));
        } else {
            getInsurance().setAmount(currentInsuranceAmount+toBeAdded);
        }
        if ((checkAmountOfWiredEarphones() != 0) || (checkAmountOfWirelessEarphones() != 0)) {
            getInsurance().setNumberOfDiscounts(getInsurance().getAmount());
        }
        getInsurance().setFinalPrice();
    }

    public static int checkSIMBOGOF(Scanner scanner, int toBeAdded){
        if ( (toBeAdded & 1) != 0 || ((toBeAdded + checkAmountOfSIM()) & 1) != 0) {
            System.out.println("There is a \"buy one get one free\" offer for " +
                    "SIM cards. Would you like to add one free SIM card to your " +
                    "cart?\nEnter \"y\" for yes or \"n\" for no.");
            setValidInput(scanner.nextLine());
            while (true){
                switch (getValidInput().toLowerCase()) {
                    case "y" -> {
                        System.out.println("One free SIM card was added.\n");
                        return ++toBeAdded;
                    }
                    case "n" -> {
                        return toBeAdded;
                    }
                    default -> System.out.println("***\nInvalid input.\n***\n");
                }
            }
        } else {
            return toBeAdded;
        }
    }

    public static int checkPhoneCaseOffer(Scanner scanner, int toBeAdded){
        if (((checkAmountOfPhoneCases() + toBeAdded) % 4) != 3) {
            return toBeAdded;
        } else {
            System.out.println("There is a \"4 for 3\" offer for " +
                    "phone cases. Would you like to add one free phone case to your " +
                    "cart?\nEnter \"y\" for yes or \"n\" for no.");
            setValidInput(scanner.nextLine());
            while (true){
                switch (getValidInput().toLowerCase()) {
                    case "y" -> {
                        System.out.println("One free phone case was added.\n");
                        return ++toBeAdded;
                    }
                    case "n" -> {
                        return toBeAdded;
                    }
                    default -> System.out.println("***\nInvalid input.\n***\n");
                }
            }
        }
    }

    public static void checkInsuranceEarphonesDiscount(Scanner scanner){
        if ((checkAmountOfInsurance() <= 0) && ((checkAmountOfWiredEarphones() != 0)
                || (checkAmountOfWirelessEarphones() != 0))) {
            System.out.println("Purchasing any pair of earphones grants you a 20% " +
                    "discount for phone insurance.\nWould you like to purchase " +
                    "phone insurance to use this discount? [y/n]");
            while (true){
                setValidInput(scanner.nextLine());
                switch (getValidInput().toLowerCase()) {
                    case "y" -> {
                        insuranceShopping(scanner);
                        if (0 == checkAmountOfInsurance()) {
                            System.out.println("No insurance was added to your cart." +
                                    "\nReturning.");
                        } else {
                            getInsurance().setNumberOfDiscounts(getInsurance().getAmount());
                            getInsurance().setFinalPrice();
                            System.out.println("20% discount was applied to your " +
                                    "phone insurance.\n");
                        }
                        return;
                    }
                    case "n" -> {
                        System.out.println("Returning...\n");
                        return;
                    }
                    default -> System.out.println("***\nInvalid input.\n***\n");
                }
            }
        }
        if ((checkAmountOfInsurance() > 0) && ((checkAmountOfWiredEarphones() != 0)
                || (checkAmountOfWirelessEarphones() != 0))) {
            System.out.println("---\nYour cart contains both phone insurance and " +
                    "earphones, which grants you a 20% discount on phone insurance.\n---");
            getInsurance().setNumberOfDiscounts(getInsurance().getAmount());
            getInsurance().setFinalPrice();
        } else {
            while (true){
                System.out.println("Purchasing any pair of earphones grants you a 20% " +
                        "discount for your phone insurance.\nEnter 1 to purchase wired " +
                        "earphones\n2 to purchase wireless earphones or\n3 to continue " +
                        "without a discount on phone insurance.");
                setValidInput(scanner.nextLine());
                switch (getValidInput()) {
                    case "1" -> {
                        wiredShopping(scanner);
                        if (0 == checkAmountOfWiredEarphones()) {
                            System.out.println("No discount was applied to your " +
                                    "phone insurance. You can obtain a discount later by " +
                                    "purchasing earphones.");
                        } else {
                            getInsurance().setNumberOfDiscounts(getInsurance().getAmount());
                            getInsurance().setFinalPrice();
                            System.out.println("20% discount was applied to your " +
                                    "phone insurance.");
                        }
                        return;
                    }
                    case "2" -> {
                        wirelessShopping(scanner);
                        if (0 == checkAmountOfWirelessEarphones()) {
                            System.out.println("No discount was applied to your " +
                                    "phone insurance. You can obtain a discount later by " +
                                    "purchasing earphones.");
                        } else {
                            getInsurance().setNumberOfDiscounts(getInsurance().getAmount());
                            getInsurance().setFinalPrice();
                            System.out.println("20% discount was applied to your " +
                                    "phone insurance.");
                        }
                        return;
                    }
                    case "3" -> {
                        System.out.println("Returning...\n");
                        return;
                    }
                    default -> System.out.println("***\nInvalid input.\n***\n");
                }
            }
        }
    }

    public static void confirmAddingSIM (Scanner scanner, int toBeAdded){
        toBeAdded = checkSIMBOGOF(scanner, toBeAdded);
        int newAmount = checkAmountOfSIM()+toBeAdded;
        System.out.println("+++\nThere are "+checkAmountOfSIM()+" SIM cards in your cart." +
                "\nDo you wish to add "+toBeAdded+"? (New amount of SIM cards" +
                " = "+newAmount+")\nEnter \"y\" to confirm or " +
                "any other key to return.\n+++");
        setValidInput(scanner.nextLine());
        if ("y".equals(getValidInput().toLowerCase())) {
            addSIM(toBeAdded);
            System.out.println("You now have " + getSim().getAmount() + " SIM cards.");
            setCurrentItemAmount(getCurrentItemAmount()+toBeAdded);
        } else {
            System.out.println("Returning...\n");
        }
    }

    public static void confirmAddingPhoneCase (Scanner scanner, int toBeAdded){
        toBeAdded = checkPhoneCaseOffer(scanner, toBeAdded);
        int newAmount = checkAmountOfPhoneCases()+toBeAdded;
        System.out.println("+++\nThere are "+checkAmountOfPhoneCases()+" phone cases in your cart." +
                "\nDo you wish to add "+toBeAdded+"? (New amount of phone cases" +
                " = "+newAmount+")\nEnter \"y\" to confirm or " +
                "any other key to return.\n+++");
        setValidInput(scanner.nextLine());
        if ("y".equals(getValidInput().toLowerCase())) {
            addPhoneCase(toBeAdded);
            System.out.println("You now have " + getPhoneCase().getAmount() + " phone cases.");
            setCurrentItemAmount(getCurrentItemAmount()+toBeAdded);
        } else {
            System.out.println("Returning...\n");
        }
    }

    public static void confirmAddingWiredEarphones (Scanner scanner, int toBeAdded){
        int newAmount = checkAmountOfWiredEarphones()+toBeAdded;
        System.out.println("+++\nThere are "+checkAmountOfWiredEarphones()+" pairs of wired earphones in your cart." +
                "\nDo you wish to add "+toBeAdded+"? (New amount of wired earphones" +
                " = "+newAmount+" pairs.)\nEnter \"y\" to confirm or " +
                "any other key to return.\n+++");
        setValidInput(scanner.nextLine());
        if ("y".equals(getValidInput().toLowerCase())) {
            addWiredEarphones(scanner, toBeAdded);
            System.out.println("You now have " + getWiredEarphones().getAmount() + " pairs of wired earphones.");
            setCurrentItemAmount(getCurrentItemAmount()+toBeAdded);
        } else {
            System.out.println("Returning...\n");
        }
    }

    public static void confirmAddingWirelessEarphones (Scanner scanner, int toBeAdded){
        int newAmount = checkAmountOfWirelessEarphones()+toBeAdded;
        System.out.println("+++\nThere are "+checkAmountOfWirelessEarphones()+" pairs of wireless earphones in your cart." +
                "\nDo you wish to add "+toBeAdded+"? (New amount of wireless earphones" +
                " = "+newAmount+" pairs.)\nEnter \"y\" to confirm or " +
                "any other key to return.\n+++");
        setValidInput(scanner.nextLine());
        if ("y".equals(getValidInput().toLowerCase())) {
            addWirelessEarphones(scanner, toBeAdded);
            System.out.println("You now have " + getWirelessEarphones().getAmount() + " pairs of wireless earphones.");
            setCurrentItemAmount(getCurrentItemAmount()+toBeAdded);
        } else {
            System.out.println("Returning...\n");
        }
    }

    public static void confirmAddingInsurance (Scanner scanner, int toBeAdded){
        int newAmount = checkAmountOfInsurance()+toBeAdded;
        System.out.println("+++\nThere are "+checkAmountOfInsurance()+" phone insurances in your cart." +
                "\nDo you wish to add "+toBeAdded+"? (New amount" +
                " = "+newAmount+")\nEnter \"y\" to confirm or " +
                "any other key to return.\n+++");
        setValidInput(scanner.nextLine());
        if ("y".equals(getValidInput().toLowerCase())) {
            addInsurance(toBeAdded);
            System.out.println("You now have " + getInsurance().getAmount() + " phone insurances in your cart.");
            setCurrentItemAmount(getCurrentItemAmount()+toBeAdded);
            checkInsuranceEarphonesDiscount(scanner);
        } else {
            System.out.println("Returning...\n");
        }
    }

    public static void SIMCardshopping (Scanner scanner) {
        boolean SIMLoop = true;
        int number;
        while (SIMLoop){
            System.out.println("---\nYour cart contains "+checkAmountOfSIM()+" SIM cards.\n" +
                "The maximum amount of SIM cards is 10.\n---\nEnter how many " +
                    "you wish to purchase or enter \"0\" to return:");
            setValidInput(scanner.nextLine());
            try {
                number = Integer.parseInt(getValidInput());
                if (number < 0){
                    System.out.println("***\nPlease enter a positive number.\n***\n");
                    continue;
                }
                if (-0 == number){
                    System.out.println("Returning...\n");
                    SIMLoop = false;
                } else {
                    if (10 < number || (limitSIM < number + checkAmountOfSIM())) {
                        System.out.println("***\nThe maximum amount of SIM cards is 10.\n***\n");
                    } else {
                        confirmAddingSIM(scanner, number);
                        SIMLoop = false;
                    }
                }
            }
            catch (Exception e) {
                System.out.println("***\nInvalid input.\n***\n");
            }
        }
    }

    public static void phoneCaseShopping (Scanner scanner) {
        boolean caseLoop = true;
        int number;
        while (caseLoop){
            System.out.println("---\nYour cart contains "+checkAmountOfPhoneCases()+" phone cases.\n" +
                    "---\nEnter how many wish to purchase " +
                    "or enter \"0\" to return:");
            setValidInput(scanner.nextLine());
            try {
                number = Integer.parseInt(getValidInput());
                if (number < 0){
                    System.out.println("***\nPlease enter a positive number.\n***\n");
                } else
                    if (0 == number){
                        System.out.println("Returning...\n");
                        caseLoop = false;
                    } else {
                        confirmAddingPhoneCase(scanner, number);
                        caseLoop = false;
                    }
            }
            catch (Exception e) {
                System.out.println("***\nInvalid input.\n***\n");
            }
        }
    }

    public static void wiredShopping (Scanner scanner) {
        boolean wiredLoop = true;
        int number;
        while (wiredLoop){
            System.out.println("---\nYour cart contains "+checkAmountOfWiredEarphones()+
                    " pairs of wired earphones.\n" +
                    "---\nEnter how many you wish to purchase " +
                    "or enter \"0\" to return:");
            setValidInput(scanner.nextLine());
            try {
                number = Integer.parseInt(getValidInput());
                if (number < 0){
                    System.out.println("***\nPlease enter a positive number.\n***\n");
                } else
                if (0 == number){
                    System.out.println("Returning...\n");
                    wiredLoop = false;
                } else {
                    confirmAddingWiredEarphones(scanner, number);
                    wiredLoop = false;
                }
            }
            catch (Exception e) {
                System.out.println("***\nInvalid input.\n***\n");
            }
        }
    }

    public static void wirelessShopping (Scanner scanner) {
        boolean wirelessLoop = true;
        int number;
        while (wirelessLoop){
            System.out.println("---\nYour cart contains "+checkAmountOfWirelessEarphones()+
                    " pairs of wireless earphones.\n" +
                    "---\nEnter how many you wish to purchase " +
                    "or enter \"0\" to return:");
            setValidInput(scanner.nextLine());
            try {
                number = Integer.parseInt(getValidInput());
                if (number < 0){
                    System.out.println("***\nPlease enter a positive number.\n***\n");
                } else
                if (0 == number){
                    System.out.println("Returning...\n");
                    wirelessLoop = false;
                } else {
                    confirmAddingWirelessEarphones(scanner, number);
                    wirelessLoop = false;
                }
            }
            catch (Exception e) {
                System.out.println("***\nInvalid input.\n***\n");
            }
        }
    }

    public static void insuranceShopping (Scanner scanner) {
        boolean insuranceLoop = true;
        int number;
        while (insuranceLoop){
            System.out.println("---\nYour cart contains "+checkAmountOfInsurance()+" phone insurances.\n" +
                    "---\nEnter how many wish to purchase " +
                    "or enter \"0\" to return:");
            setValidInput(scanner.nextLine());
            try {
                number = Integer.parseInt(getValidInput());
                if (number < 0){
                    System.out.println("***\nPlease enter a positive number.\n***\n");
                } else
                if (0 == number){
                    System.out.println("Returning...\n");
                    insuranceLoop = false;
                } else {
                    confirmAddingInsurance(scanner, number);
                    insuranceLoop = false;
                }
            }
            catch (Exception e) {
                System.out.println("***\nInvalid input.\n***\n");
            }
        }
    }

    public static void shopping(Scanner scanner) {
        boolean shoppingLoop = true;
        while (shoppingLoop) {
            System.out.println("---\nYour cart contains "
                    +getCurrentItemAmount()+" products.\n" +
                    "---\nEnter 1 for SIM cards (20 CHF, SPECIAL OFFER: buy one get one free)" +
                    "\n2 for phone cases (10 CHF, SPECIAL OFFER: 4 for 3)" +
                    "\n3 for insurance (120 CHF for 2 years)" +
                    "\n4 for wired earphones (30 CHF)" +
                    "\n5 for wireless headphones (50 CHF)\n6 to return.");
            setValidInput(scanner.nextLine());
            switch (getValidInput()) {
                case "1" -> SIMCardshopping(scanner);
                case "2" -> phoneCaseShopping(scanner);
                case "3" -> insuranceShopping(scanner);
                case "4" -> wiredShopping(scanner);
                case "5" -> wirelessShopping(scanner);
                case "6" -> {
                    System.out.println("Returning...\n");
                    shoppingLoop = false;
                }
                default -> System.out.println("***\nInvalid input\n***\n");
            }
        }
    }

    public static void orderCleanup(){
        setCurrentItemAmount(0);
        setSim(null);
        setInsurance(null);
        setPhoneCase(null);
        setWiredEarphones(null);
        setWirelessEarphones(null);
    }

    public static void showCartContent() {

        if (checkAmountOfSIM() > 0){
            System.out.println("SIM card (20 CHF) * "+checkAmountOfSIM()+
                    " = "+getSim().getFinalPrice());
        }
        if (checkAmountOfPhoneCases() > 0){
            System.out.println("Phone case (10 CHF) * "+checkAmountOfPhoneCases()+
                    " = "+getPhoneCase().getFinalPrice());
        }
        if (checkAmountOfInsurance() > 0){
            System.out.println("Phone insurance (120 CHF for 2 years) * "+checkAmountOfInsurance()+
                    " = "+getInsurance().getFinalPrice());
        }
        if (checkAmountOfWiredEarphones() > 0){
            System.out.println("Wired earphones (30 CHF) * "+checkAmountOfWiredEarphones()+
                    " = "+getWiredEarphones().getFinalPrice());
        }
        if (checkAmountOfWirelessEarphones() > 0){
            System.out.println("Wireless earphones (50 CHF) * "+checkAmountOfWirelessEarphones()+
                    " = "+getWirelessEarphones().getFinalPrice());
        }
    }

    public static void cartOutput() {
        try {
            File myObj = new File("output.txt");
            if (myObj.createNewFile()) {
                System.out.println("Output file created: " + myObj.getName());
            } else {
                System.out.println("Output file already exists.");
            }
            FileWriter fileWriter = new FileWriter("output.txt");
            float basePrice = 0;
            float taxAdded = 0;
            float sumPrice = 0;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            System.out.println("---\nBig Shop\n"+dtf.format(now)+"\n---");
            fileWriter.write("---\nBig Shop\n"+dtf.format(now)+"\n---\n");
            if (checkAmountOfSIM() > 0){
                System.out.println("\nSIM card\n"+getSim().getAmount()+" * "+
                        getSim().getUnitPrice()+" CHF = "+getSim().getPriceWithoutDiscount());
                fileWriter.write("\nSIM card\n"+getSim().getAmount()+" * "+
                        getSim().getUnitPrice()+" CHF = "+getSim().getPriceWithoutDiscount()+"\n");
                if (0 < getSim().getNumberOfDiscounts()) {
                    System.out.println("Discount 100% * " + getSim().getNumberOfDiscounts()+
                            " = " + (getSim().getNumberOfDiscounts() * getSim().getUnitPrice()));
                    fileWriter.write("Discount 100% * " + getSim().getNumberOfDiscounts()+
                            " = " + (getSim().getNumberOfDiscounts() * getSim().getUnitPrice()+"\n"));
                }
                System.out.println("Total price for SIM cards including taxes and discounts = "+
                        getSim().getFinalPrice());
                fileWriter.write("Total price for SIM cards including taxes and discounts = "+
                        getSim().getFinalPrice()+"\n");
                basePrice += (getSim().getFinalPrice() / getSim().getSIMTax());
                taxAdded = (float) (taxAdded + ((getSim().getFinalPrice() / getSim().getSIMTax()) * 0.12));
                sumPrice += getSim().getFinalPrice();
                //System.out.println("1 "+basePrice+"\t"+taxAdded);
            }
            if (checkAmountOfPhoneCases() > 0){
                System.out.println("\nPhone case\n"+getPhoneCase().getAmount()+" * "+
                        getPhoneCase().getUnitPrice()+" CHF = "+getPhoneCase().getPriceWithoutDiscount());
                fileWriter.write("\nPhone case\n"+getPhoneCase().getAmount()+" * "+
                        getPhoneCase().getUnitPrice()+" CHF = "+getPhoneCase().getPriceWithoutDiscount()+"\n");
                if (0 < getPhoneCase().getNumberOfDiscounts()) {
                    System.out.println("Discount 100% * "+getPhoneCase().getNumberOfDiscounts()+
                            " = "+(getPhoneCase().getNumberOfDiscounts()*getPhoneCase().getUnitPrice()));
                    fileWriter.write("Discount 100% * "+getPhoneCase().getNumberOfDiscounts()+
                            " = "+(getPhoneCase().getNumberOfDiscounts()*getPhoneCase().getUnitPrice())+"\n");
                }
                System.out.println("Total price for phone cases including taxes and discounts = "+
                        getPhoneCase().getFinalPrice());
                fileWriter.write("Total price for phone cases including taxes and discounts = "+
                        getPhoneCase().getFinalPrice()+"\n");
                basePrice += (getPhoneCase().getFinalPrice() / getPhoneCase().getCaseTax());
                taxAdded = (float) (taxAdded + ((getPhoneCase().getFinalPrice() / getPhoneCase().getCaseTax()) * 0.12));
                sumPrice += getPhoneCase().getFinalPrice();
                //System.out.println("2 "+basePrice+"\t"+taxAdded);
            }
            if (checkAmountOfInsurance() > 0){
                System.out.println("\nPhone insurance\n"+getInsurance().getAmount()+" * "+
                        getInsurance().getUnitPrice()+" CHF = "+getInsurance().getPriceWithoutDiscount());
                fileWriter.write("\nPhone insurance\n"+getInsurance().getAmount()+" * "+
                        getInsurance().getUnitPrice()+" CHF = "+getInsurance().getPriceWithoutDiscount()+"\n");
                if (0 < getInsurance().getNumberOfDiscounts()) {
                    System.out.println("Discount 20% * "+getInsurance().getNumberOfDiscounts()+
                            " = "+(getInsurance().getNumberOfDiscounts()*getInsurance().getUnitPrice()));
                    fileWriter.write("Discount 20% * "+getInsurance().getNumberOfDiscounts()+
                            " = "+(getInsurance().getNumberOfDiscounts()*getInsurance().getUnitPrice())+"\n");
                }
                System.out.println("Total price for phone insurance including discounts = "+getInsurance().getFinalPrice());
                fileWriter.write("Total price for phone insurance including discounts = "+getInsurance().getFinalPrice()+"\n");
                basePrice += getInsurance().getFinalPrice();
                sumPrice += getInsurance().getFinalPrice();
            }
            if (checkAmountOfWiredEarphones() > 0){
                System.out.println("\nWired earphones\n"+getWiredEarphones().getAmount()+" * "+
                        getWiredEarphones().getUnitPrice()+" CHF = "+
                        getWiredEarphones().getPriceWithoutDiscount());
                System.out.println("Total price for wired headphones including taxes = "+getWiredEarphones().getFinalPrice());
                fileWriter.write("\nWired earphones\n"+getWiredEarphones().getAmount()+" * "+
                        getWiredEarphones().getUnitPrice()+" CHF = "+
                        getWiredEarphones().getPriceWithoutDiscount()+"\n");
                fileWriter.write("Total price for wired headphones including taxes = "+getWiredEarphones().getFinalPrice()+"\n");
                basePrice += (getWiredEarphones().getFinalPrice() / getWiredEarphones().getWiredTax());
                taxAdded = (float) (taxAdded + ((getWiredEarphones().getFinalPrice() /
                        getWiredEarphones().getWiredTax()) * 0.12));
                sumPrice += getWiredEarphones().getFinalPrice();
                //System.out.println("4 "+basePrice+"\t"+taxAdded);
            }
            if (checkAmountOfWirelessEarphones() > 0){
                System.out.println("\nWireless earphones\n"+getWirelessEarphones().getAmount()+" * "+
                        getWirelessEarphones().getUnitPrice()+" CHF = "+
                        getWirelessEarphones().getPriceWithoutDiscount());
                System.out.println("Total price for wireless headphones including taxes = "+getWirelessEarphones().getFinalPrice());
                fileWriter.write("\nWireless earphones\n"+getWirelessEarphones().getAmount()+" * "+
                        getWirelessEarphones().getUnitPrice()+" CHF = "+
                        getWirelessEarphones().getPriceWithoutDiscount()+"\n");
                fileWriter.write("Total price for wireless headphones including taxes = "+getWirelessEarphones().getFinalPrice()+"\n");
                basePrice += (getWirelessEarphones().getFinalPrice() / getWirelessEarphones().getWirelessTax());
                taxAdded = (float) (taxAdded + ((getWirelessEarphones().getFinalPrice() /
                        getWirelessEarphones().getWirelessTax()) * 0.12));
                sumPrice += getWirelessEarphones().getFinalPrice();
                //System.out.println("5 "+basePrice+"\t"+taxAdded);
            }
            System.out.println("---\nSales tax - Base price - Tax");
            System.out.println("12% - "+basePrice+" - "+taxAdded);
            System.out.println("---\nSum - "+sumPrice+"\n---");
            fileWriter.write("---\nSales tax - Base price - Tax\n");
            fileWriter.write("12% - "+basePrice+" - "+taxAdded+"\n");
            fileWriter.write("---\nSum - "+sumPrice+"\n---\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while creating  output file or file writer.");
            e.printStackTrace();
        }
    }

    public static void reviewOrder(Scanner scanner) {
        boolean reviewLoop = true;
        while (reviewLoop) {
            System.out.println("---\nPlease review the contents of your cart." +
                    "\n(Calculated prices include taxes and discounts.)\n---");
            showCartContent();
            System.out.println("---\nEnter 1 to confirm purchase and to receive an invoice" +
                    "\n2 to return.");
            setValidInput(scanner.nextLine());
            switch (getValidInput()) {
                case "1" -> {
                    cartOutput();
                    System.out.println("Thank you for your purchase...\n");
                    orderCleanup();
                    reviewLoop = false;
                }
                case "2" -> {
                    System.out.println("Returning...\n");
                    reviewLoop = false;
                }
                default -> System.out.println("Invalid input\n");
            }
        }
    }

    public static void checkoutOrder(Scanner scanner) {
        boolean checkoutLoop = true;
        while (checkoutLoop) {
            System.out.println("---\nYour cart contains "+getCurrentItemAmount()+
                    " products.\n---\nPlease review your order and enter 1 " +
                    "to confirm purchase\n2 to discard all products in your cart " +
                    "or\n3 to return.");
            setValidInput(scanner.nextLine());
            switch (getValidInput()) {
                case "1" -> {
                    if (getCurrentItemAmount() > 0) {
                        reviewOrder(scanner);
                    } else System.out.println("No items in cart.\nReturning...\n");
                    checkoutLoop = false;
                }
                case "2" -> {
                    if (getCurrentItemAmount() > 0) {
                        System.out.println("Discarding items in cart...\n");
                        orderCleanup();
                    } else System.out.println("No items to discard.\nReturning...\n");
                    checkoutLoop = false;
                }
                case "3" -> {
                    System.out.println("Returning...\n");
                    checkoutLoop = false;
                }
                default -> System.out.println("***\nInvalid input\n***\n");
            }
        }
    }

    public static boolean confirmExit(Scanner scanner) {
        System.out.println("Are you sure you wish to leave? Enter y to leave or any other key to stay.");
        setValidInput(scanner.nextLine());
        if (getValidInput().toLowerCase().equals("y")) {
            System.out.println("Thank you for visiting Big Shop. Until next time, goodbye!\n");
            return true;
        } else {
            System.out.println("Returning back to the shop...\n");
            return false;
        }
    }

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        while (isLoop()) {
            System.out.println("---\nYour cart contains "+getCurrentItemAmount()+" products.\n---\n" +
                    "Enter 1 to continue shopping\n2 to go to checkout\n3 to exit.");
            setValidInput(scanner.nextLine());
            switch (validInput) {
                case "1" -> {
                    System.out.println("Continuing shopping...\n");
                    shopping(scanner);
                }
                case "2" -> {
                    System.out.println("Proceeding to checkout...\n");
                    checkoutOrder(scanner);
                }
                case "3" -> {
                    if (confirmExit(scanner)) {
                        scanner.close();
                        setLoop(false);
                    }
                }
                default -> System.out.println("***\nInvalid input\n***\n");
            }
        }
    }

    public static boolean isLoop() {
        return loop;
    }

    public static void setLoop(boolean loop) {
        Cart.loop = loop;
    }

    public static String getValidInput() {
        return validInput;
    }

    public static void setValidInput(String validInput) {
        Cart.validInput = validInput;
    }

    public static int getCurrentItemAmount() {
        return currentItemAmount;
    }

    public static void setCurrentItemAmount(int currentItemAmount) {
        Cart.currentItemAmount = currentItemAmount;
    }

    public static SIM getSim() {
        return sim;
    }

    public static void setSim(SIM sim) {
        Cart.sim = sim;
    }

    public static PhoneCase getPhoneCase() {
        return phoneCase;
    }

    public static void setPhoneCase(PhoneCase phoneCase) {
        Cart.phoneCase = phoneCase;
    }

    public static Insurance getInsurance() {
        return insurance;
    }

    public static void setInsurance(Insurance insurance) {
        Cart.insurance = insurance;
    }

    public static WiredEarphones getWiredEarphones() {
        return wiredEarphones;
    }

    public static void setWiredEarphones(WiredEarphones wiredEarphones) {
        Cart.wiredEarphones = wiredEarphones;
    }

    public static WirelessEarphones getWirelessEarphones() {
        return wirelessEarphones;
    }

    public static void setWirelessEarphones(WirelessEarphones wirelessEarphones) {
        Cart.wirelessEarphones = wirelessEarphones;
    }
}
