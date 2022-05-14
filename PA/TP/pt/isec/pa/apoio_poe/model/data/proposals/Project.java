package pt.isec.pa.apoio_poe.model.data.proposals;

import pt.isec.pa.apoio_poe.model.data.Teacher;

public class Project extends Proposal {
  private Teacher docenteProponente;
  private String ramos;

  public Project(String id, String titulo, String ramos, long nEstudante, Teacher docente) {
    super(id, titulo, nEstudante);
    this.docenteProponente = docente;
    this.ramos = ramos;
  }

  public Teacher getDocenteProponente() {
    return docenteProponente;
  }

  @Override
  public String toStringFicheiro() {
    StringBuilder sb = new StringBuilder();

    sb.append("T2,");
    sb.append(id + ',');
    sb.append(ramos + ',');
    sb.append(titulo + ',');
    sb.append(docenteProponente.getEmail());
    if (nEstudante != 0L) {
      sb.append(',' + nEstudante);
    }

    return sb.toString();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (nEstudante != 0L) {
      sb.append("T2" + "," + id + "," + ramos + ","
          + titulo + "," + docenteProponente.getEmail() + "," + nEstudante);
    } else {
      sb.append("T2" + "," + id + "," + ramos + ","
          + titulo + "," + docenteProponente.getEmail());
    }
    return sb.toString();
  }
}
