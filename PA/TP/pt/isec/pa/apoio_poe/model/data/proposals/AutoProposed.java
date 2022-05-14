package pt.isec.pa.apoio_poe.model.data.proposals;

import pt.isec.pa.apoio_poe.model.data.Student;

public class AutoProposed extends Proposal {
  private Student alunoProponente;

  public AutoProposed(String id, String titulo, long nEstudante, Student aluno) {
    super(id, titulo, nEstudante);
    this.alunoProponente = aluno;
  }

  @Override
  public String toStringFicheiro() {
    StringBuilder sb = new StringBuilder();

    sb.append("T2,");
    sb.append(id + ',');
    sb.append(titulo + ',');
    sb.append(alunoProponente.getnEstudante());

    return sb.toString();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("T3" + "," + id + ","
        + titulo + " || " + alunoProponente);
    return sb.toString();
  }

  public Student getAluno() {
    return alunoProponente;
  }
}
