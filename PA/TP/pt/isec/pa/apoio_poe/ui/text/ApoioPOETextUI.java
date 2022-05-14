package pt.isec.pa.apoio_poe.ui.text;

import java.io.IOException;

import pt.isec.pa.apoio_poe.model.ApoioPOEManager;
import pt.isec.pa.apoio_poe.ui.utils.PAInput;

public class ApoioPOETextUI {
  private boolean finish;
  private ApoioPOEManager manager;

  public ApoioPOETextUI(ApoioPOEManager manager) {
    this.manager = manager;
    this.finish = false;
  }

  public void start() {
    while (!finish) {
      switch (manager.getState()) {
        case PHASE1:
          phase1UI();
          break;
        case PHASE2:
          phase2UI();
          break;
        case PHASE3:
          phase3UI();
          break;
        case PHASE4:
          phase4UI();
          break;
        case PHASE5:
          phase5UI();
          break;
        case START:
          startUI();
          break;
        default:
          break;
      }
    }
  }

  private void startUI() {
    switch (PAInput.chooseOption("O que fazer? (Start)", "Ir para fase 1", "Ir para fase 2", "Ir para fase 3", "Ir para fase 4",
        "Guardar", "Carregar", "Sair")) {
      case 1:
        manager.goToPhase(1);
        break;
      case 2:
        manager.goToPhase(2);
        break;
      case 3:
        manager.goToPhase(3);
        break;
      case 4:
        manager.goToPhase(4);
        break;
      case 5:
        manager.save();
        break;
      case 6:
        manager.load();
        break;
      case 7:
        finish = true;
        return;
    }
  }

  // fase 1 -------------------------------------------------------------------
  private void phase1UI() {
    if (manager.getModo() == null) {
      switch (PAInput.chooseOption("Configuracao (Fase 1):", "Estudante", "Docente", "Proposta",
          "Fechar e seguir", "Seguir sem fechar", "Voltar atras")) {
        case 1:
          manager.setModoEstudante();
          phase1ModeUI();
          break;
        case 2:
          manager.setModoDocente();
          phase1ModeUI();
          break;
        case 3:
          manager.setModoProposta();
          phase1ModeUI();
          break;
        case 4:
          manager.setClose(0);
          manager.nextAndClose();
          break;
        case 5:
          manager.nextNoClose();
          break;
        case 6:
          manager.backToStart();
          return;
      }
    } else
      phase1ModeUI();
  }

  private void phase1ModeUI() {
    switch (PAInput.chooseOption("O que fazer:", "Ler de ficheiro CSV", "Consultar", "Adicionar manualmente",
        "Escrever para ficheiro csv", "Voltar")) {
      case 1:
        String fileName = PAInput.readString("Nome do ficheiro: ", true);
        try {
          manager.lerCSV(fileName);
        } catch (IOException e) {
          System.out.println("Erro a ler ficheiro: " + e.getMessage());
        }
        break;
      case 2:
        System.out.println(manager.consultar());
        break;
      case 3:
        //manulamente
        break;
      case 4:
        manager.escreverCSV(PAInput.readString("Nome do ficheiro: ", true));
        break;
      case 5:
        manager.unsetModo();
        return;
    }
  }

  // fase 2 ---------------------------------------------------------
  private void phase2UI() {
    switch (PAInput.chooseOption("Candidaturas (Fase 2):", "Ler de ficheiro CSV", "Consultar candidaturas", "Consultar alunos",
        "Consultar propostas", "Adicionar manualmente", "Exportar para CSV", "Fechar e seguir", "Seguir sem fechar", "Voltar para Fase 1",
        "Voltar ao menu inicial")) {
      case 1:
        String fileName = PAInput.readString("Nome do ficheiro: ", true);
        try {
          manager.lerCSVCandidaturas(fileName);
        } catch (IOException e) {
          System.out.println("erro a ler ficheiro: " + e.getMessage());
        }
        break;
      case 2:
        System.out.println(manager.consultarCandidaturas());
        break;
      case 3:
        mostraAlunos();
        break;
      case 4:
        mostraPropostasPhase2();
        break;
      case 5:
        //manualmente
        break;
      case 6:
        manager.escreveFicheiroCSVCandidaturas(PAInput.readString("Nome do ficheiro: ", true));
        break;
      case 7:
        manager.setClose(1);
        manager.nextAndClose();
        break;
      case 8:
        manager.nextNoClose();
        break;
      case 9:
        manager.previous();
        break;
      case 10:
        manager.backToStart();
        break;
    }
  }

