package pt.isec.pa.apoio_poe.model.data;

import pt.isec.pa.apoio_poe.model.data.proposals.Proposal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Application implements Serializable {
  private static final long serialVersionUID = 1L;
  private List<Proposal> propostas;
  private Student aluno;

  public Application(Student aluno, List<Proposal> propostas) {
    this.propostas = new ArrayList<>(propostas);
    this.aluno = aluno;
  }

  private Application(Student aluno) {
    this.aluno = aluno;
  }

  public List<Proposal> getPropostas() {
    return propostas;
  }

  public Student getAluno() {
    return aluno;
  }

  public static Application getDummyApplication(Student aluno) {
    return new Application(aluno);
  }

  public String toStringFicheiro() {
    StringBuilder sb = new StringBuilder();

    sb.append(aluno.getnEstudante() + ',');
    for (int i = 0; i < propostas.size(); i++) {
      sb.append(propostas.get(i).getId());
      if (i < propostas.size() - 1) {
        sb.append(',');
      }
    }
    sb.append(System.lineSeparator());

    return sb.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (!(obj instanceof Application)) {
      return false;
    }

    Application other = (Application) obj;

    if (this.aluno.getnEstudante() == other.aluno.getnEstudante()) {
      return true;
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Long.valueOf(aluno.getnEstudante()).intValue();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("N Aluno: " + aluno.getnEstudante() + "\tNome: " + aluno.getNome() + System.lineSeparator());
    for (Proposal p : propostas) {
      sb.append(p.toString() + System.lineSeparator());
    }
    sb.append(System.lineSeparator());

    return sb.toString();
  }
}
