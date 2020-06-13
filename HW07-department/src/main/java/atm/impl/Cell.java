package atm.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cell implements Comparable<Cell> {
    private static final Logger logger = LoggerFactory.getLogger(Cell.class);
    private final Banknotes banknotes;
    private final int banknotesValue;
    private int countOfBanknotes = 0;

    public Cell(Banknotes banknotes) {
        this.banknotes = banknotes;
        this.banknotesValue = banknotes.value;
    }

    public void addBanknotes(int count) {
        this.countOfBanknotes += count;
    }

    public void removeBanknotes(int count) {
        if(countOfBanknotes - count >= 0){
            this.countOfBanknotes -= count;
        }else {
            logger.info("Not enough banknotes");
        }
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

    public Banknotes getBanknotes() {
        return banknotes;
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
