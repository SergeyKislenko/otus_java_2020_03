package impl;


public class Cell implements Comparable<Cell> {
    private final Banknotes banknotes;
    private final int banknotesValue;
    private int countOfBanknotes = 0;

    public Cell(Banknotes banknotes) {
        this.banknotes = banknotes;
        this.banknotesValue = Banknotes.getValue(banknotes);
    }

    public void addBanknotes(int count) {
        this.countOfBanknotes += count;
    }

    public void removeBanknotes(int count) {
        this.countOfBanknotes -= count;
    }

    public int getBanknotesValue() {
        return banknotesValue;
    }

    public int getMoneySum() {
        return countOfBanknotes * banknotesValue;
    }

    public int getCountOfBanknotes() {
        return countOfBanknotes;
    }

    @Override
    public int compareTo(Cell o) {
        return (o.getBanknotesValue()-this.getBanknotesValue());
    }

    @Override
    public String toString() {
        return "Cell{" +
                "banknotes=" + banknotes +
                ", banknotesValue=" + banknotesValue +
                ", countOfBanknotes=" + countOfBanknotes +
                '}';
    }
}
