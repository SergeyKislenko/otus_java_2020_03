package department.memento;

import atm.impl.AtmImpl;

public class StateAtm {

    private final AtmImpl atm;

    public StateAtm(AtmImpl atm) {
        this.atm = atm;
    }

    public StateAtm(StateAtm state) {
        this.atm = state.getAtm().getCopy();
    }

    public AtmImpl getAtm() {
        return atm;
    }

    @Override
    public String toString() {
        return "StateAtm{" +
                "atm=" + atm +
                '}';
    }
}
