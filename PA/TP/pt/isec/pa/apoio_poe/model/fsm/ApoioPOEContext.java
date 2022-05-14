package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;
import pt.isec.pa.apoio_poe.model.data.proposals.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static pt.isec.pa.apoio_poe.model.fsm.ApoioPOEState.*;

public class ApoioPOEContext implements Serializable {
  private static final long serialVersionUID = 1L;
  private IApoioPOEState state;
  private ApoioPOEData data;
  private Mode modo;
  private boolean[] closedPhases;
  private boolean[] filtrosPropostas;

  public ApoioPOEContext() {
    this.data = new ApoioPOEData();
    this.state = START.createState(this, data);
    this.modo = null;
    this.closedPhases = new boolean[] { false, false, false };
    this.filtrosPropostas = new boolean[] { false, false, false, false };
  }

  public void changeState(IApoioPOEState newState) {
    state = newState;
  }

  public ApoioPOEState getState() {
    if (state == null) {
      return null;
    }

    return state.getState();
  }
  
  public void setClose(int index) {
    closedPhases[index] = true;
  }

  // fase 1 ----------------------------------------------------------------------------------------------------------------------
  public Mode getModo() {
    return modo;
  }

  public void setModoEstudante() {
    modo = Mode.STUDENT;
  }

  public void setModoDocente() {
    modo = Mode.TEACHER;
  }

  public void setModoProposta() {
    modo = Mode.PROPOSITION;
  }

  public void unsetModo() {
    modo = null;
  }

  public void lerCSV(String fileName) throws IOException {
    BufferedReader br = null;
    try {
      FileReader fr = new FileReader(fileName);
      br = new BufferedReader(fr);
      String line = " ";
      String[] tempArr;
      switch (modo) {
        case PROPOSITION:
          while ((line = br.readLine()) != null) {
            tempArr = line.split(",");
            String id = tempArr[1];

            if (tempArr[0].equalsIgnoreCase("T1")) {
              String ramos = tempArr[2];
              String titulo = tempArr[3];
              String entidade = tempArr[4];
              long nEstudante = 0L;
              if (tempArr.length > 5) {
                nEstudante = Long.parseLong(tempArr[5]);
              }
              data.adicionaProposta(new Internship(id, titulo, ramos, nEstudante, entidade));
            }
            if (tempArr[0].equalsIgnoreCase("T2")) {
              if (data.findTeacher(tempArr[4]) != null) {
                String titulo = tempArr[3];
                long nEstudante = 0L;
                if (tempArr.length > 5) {
                  nEstudante = Long.parseLong(tempArr[5]);
                }
                String ramos = tempArr[2];
                data.adicionaProposta(new Project(id, titulo, ramos, nEstudante, data.findTeacher(tempArr[4])));
              }
            }
            if (tempArr[0].equalsIgnoreCase("T3")) {
              if (data.findStudent(Long.parseLong(tempArr[3])) != null) {
                String titulo = tempArr[2];
                data.adicionaProposta(new AutoProposed(id, titulo, Long.parseLong(tempArr[3]),
                    data.findStudent(Long.parseLong(tempArr[3]))));
              }
            }
          }

          break;
        case STUDENT:
          while ((line = br.readLine()) != null) {
            tempArr = line.split(",");
            if (tempArr.length != 7) {
              return;
            }
            try {
              long nEstudante = Long.parseLong(tempArr[0]);
              String nome = tempArr[1];
              String email = tempArr[2];
              String siglaCurso = tempArr[3];
              String siglaRamo = tempArr[4];
              double classificacao = Double.parseDouble(tempArr[5]);
              boolean possAceder = Boolean.parseBoolean(tempArr[6]);

              data.adicionaAluno(new Student(nEstudante, nome, email, siglaCurso, siglaRamo, classificacao, possAceder));
            } catch (NumberFormatException e) {
              return;
            }
          }

          break;
        case TEACHER:
          while ((line = br.readLine()) != null) {
            tempArr = line.split(",");
            if (tempArr.length != 2) {
              return;
            }
            String nome = tempArr[0];
            String email = tempArr[1];

            data.adicionaDocente(new Teacher(nome, email));
          }
          break;
        default:
          break;
      }
    } catch (FileNotFoundException e) {
      System.out.println("Erro a ler ficheiro: " + e);
    } finally {
      if (br != null)
        br.close();
    }
  }

