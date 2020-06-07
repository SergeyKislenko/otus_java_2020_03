package atm.impl;

import atm.Atm;
import department.chain.AtmProc;
import department.observer.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class AtmImpl extends AtmProc implements Atm, Listener {
    private static final Logger logger = LoggerFactory.getLogger(AtmImpl.class);
    private Set<Cell> cells = new TreeSet<>();
    private final String nameAtm;

    public AtmImpl(String name) {
        this.nameAtm = name;
        for (Banknotes banknotes : Banknotes.values()) {
            cells.add(new Cell(banknotes));
        }
    }

    @Override
    public int getBalance() {
        return cells.stream().map(x -> x.getMoneySum()).reduce(0, Integer::sum);
    }

    @Override
    public void depositMoney(Banknotes banknotes, int count) {
        cells.stream()
                .filter(cell -> cell.getBanknotesValue() == banknotes.value)
                .forEach(cell -> cell.addBanknotes(count));
    }

    @Override
    public void getMoney(int amount) {
        int amountLeft = amount;
        Map issuedBanknotes = new HashMap<Banknotes, Integer>();
        for (Cell cell : cells) {
            int countBanknotes = 0;
            for (int i = cell.getCountOfBanknotes(); i > 0 && cell.getBanknotesValue() <= amountLeft; i--) {
                issuedBanknotes.put(cell.getBanknotesValue(), ++countBanknotes);
                amountLeft -= cell.getBanknotesValue();
            }
        }

        if (amountLeft == 0) {
            for (Cell cell : cells) {
                int banknoteValue = cell.getBanknotesValue();
                if (issuedBanknotes.containsKey(banknoteValue)) {
                    cell.removeBanknotes((Integer) issuedBanknotes.get(banknoteValue));
                }
            }
            logger.info("Thank you for your visit! Take your money!(" + amount + ")\n" + issuedBanknotes);
        } else {
            logger.info("Sorry in our ATM not enough money, come back later or enter another amount");
        }
    }

    public AtmImpl getCopy() {
        AtmImpl copy = new AtmImpl(this.getNameAtm());
        for (Cell cell : this.getCells()) {
            copy.depositMoney(cell.getBanknotes(), cell.getCountOfBanknotes());
        }
        return copy;
    }


    public String getNameAtm() {
        return nameAtm;
    }

    public Set<Cell> getCells() {
        return cells;
    }

    public void setCells(Set<Cell> cells) {
        this.cells = cells;
    }

    @Override
    public String toString() {
        return "AtmImpl{" +
                "nameAtm=" + nameAtm +
                ", cells='" + cells + '\'' +
                '}';
    }

    @Override
    public void onUpdate() {
        logger.info("Atm: " + getNameAtm() + "; Balance: " + getBalance());
    }


}
