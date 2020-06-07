package department.memento;

class Memento {
    private final StateAtm stateAtm;

    Memento(StateAtm stateAtm) {
        this.stateAtm = new StateAtm(stateAtm);
    }

    StateAtm getState() {
        return stateAtm;
    }
}
