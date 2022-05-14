package pt.isec.pa.apoio_poe.model.fsm.states;

import pt.isec.pa.apoio_poe.model.data.ApoioPOEData;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPOEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPOEState;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPOEStateAdapter;

public class StartState extends ApoioPOEStateAdapter {

  public StartState(ApoioPOEContext context, ApoioPOEData data) {
    super(context, data);
  }

  @Override
  public boolean goToPhase(int fase, boolean[] closedPhases) {
    switch (fase){
      case 1:
        if (!closedPhases[0])
          changeState(ApoioPOEState.PHASE1);
        break;
      case 2:
        if (!closedPhases[1])
          changeState(ApoioPOEState.PHASE2);
        break;
      case 3:
        if (!closedPhases[2])
          changeState(ApoioPOEState.PHASE3);
        break;
      case 4:
        changeState(ApoioPOEState.PHASE4);
        break;
    }

    return true;
  }
  
  @Override
  public ApoioPOEState getState() {
    return ApoioPOEState.START;
  }
  
}
