package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.ApoioPOEData;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPOEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPOEState;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPOEStateAdapter;

public class Phase4State extends ApoioPOEStateAdapter {


  public Phase4State(ApoioPOEContext context, ApoioPOEData data) {
    super(context, data);
  }

  @Override
  public ApoioPOEState getState() {
    return ApoioPOEState.PHASE4;
  }

  @Override
  public boolean nextAndClose() {
    changeState(ApoioPOEState.PHASE5);

    return true;
  }

  @Override
  public boolean previous(boolean[] closedPhases) {
    if (!closedPhases[2]) {
      changeState(ApoioPOEState.PHASE3);
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
