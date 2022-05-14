package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.ApoioPOEData;

import java.io.Serializable;

public abstract class ApoioPOEStateAdapter implements IApoioPOEState, Serializable {
  private static final long serialVersionUID = 1L;
  private ApoioPOEContext context;
  private ApoioPOEData data;

  public ApoioPOEStateAdapter(ApoioPOEContext context, ApoioPOEData data) {
    this.context = context;
    this.data = data;
  }

  protected void changeState(ApoioPOEState newState) {
    context.changeState(newState.createState(context, data));
  }

  public void setClose(int index) {
    context.setClose(index);
  }

  @Override
  public boolean nextAndClose() {
    return false;
  }

  @Override
  public boolean nextNoClose() {
    return false;
  }

  @Override
  public boolean previous(boolean[] closedPhases) {
    return false;
  }

  @Override
  public boolean backToStart() {
    return false;
  }

  @Override
  public boolean goToPhase(int fase, boolean[] closedPhases) {
    return false;
  }
}
