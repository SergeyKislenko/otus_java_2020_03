package impl;

import domain.Atm;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class AtmImpl implements Atm {
    private final Set<Cell> cells = new TreeSet<>();

    public AtmImpl() {
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
                .filter(cell -> cell.getBanknotesValue() == Banknotes.getValue(banknotes))
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
            System.out.println("Thank you for your visit! Take your money!(" + amount + ")\n" + issuedBanknotes);
        } else {
            System.out.println("Sorry in our ATM not enough money, come back later or enter another amount");
        }
    }

    @Override
    public String toString() {
        return "AtmImpl{" +
                "cells=" + cells +
                '}';
    }
}
