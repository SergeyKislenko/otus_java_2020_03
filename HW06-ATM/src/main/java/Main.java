import impl.AtmImpl;
import impl.Banknotes;

public class Main {
    public static void main(String[] args) {
        AtmImpl atm = new AtmImpl();
        atm.depositMoney(Banknotes.TEN, 2);
        atm.depositMoney(Banknotes.FIFTY, 2);
        atm.depositMoney(Banknotes.FIVE_HUNDRED, 5);
        atm.depositMoney(Banknotes.FIVE_THOUSAND, 5);
        System.out.println(atm.getBalance());
        atm.getMoney(6060);
        System.out.println(atm.getBalance());
        atm.getMoney(5010);
        System.out.println(atm.getBalance());
        atm.getMoney(5000);
        System.out.println(atm.getBalance());
        atm.getMoney(33);
        System.out.println(atm.getBalance());
        atm.getMoney(11550);
        System.out.println(atm.getBalance());
    }
}