  public void escreverCSV(String fileName) {
    PrintWriter pw = null;
    try {
      File file = new File(fileName);
      FileWriter fw = new FileWriter(file);
      BufferedWriter bw = new BufferedWriter(fw);
      pw = new PrintWriter(bw);

      switch (modo) {
        case PROPOSITION:
          for (Proposal p : data.getPropostas()) {
            pw.println(p.toStringFicheiro());
          }
          break;
        case STUDENT:
          for (Student s : data.getAlunos()) {
            pw.println(s.toStringFicheiro());
          }
          break;
        case TEACHER:
          for (Teacher t : data.getDocentes()) {
            pw.println(t.toStringFicheiro());
          }
          break;
      }
    } catch (IOException e) {
      return;
    } finally {
      if (pw != null)
        pw.close();
    }
  }

  public String consultar() {
    StringBuilder sb = new StringBuilder();

    switch (modo) {
      case PROPOSITION:
        for (Proposal p : data.getPropostas()) {
          sb.append(p.toString() + System.lineSeparator());
        }
        break;
      case STUDENT:
        for (Student a : data.getAlunos()) {
          sb.append(a.toString() + System.lineSeparator());
        }
        break;
      case TEACHER:
        for (Teacher t : data.getDocentes()) {
          sb.append(t.toString() + System.lineSeparator());
        }
        break;
      default:
        break;
    }

    return sb.toString();
  }

