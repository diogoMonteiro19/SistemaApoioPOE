package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.ApoioPOEData;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPOEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPOEState;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPOEStateAdapter;

public class Phase3State extends ApoioPOEStateAdapter {


  public Phase3State(ApoioPOEContext context, ApoioPOEData data) {
    super(context, data);
  }

  @Override
  public ApoioPOEState getState() {
    return ApoioPOEState.PHASE3;
  }

  @Override
  public boolean nextAndClose() {
    setClose(2);
    changeState(ApoioPOEState.PHASE4);

    return true;
  }

  @Override
  public boolean nextNoClose() {
    changeState(ApoioPOEState.PHASE4);

    return true;
  }

  @Override
  public boolean previous(boolean[] closedPhases) {
    if (!closedPhases[1]) {
      changeState(ApoioPOEState.PHASE2);
      return true;
    }

    return false;
  }

  @Override
  public boolean backToStart() {
    changeState(ApoioPOEState.START);

    return true;
  }
  
}