  private void mostraPropostasPhase2() {
    int i = 0;
    while (true) {
      System.out.println(manager.getPropostasFiltradasPhase2(i));
      switch (PAInput.chooseOption("Filtros:", "Autopropostas de alunos", "Propostas de docentes",
          "Propostas com candidaturas", "Propostas sem candidaturas", "Remover filtros", "Voltar")) {
        case 1:
          i = 1;
          break;
        case 2:
          i = 2;
          break;
        case 3:
          i = 3;
          break;
        case 4:
          i = 4;
          break;
        case 5:
          i = 5;
          break;
        case 6:
          manager.getPropostasFiltradasPhase2(5);
          return;
      }
    }
  }

  private void mostraAlunos() {
    while (true) {
      switch (PAInput.chooseOption("Filtros:", "Alunos com Autoproposta", "Alunos com candidatura Registada",
          "Alunos sem candidatura", "Voltar")) {
        case 1:
          System.out.println(manager.getAlunos(1));
          break;
        case 2:
          System.out.println(manager.getAlunos(2));
          break;
        case 3:
          System.out.println(manager.getAlunos(3));
          break;
        case 4:
          return;
      }
    }
  }

  //fase 3 ------------------------------------------------------------
  private void phase3UI() {
    switch (PAInput.chooseOption("Atribuicao de Propostas (Fase 3):", "Atribuicao automatica", "Atribuicao automatica por nota",
        "Atribuicao manual", "Remover atribuicao", "Consultar", "Exportar atribuicoes para CSV", "Fechar e seguir",
        "Seguir sem fechar", "Voltar para Fase 2", "Voltar ao menu inicial")) {
      case 1:
        manager.atribuicaoAutomaticaBasica();
        break;
      case 2:
        manager.atribuicaoAutomaticaPorNota();
        break;
      case 3:
        // manual
        break;
      case 4:
        // remover
        break;
      case 5:
        consulta();
        break;
      case 6:
        manager.exportaAtribuicoesParaCSV(PAInput.readString("Nome do ficheiro (CSV): ", true));
        break;
      case 7:
        manager.setClose(2);
        manager.nextAndClose();
        break;
      case 8:
        manager.nextNoClose();
        break;
      case 9:
        manager.previous();
        break;
      case 10:
        manager.backToStart();
        break;
    }
  }

  private void consulta() {
    while (true) {
      switch (PAInput.chooseOption("Consulta:", "Propostas", "Alunos", "Voltar")) {
        case 1:
          mostraPropostasphase3();
          break;
        case 2:
          consultaCandidaturas();
          break;
        case 3:
          return;
      }
    }
  }
  
  private void mostraPropostasphase3() {
    int i = 0;
    while (true) {
      System.out.println(manager.getPropostasFiltradasPhase3(i));
      switch (PAInput.chooseOption("Filtros:", "Autopropostas de alunos", "Propostas de docentes",
          "Propostas Disponiveis", "Propostas atribuidas", "Remover filtros", "Voltar")) {
        case 1:
          i = 1;
          break;
        case 2:
          i = 2;
          break;
        case 3:
          i = 3;
          break;
        case 4:
          i = 4;
          break;
        case 5:
          i = 5;
          break;
        case 6:
          return;
      }
    }
  }

  private void consultaCandidaturas() {
    while (true) {
      switch (PAInput.chooseOption("Consulta:", "Autoproposta associada", "Candidatura ja registada",
          "Proposta Atribuida", "Sem proposta atribuida", "Voltar")) {
        case 1:
          System.out.println(manager.mostraAlunosComAutoPropostaAss());
          break;
        case 2:
          System.out.println(manager.mostraAlunosCandidaturaRegistada());
          break;
        case 3:
          System.out.println(manager.mostraAlunosPropAtr());
          break;
        case 4:
          System.out.println(manager.mostraAlunosSemPropAtr());
          break;
        case 5:
          return;
      }
    }
  }

  //fase 4 ----------------------------------------------------------------
  private void phase4UI() {
    switch (PAInput.chooseOption("Atribuicao de Orientadores (Fase 4):", "Atribuicao automatica",
        "Consultar", "Atribuicao Manual", "Fechar e seguir", "Voltar", "Menu inicial")) {
      case 1:
        manager.associacaoAutoDocentes();
        break;
      case 2:
        consultaPhase4();
        break;
      case 3:
        //manualmente
        break;
      case 4:
        manager.nextAndClose();
        break;
      case 5:
        manager.previous();
        break;
      case 6:
        manager.backToStart();
        break;
    }
  }

