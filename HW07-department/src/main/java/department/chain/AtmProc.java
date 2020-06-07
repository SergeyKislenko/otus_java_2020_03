package department.chain;

import atm.impl.AtmImpl;

public abstract class AtmProc {
    private AtmProc next;

    private AtmProc getNext() {
        return next;
    }
    public void setNext(AtmProc next) {
        this.next = next;
    }

    public void process(AtmImpl atm) {
        if (getNext() != null) {
            getNext().process(atm);
        }
    }
}
