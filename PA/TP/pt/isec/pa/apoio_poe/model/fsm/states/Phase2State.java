package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.ApoioPOEData;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPOEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPOEState;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPOEStateAdapter;

public class Phase2State extends ApoioPOEStateAdapter {

  public Phase2State(ApoioPOEContext context, ApoioPOEData data) {
    super(context, data);
  }

  @Override
  public ApoioPOEState getState() {
    return ApoioPOEState.PHASE2;
  }
  @Override
  public boolean previous(boolean[] closedPhases) {
    if (!closedPhases[0]) {
      changeState(ApoioPOEState.PHASE1);
      return true;
    }
    return false;
  }
  @Override
  public boolean backToStart() {
    changeState(ApoioPOEState.START);

    return true;
  }
  @Override
  public boolean nextNoClose() {
    changeState(ApoioPOEState.PHASE3);

    return true;
  }
  @Override
  public boolean nextAndClose() {
    setClose(1);
    changeState(ApoioPOEState.PHASE3);

    return true;
  }
  
}
