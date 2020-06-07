package atm.impl;

public enum Banknotes {
    TEN(10),
    FIFTY(50),
    ONE_HUNDRED(100),
    FIVE_HUNDRED(500),
    ONE_THOUSAND(1000),
    TWO_THOUSAND(2000),
    FIVE_THOUSAND(5000);

    public int value;

    Banknotes(int value) {
        this.value = value;
    }
}
