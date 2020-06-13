package department.domain.impl;

import atm.impl.AtmImpl;
import department.chain.AtmProc;
import department.domain.Department;
import department.memento.OriginatorAtm;
import department.memento.StateAtm;
import department.observer.EventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DepartmentImpl implements Department {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentImpl.class);
    private final List<AtmImpl> atms = new ArrayList<>();
    private final EventProducer producer = new EventProducer();
    private final List<OriginatorAtm> originatorList = new ArrayList<>();

    public DepartmentImpl(AtmImpl... atms) {
        for (AtmImpl atm : atms) {
            OriginatorAtm originatorAtm = new OriginatorAtm();
            originatorAtm.saveState(new StateAtm(atm));
            this.atms.add(atm);
            this.producer.addListener(atm);
            this.originatorList.add(originatorAtm);
        }
    }

    @Override
    public void collectAtms() {
        for (int i = 0; i < atms.size(); i++) {
            AtmProc current = atms.get(i);
            if (atms.size() > 1 && i != atms.size() - 1) {
                current.setNext(atms.get(i + 1));
            }
            current.process((AtmImpl) current);
            logger.info("Atm has been collect: " + atms.get(i));
        }
    }

    @Override
    public void getAllAtmsBalance() {
        producer.event();
    }

    @Override
    public void restoreAtms() {
        for (int i = 0; i < atms.size(); i++) {
            AtmImpl restoreAtm = originatorList.get(i).restoreState().getAtm();
            atms.get(i).setCells(restoreAtm.getCells());
        }
    }
}
