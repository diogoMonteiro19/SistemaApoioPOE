package pt.isec.pa.apoio_poe.model;

import pt.isec.pa.apoio_poe.model.fsm.ApoioPOEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPOEState;
import pt.isec.pa.apoio_poe.model.fsm.IApoioPOEState;
import pt.isec.pa.apoio_poe.model.fsm.Mode;

import java.io.*;

public class ApoioPOEManager {
  private ApoioPOEContext context;
  private final String FILE_NAME = "apoiopoe.bin";

  public ApoioPOEManager() {
    this.context = new ApoioPOEContext();
  }

  public void changeState(IApoioPOEState newState) {
    context.changeState(newState);
  }

  public ApoioPOEState getState() {
    return context.getState();
  }
  
  public void setClose(int index) {
    context.setClose(index);
  }

  //fase 1 -----------------------------------------------------------------------------------------------
  public Mode getModo() {
    return context.getModo();
  }

  public void setModoEstudante() {
    context.setModoEstudante();
  }

  public void setModoDocente() {
    context.setModoDocente();
  }

  public void setModoProposta() {
    context.setModoProposta();
  }

  public void unsetModo() {
    context.unsetModo();
  }

  public void lerCSV(String fileName) throws IOException {
    context.lerCSV(fileName);
  }

  public void escreverCSV(String fileName) {
    context.escreverCSV(fileName);
  }

  public String consultar() {
    return context.consultar();
  }

  //fase 2--------------------------------------------------------------------------------------------------------------
  public void lerCSVCandidaturas(String fileName) throws IOException {
    context.lerCSVCandidaturas(fileName);
  }

  public void escreveFicheiroCSVCandidaturas(String fileName) {
    context.escreveFicheiroCSVCandidaturas(fileName);
  }

  public String consultarCandidaturas() {
    return context.consultarCandidaturas();
  }

  public String getAlunos(int filtro) {
    return context.getAlunos(filtro);
  }

  public String getPropostasFiltradasPhase2(int index) {
    return context.getPropostasFiltradasPhase2(index);
  }

  //fase 3 ---------------------------------------------------------------------------------------------------
  public void atribuicaoAutomaticaBasica() {
    context.atribuicaoAutomaticaBasica();
  }

  public void atribuicaoAutomaticaPorNota() {
    context.atribuicaoAutomaticaPorNota();
  }

  public String mostraAlunosComAutoPropostaAss() {
    return context.mostraAlunosComAutoPropostaAss();
  }

  public String mostraAlunosCandidaturaRegistada() {
    return context.mostraAlunosCandidaturaRegistada();
  }

  public String mostraAlunosPropAtr() {
    return context.mostraAlunosPropAtr();
  }

  public String mostraAlunosSemPropAtr() {
    return context.mostraAlunosSemPropAtr();
  }

  public String getPropostasFiltradasPhase3(int i) {
    return context.getPropostasFiltradasPhase3(i);
  }

  public void exportaAtribuicoesParaCSV(String fileName) {
    context.exportaAtribuicoesParaCSV(fileName);
  }

  //fase 4 -------------------------------------------------------------------------------------------------------------------
  public void associacaoAutoDocentes() {
    context.associcaoAutoDocentes();
  }

  public String getEstudPropOrientador() {
    return context.getEstudPropOrientador();
  }

  public String getEstudPropSOrientador() {
    return context.getEstudPropSOrientador();
  }

  public String estatisticasGerais() {
    return context.estatisticasGerais();
  }

  public String estatisticaEspecifica(String email) {
    return context.estatisticaEspecifica(email);
  }

  // fase 5 ----------------------------------------------------------------------------------------------------------
  public String consultaPhase5(int tipo) {
    return context.consultaPhase5(tipo);
  }
  
  //transições de estado ------------------------------------------------------------------------
  public boolean nextAndClose() {
    return context.nextAndClose();
  }

  public boolean nextNoClose() {
    return context.nextNoClose();
  }

  public boolean previous() {
    return context.previous();
  }

  public boolean backToStart() {
    return context.backToStart();
  }

  public boolean goToPhase(int fase) {
    return context.goToPhase(fase);
  }

  //save e load -----------------------------------------------------------------------------------
  public boolean save() {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
      oos.writeObject(context);

      return true;
    } catch (Exception e) {
      System.out.println("Erro a guardar!" + e);
    }

    return false;
  }

  public boolean load() {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
      context = (ApoioPOEContext) ois.readObject();

      return true;
    } catch (Exception e) {
      System.out.println("Erro a carregar!");
    }

    return false;
  }
}
