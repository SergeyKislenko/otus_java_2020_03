package domain;

import impl.Banknotes;

public interface Atm {
    int getBalance();
    void depositMoney (Banknotes banknotes, int count);
    void getMoney (int amount);
}
