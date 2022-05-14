package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.ApoioPOEData;
import pt.isec.pa.apoio_poe.model.fsm.states.*;

public enum ApoioPOEState {
  PHASE1, PHASE2, PHASE3, PHASE4, PHASE5, START;

  public IApoioPOEState createState(ApoioPOEContext context, ApoioPOEData data) {
    return switch (this) {
      case PHASE1 -> new Phase1State(context, data);
      case PHASE2 -> new Phase2State(context, data);
      case PHASE3 -> new Phase3State(context, data);
      case PHASE4 -> new Phase4State(context, data);
      case PHASE5 -> new Phase5State(context, data);
      case START -> new StartState(context, data);
      default -> null;
    };
  }
}
