package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.ApoioPOEData;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPOEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPOEState;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPOEStateAdapter;

public class Phase5State extends ApoioPOEStateAdapter {
  public Phase5State(ApoioPOEContext context, ApoioPOEData data) {
    super(context, data);
  }

  @Override
  public ApoioPOEState getState() {
    return ApoioPOEState.PHASE5;
  }

  @Override
  public boolean backToStart() {
    changeState(ApoioPOEState.START);

    return true;
  }
}
