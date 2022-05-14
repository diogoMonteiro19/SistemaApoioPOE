package pt.isec.pa.apoio_poe.model.fsm;

public interface IApoioPOEState {
  // transições
  boolean nextAndClose();

  boolean nextNoClose();

  boolean previous(boolean[] closedPhases);

  boolean backToStart();

  boolean goToPhase(int fase, boolean[] closedPhases);

  ApoioPOEState getState();
}
