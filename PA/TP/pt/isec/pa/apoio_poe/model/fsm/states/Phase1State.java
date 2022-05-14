package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.ApoioPOEData;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPOEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPOEState;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPOEStateAdapter;

public class Phase1State extends ApoioPOEStateAdapter {

  public Phase1State(ApoioPOEContext context, ApoioPOEData data) {
    super(context, data);
  }

  @Override
  public ApoioPOEState getState() {
    return ApoioPOEState.PHASE1;
  }

  @Override
  public boolean nextAndClose() {
    setClose(0);
    changeState(ApoioPOEState.PHASE2);

    return true;
  }

  @Override
  public boolean nextNoClose() {
    changeState(ApoioPOEState.PHASE2);
    return true;
  }
  @Override
  public boolean backToStart() {
    changeState(ApoioPOEState.START);
    return true;
  }
}