  /*
   * public String adicionaManualmenteStudent(long nEstudante, String nome, String
   * email, String siglaCurso,
   * String siglaRamo, double classificacao, boolean possAceder) {
   * 
   * Student novo = new Student(nEstudante, nome, email, siglaCurso,
   * siglaRamo, classificacao, possAceder);
   * if(data.adicionaAluno(novo)){
   * return "Aluno com o numero "+nEstudante+" adicionado com sucesso";
   * }
   * else{
   * return "Erro ao adicionar aluno!!";
   * }
   * }
   * public String adicionaManualmenteTeacher(String nome,String email){
   * 
   * Teacher novo = new Teacher(nome, email);
   * if(data.adicionaDocente(novo)){
   * return "Docente com o email " + email+" adicionado com sucesso";
   * }
   * else{
   * return "Erro ao adicionar Docente com email:"+email;
   * }
   * }
   * 
   * public String adicionaManualmenteEstagio(String id, String titulo, String
   * ramos, long nEstudante, String entidade) {
   * Internship novo = new Internship(id,titulo,ramos,nEstudante,entidade);
   * if(data.adicionaProposta(novo)){
   * return "Proposta com id " +id+" adicoinada com sucesso\n";
   * }else {
   * return "Erro ao adicionar proposta:"+id+"\n";
   * }
   * }
   * 
   * public String adicionaManualmenteProject(String id, String titulo, String
   * ramos, long nEstudante, String emailDocente) {
   * Project novo = new
   * Project(id,titulo,ramos,nEstudante,data.findTeacher(emailDocente));
   * if(data.adicionaProposta(novo)){
   * return "Proposta com id " +id+" adicoinada com sucesso\n";
   * }else {
   * return "Erro ao adicionar proposta:"+id+"\n";
   * }
   * }
   * public String adicionaManualmenteAutoProposed(String id, String titulo,long
   * nEstudante) {
   * AutoProposed novo = new AutoProposed(id,
   * titulo,nEstudante,data.findStudent(nEstudante));
   * if (data.adicionaProposta(novo)) {
   * return "Proposta com id " + id + " adicoinada com sucesso\n";
   * } else {
   * return "Erro ao adicionar proposta:" + id + "\n";
   * }
   * }
   */


  
  // fase 2 --------------------------------------------------------------------------------------------------------------------------
  public void lerCSVCandidaturas(String fileName) throws IOException {
    BufferedReader br = null;
    try {
      FileReader fr = new FileReader(fileName);
      br = new BufferedReader(fr);
      String line = " ";
      String[] tempArr;

      while ((line = br.readLine()) != null) {
        tempArr = line.split(",");
        if (data.findStudent(Long.parseLong(tempArr[0])) != null) {
          long nEstudante = Long.parseLong(tempArr[0]);
          String[] propostas = new String[tempArr.length - 1];
          for (int i = 1; i < tempArr.length; i++) {
            if (data.findProposal(tempArr[i]) == null) {
              break;
            }
            propostas[i - 1] = tempArr[i];
          }

          data.adicionaCandidatura(nEstudante, propostas);
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("Erro a ler ficheiro: " + e);
    } finally {
      if (br != null)
        br.close();
    }
  }

  public void escreveFicheiroCSVCandidaturas (String fileName) {
    PrintWriter pw = null;
    try {
      File file = new File(fileName);
      FileWriter fw = new FileWriter(file);
      BufferedWriter bw = new BufferedWriter(fw);
      pw = new PrintWriter(bw);

      for (Application a : data.getCandidaturas()) {
        pw.println(a.toStringFicheiro());
      }
    } catch (IOException e) {
      return;
    } finally {
      if (pw != null)
        pw.close();
    }
  }

  public String consultarCandidaturas() {
    StringBuilder sb = new StringBuilder();

    for (Application a : data.getCandidaturas()) {
      sb.append(a.toString() + System.lineSeparator());
    }

    return sb.toString();
  }

  public String getAlunos(int filtro) {
    StringBuilder sb = new StringBuilder();

    switch (filtro) {
      case 1:
        for (Proposal p : data.getPropostas()) {
          if (p instanceof AutoProposed) {
            AutoProposed temp = (AutoProposed) p;
            sb.append(temp.getAluno().toString() + System.lineSeparator());
          }
        }
        break;
      case 2:
        for (Student s : getAlunosCandidatados()) {
          sb.append(s.toString() + System.lineSeparator());
        }
        break;
      case 3:
        List<Student> temp = data.getAlunos();
        temp.removeAll(getAlunosCandidatados());
        for (Student s : temp) {
          sb.append(s.toString() + System.lineSeparator());
        }
        break;
    }

    return sb.toString();
  }

  private List<Student> getAlunosCandidatados() {
    List<Student> students = new ArrayList<>();

    for (Application a : data.getCandidaturas()) {
      students.add(a.getAluno());
    }

    return students;
  }

  public String getPropostasFiltradasPhase2(int index) {
    StringBuilder sb = new StringBuilder();

    if (index > 0) {
      switch (index) {
        case 1 -> filtrosPropostas[0] = !filtrosPropostas[0];
        case 2 -> filtrosPropostas[1] = !filtrosPropostas[1];
        case 3 -> filtrosPropostas[2] = !filtrosPropostas[2];
        case 4 -> filtrosPropostas[3] = !filtrosPropostas[3];
        case 5 -> {
          for (int i = 0; i < filtrosPropostas.length; i++) {
            filtrosPropostas[i] = false;
          }
        }
      }
    }
    List<Proposal> temp = new ArrayList<>(data.getPropostas());

    if (filtrosPropostas[0]) {
      List<Proposal> remover = new ArrayList<>();
      for (Proposal p : temp) {
        if (!(p instanceof AutoProposed)) {
          remover.add(p);
        }
      }
      temp.removeAll(remover);
    }
    if (filtrosPropostas[1]) {
      List<Proposal> remover = new ArrayList<>();
      for (Proposal p : temp) {
        if (!(p instanceof Project)) {
          remover.add(p);
        }
      }
      temp.removeAll(remover);
    }
    if (filtrosPropostas[2]) {
      List<Proposal> aux = new ArrayList<>();
      for (Application a : data.getCandidaturas()) {
        for (Proposal p : a.getPropostas()) {
          if (!aux.contains(p)) {
            aux.add(p);
          }
        }
      }
      List<Proposal> aux2 = new ArrayList<>(temp);
      temp.removeAll(aux);
      aux2.removeAll(temp);
      temp = aux2;
    }
    if (filtrosPropostas[3]) {
      List<Proposal> aux = new ArrayList<>();
      for (Application a : data.getCandidaturas()) {
        for (Proposal p : a.getPropostas()) {
          if (!aux.contains(p)) {
            aux.add(p);
          }
        }
      }
      temp.removeAll(aux);
    }

    for (Proposal p : temp) {
      sb.append(p.toString() + System.lineSeparator());
    }

    return sb.toString();
  }

  //fase 3 -----------------------------------------------------------------------------------------------------------------------
  public void atribuicaoAutomaticaBasica() {
    for (Proposal p : data.getPropostas()) {
      if (p instanceof AutoProposed) {
        data.findStudent(p.getnEstudante()).associaProposta(p, 1);
      }
      if (p instanceof Project) {
        if (p.getnEstudante() != 0L) {
          data.findStudent(p.getnEstudante()).associaProposta(p, 1);
        }
      }
    }
  }
  
  public void atribuicaoAutomaticaPorNota() {
    if (closedPhases[1]) {
      List<Student> listaAlunos = new ArrayList<>(data.getAlunosCandidaturasSorted());
      for (Student s : listaAlunos) {
        Application aux = data.findApplication(s);
        for (int i = 0; i < aux.getPropostas().size(); i++) {
          if (aux.getPropostas().get(i).getnEstudante() == 0L) {
            s.associaProposta(aux.getPropostas().get(i), i);
            aux.getPropostas().get(i).setnEstudante(s.getnEstudante());
          }
        }
      }
    }
  }

  public String mostraAlunosComAutoPropostaAss() {
    StringBuilder sb = new StringBuilder();
    for (Proposal p : data.getPropostas()) {
      if (p instanceof AutoProposed) {
        sb.append(((AutoProposed) p).getAluno().toString() + System.lineSeparator());
      }
    }
    return sb.toString();
  }

  public String mostraAlunosCandidaturaRegistada() {
    StringBuilder sb = new StringBuilder();
    for (Application a : data.getCandidaturas()) {
      sb.append(a.getAluno().toString() + System.lineSeparator());
    }
    return sb.toString();
  }

  public String mostraAlunosPropAtr() {
    StringBuilder sb = new StringBuilder();
    for (Student s : data.getAlunos()) {
      if (s.getProposta() != null) {
        sb.append(s.toString() + "," + s.getOrdemPreferencia() + System.lineSeparator());
      }
    }
    return sb.toString();
  }

  public String mostraAlunosSemPropAtr() {
    StringBuilder sb = new StringBuilder();
    for (Student s : data.getAlunos()) {
      if (s.getProposta() == null) {
        sb.append(s.toString() + System.lineSeparator());
      }
    }
    return sb.toString();
  }
  
  public String getPropostasFiltradasPhase3(int index) {
    StringBuilder sb = new StringBuilder();

    if (index > 0) {
      switch (index) {
        case 1 -> filtrosPropostas[0] = !filtrosPropostas[0];
        case 2 -> filtrosPropostas[1] = !filtrosPropostas[1];
        case 3 -> filtrosPropostas[2] = !filtrosPropostas[2];
        case 4 -> filtrosPropostas[3] = !filtrosPropostas[3];
        case 5 -> {
          for (int i = 0; i < filtrosPropostas.length; i++) {
            filtrosPropostas[i] = false;
          }
        }
      }
    }
    List<Proposal> temp = new ArrayList<>(data.getPropostas());

    if (filtrosPropostas[0]) {
      List<Proposal> remover = new ArrayList<>();
      for (Proposal p : temp) {
        if (!(p instanceof AutoProposed)) {
          remover.add(p);
        }
      }
      temp.removeAll(remover);
    }
    if (filtrosPropostas[1]) {
      List<Proposal> remover = new ArrayList<>();
      for (Proposal p : temp) {
        if (!(p instanceof Project)) {
          remover.add(p);
        }
      }
      temp.removeAll(remover);
    }
    if (filtrosPropostas[2]) {
      List<Proposal> aux = new ArrayList<>();
      for (Proposal p : data.getPropostas()) {
        if (p.getnEstudante() != 0L) {
          aux.add(p);
        }
      }
      temp.removeAll(aux);
    }
    if (filtrosPropostas[3]) {
      List<Proposal> aux = new ArrayList<>();
      for (Proposal p : data.getPropostas()) {
        if (p.getnEstudante() == 0L) {
          aux.add(p);
        }
      }
      temp.removeAll(aux);
    }

    for (Proposal p : temp) {
      sb.append(p.toString() + System.lineSeparator());
    }

    return sb.toString();
  }

  public void exportaAtribuicoesParaCSV(String fileName) {
    PrintWriter pw = null;
    try {
      File file = new File(fileName);
      FileWriter fw = new FileWriter(file);
      BufferedWriter bw = new BufferedWriter(fw);
      pw = new PrintWriter(bw);

      for (Student s : data.getAlunos()) {
        if (s.getProposta() != null) {
          pw.print("N Aluno: " + s.getnEstudante() + ", Nome: " + s.getNome() + ", Proposta: " + s.getProposta().getId() + 
          ", Ordem Preferencia: " + s.getOrdemPreferencia());
          Application app = data.findApplication(s);
          if (app != null) {
            pw.print(", Candidaturas: ");
            for(int i = 0; i < app.getPropostas().size(); i++) {
              if (app.getPropostas().get(i).getId() != s.getProposta().getId()) {
                pw.print(app.getPropostas().get(i).getId());
                if (i < app.getPropostas().size() - 1) {
                  pw.print(',');
                }
              }
            }
          }
          pw.println();
        }
      }
    } catch (IOException e) {
      return;
    } finally {
      if (pw != null)
        pw.close();
    }
  }

  // fase 4 -------------------------------------------------------------------------------------------------------------------
  public void associcaoAutoDocentes() {
    for (Proposal p : data.getPropostas()) {
      if (p instanceof Project) {
        Project aux = (Project) p;
        aux.getDocenteProponente().addProposta(p);
        p.setTeacherEmail(aux.getDocenteProponente().getEmail());
      }
    }
  }

  public String getEstudPropOrientador() {
    StringBuilder sb = new StringBuilder();
    for (Student s : data.getAlunos()) {
      if (s.getProposta() != null) {
        if (s.getProposta().getTeacherEmail() != null) {
          sb.append(s.getProposta().toString() + " || "
              + data.findTeacher(s.getProposta().getTeacherEmail()).toString() + System.lineSeparator());
        }
      }
    }
    return sb.toString();
  }

  public String getEstudPropSOrientador() {
    StringBuilder sb = new StringBuilder();
    for (Student s : data.getAlunos()) {
      if (s.getProposta() != null) {
        if (s.getProposta().getTeacherEmail() == null) {
          sb.append(s.getProposta().toString() + System.lineSeparator());
        }
      }
    }
    return sb.toString();
  }

  public String estatisticasGerais() {
    StringBuilder sb = new StringBuilder();
    int maior = 0, menor = 0;
    float media = 0;
    int nrProf = 0;
    for (int i = 0; i < data.getDocentes().size(); i++) {
      int size = data.getDocentes().get(i).getPropostasAtribuidas().size();
      if (size > 0) {
        if (size > maior) {
          maior = size;
        }
        if (size < menor) {
          menor = size;
        }
        nrProf++;
        media += size;
      }
    }
    media /= nrProf;

    sb.append("Media de orientacoes por docente: " + media + ", Minimo: " + menor + ", Maior: " + maior
        + System.lineSeparator());
    return sb.toString();
  }

  public String estatisticaEspecifica(String email) {
    StringBuilder sb = new StringBuilder();
    Teacher stor = data.findTeacher(email);
    if (stor != null) {
      sb.append("Numero de orientacoes: " + stor.getPropostasAtribuidas().size());
    } else {
      sb.append("Docente nao existe");
    }
    return sb.toString();
  }

  //fase 5 -----------------------------------------------------------------------------------------------------------------------
  public String consultaPhase5(int tipo) {
    StringBuilder sb = new StringBuilder();
    switch (tipo) {
      case 1:
        for (Student s : data.getAlunos()) {
          if (s.getProposta() != null) {
            sb.append(s.toString() + "||" + s.getProposta().toString() + System.lineSeparator());
          }
        }
        return sb.toString();
      case 2:
        for (Student s : data.getAlunos()) {
          if (s.getProposta() == null) {
            for (Application a : data.getCandidaturas()) {
              if (a.getAluno().equals(s)) {
                sb.append(s.toString() + a.getPropostas().toString() + System.lineSeparator());
              }
            }
          }
        }
        return sb.toString();
      case 3:
        for (Proposal p : data.getPropostas()) {
          if (p.getnEstudante() == 0L) {
            sb.append(p.toString() + System.lineSeparator());
          }
        }
        return sb.toString();
      case 4:
        for (Proposal p : data.getPropostas()) {
          if (p.getnEstudante() != 0L) {
            sb.append(p.toString() + System.lineSeparator());
          }
        }
        return sb.toString();
      case 5:
        return estatisticasGerais();
    }
    return sb.toString();
  }

  // transições de estado -------------------------------------------------------------------------------------------------------------
  public boolean nextAndClose() {
    return state.nextAndClose();
  }

  public boolean nextNoClose() {
    return state.nextNoClose();
  }

  public boolean previous() {
    return state.previous(closedPhases);
  }

  public boolean backToStart() {
    return state.backToStart();
  }

  public boolean goToPhase(int fase) {
    return state.goToPhase(fase, closedPhases);
  }
}