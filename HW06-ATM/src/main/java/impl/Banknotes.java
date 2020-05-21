package impl;

public enum Banknotes {
    TEN,
    FIFTY,
    ONE_HUNDRED,
    FIVE_HUNDRED,
    ONE_THOUSAND,
    TWO_THOUSAND,
    FIVE_THOUSAND;

    public static int getValue(Banknotes banknotes) {
        switch (banknotes) {
            case TEN: return 10;
            case FIFTY: return 50;
            case ONE_HUNDRED: return 100;
            case FIVE_HUNDRED: return 500;
            case ONE_THOUSAND: return 1000;
            case TWO_THOUSAND: return 2000;
            case FIVE_THOUSAND: return 5000;
            default: return 0;
        }
    }
}
