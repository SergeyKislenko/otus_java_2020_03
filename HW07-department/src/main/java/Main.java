import atm.impl.AtmImpl;
import atm.impl.Banknotes;
import department.domain.impl.DepartmentImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        AtmImpl atm1 = new AtmImpl("Sberbank");
        AtmImpl atm2 = new AtmImpl("Alfa bank");
        atm1.depositMoney(Banknotes.TEN, 2);
        atm2.depositMoney(Banknotes.FIFTY, 2);

        DepartmentImpl department = new DepartmentImpl(atm1, atm2);

        department.collectAtms();
        logger.info("Сurrent balance ");
        department.getAllAtmsBalance();
        atm1.depositMoney(Banknotes.TEN, 2);
        atm2.depositMoney(Banknotes.FIVE_THOUSAND, 2);
        logger.info("Balance after adding banknotes ");
        department.getAllAtmsBalance();
        department.restoreAtms();
        logger.info("Balance after recovery ");
        department.getAllAtmsBalance();
    }
}
