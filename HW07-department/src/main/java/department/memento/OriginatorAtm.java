package department.memento;

import java.util.ArrayDeque;
import java.util.Deque;

public class OriginatorAtm {

  private final Deque<Memento> stack = new ArrayDeque<>();

  public void saveState(StateAtm stateAtm) {
    stack.push(new Memento(stateAtm));
  }

  public StateAtm restoreState() {
    return stack.pop().getState();
  }
}
