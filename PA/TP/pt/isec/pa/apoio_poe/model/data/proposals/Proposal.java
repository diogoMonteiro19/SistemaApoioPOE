package pt.isec.pa.apoio_poe.model.data.proposals;

import java.io.Serializable;

public class Proposal implements Serializable {
  private static final long serialVersionUID = 1L;
  protected String id;
  protected String titulo;
  protected long nEstudante;
  protected String teacherEmail;

  public Proposal(String id, String titulo, long nEstudante) {
    this.id = id;
    this.titulo = titulo;
    this.nEstudante = nEstudante;
    teacherEmail = null;
  }

  private Proposal(String id) {
    this.id = id;
  }

  public static Proposal getDummyProposal(String id) {
    return new Proposal(id);
  }

  public String getId() {
    return id;
  }

  public long getnEstudante() {
    return nEstudante;
  }

  public void setnEstudante(long nEstudante) {
    this.nEstudante = nEstudante;
  }

  public String getTitulo() {
    return titulo;
  }

  public String toStringFicheiro() {
    return "";
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Proposal)) {
      return false;
    }

    Proposal other = (Proposal) obj;

    if (this.id.equalsIgnoreCase(other.id)) {
      return true;
    }

    return false;
  }

  @Override
  public int hashCode() {
    byte[] byteArray = this.id.getBytes();
    int sum = 0;

    for (int i = 0; i < byteArray.length; i++) {
      sum += byteArray[i];
    }

    return sum;
  }

  public void setTeacherEmail(String teacherEmail) {
    this.teacherEmail = teacherEmail;
  }

  public String getTeacherEmail() {
    return teacherEmail;
  }
}