  private void consultaPhase4() {
    while (true) {
      switch (PAInput.chooseOption("Consulta:", "Estudantes com proposta atribuida e orientador",
          "Estudantes com proposta atribuida sem orientador", "Estatisticas", "Voltar")) {
        case 1:
          System.out.println(manager.getEstudPropOrientador());
          break;
        case 2:
          System.out.println(manager.getEstudPropSOrientador());
          break;
        case 3:
          menuEstatisticas();
          break;
        case 4:
          return;
      }
    }
  }

  private void menuEstatisticas() {
    while (true) {
      System.out.println(manager.estatisticasGerais());
      switch (PAInput.chooseOption("Especificar docente:", "Sim", "Voltar")) {
        case 1:
          String email = PAInput.readString("Email do docente:", true);
          System.out.println(manager.estatisticaEspecifica(email));
          break;
        case 2:
          return;
      }
    }
  }

  //fase 5 ----------------------------------------------------------------
  private void phase5UI() {
    switch (PAInput.chooseOption("Consulta (Fase 5):", "Estudante com propostas", "Estudante sem propostas",
        "Propostas disponiveis", "Propostas Atribuidas", "Estatisticas Docentes", "Acabou")) {
      case 1:
        System.out.println(manager.consultaPhase5(1));
        break;
      case 2:
        System.out.println(manager.consultaPhase5(2));
        break;
      case 3:
        System.out.println(manager.consultaPhase5(3));
        break;
      case 4:
        System.out.println(manager.consultaPhase5(4));
        break;
      case 5:
        System.out.println(manager.consultaPhase5(5));
        break;
      case 6:
        manager.backToStart();
        break;
    }
  }
  /*
   * private void adicionaManualmente() {
   * switch (fsm.getModo()){
   * case STUDENT:
   * String nomeStudent = PAInput.readString("Nome:",false);
   * long numero = (long) PAInput.readNumber("Numero de Estudante:");
   * String emailStudent = PAInput.readString("Email:",true);
   * String curso = PAInput.readString("Curso(Sigla):",true);
   * String ramo = PAInput.readString("Ramo(Sigla):",true);
   * double classif = PAInput.readNumber("Classificacao:");
   * boolean podeAceder = false;
   * switch (PAInput.chooseOption("Pode aceder?","Sim","Nao")){
   * case 1:
   * podeAceder=true;
   * break;
   * case 2:
   * podeAceder = false;
   * break;
   * default:
   * break;
   * }
   * //fsm.adicionaManualmenteStudent(numero,nomeStudent,emailStudent,curso,ramo,
   * classif,podeAceder);
   * break;
   * case TEACHER:
   * String nomeTeacher = PAInput.readString("Nome:",false);
   * String emailTeacher = PAInput.readString("Email:",true);
   * //fsm.adicionaManualmenteTeacher(nomeTeacher,emailTeacher);
   * break;
   * case PROPOSITION:
   * String id = PAInput.readString("ID:",true);
   * String titulo = PAInput.readString("Titulo:",false);
   * switch
   * (PAInput.chooseOption("Tipo de proposta:","Estagio","Projeto","AutoProposta")
   * ){
   * case 1:
   * String ramosEstagio = PAInput.readString("Ramos(DA|SI|RAS):",true);
   * String entidade = PAInput.readString("Entidade de acolhimento:",false);
   * long nEstudanteESTAGIO = 0L;
   * //fsm.adicionaManualmenteEstagio(id,titulo,ramosEstagio,nEstudanteESTAGIO,
   * entidade);
   * break;
   * case 2:
   * String ramosProjeto = PAInput.readString("Ramos(DA|SI|RAS):",true);
   * String emailDocenteProp =
   * PAInput.readString("Email do docente Proponente:",true);
   * long nEstudanteProject =0L;
   * // fsm.adicionaManualmenteProject(id,titulo,ramosProjeto,nEstudanteProject,
   * emailDocenteProp);
   * break;
   * case 3:
   * long nEstudanteAutoProp =
   * (long)PAInput.readNumber("Numero de estudante proponente:");
   * //fsm.adicionaManualmenteAutoProposed(id,titulo,nEstudanteAutoProp);
   * break;
   * }
   * break;
   * default:
   * break;
   * }
   * }
   */
}
