package pt.isec.pa.apoio_poe.model.data;

import pt.isec.pa.apoio_poe.model.data.proposals.Proposal;

import java.io.Serializable;

public class Student implements Serializable {
  private static final long serialVersionUID = 1L;
  private long nEstudante;
  private String nome;
  private String email;
  private String siglaCurso;
  private String siglaRamo;
  private double classificacao;
  private boolean possAceder;
  private Proposal proposta;
  private int ordemPreferencia;

  public Student(long nEstudante, String nome, String email, String siglaCurso,
      String siglaRamo, double classificacao, boolean possAceder) {
    this.nEstudante = nEstudante;
    this.nome = nome;
    this.email = email;
    this.siglaCurso = siglaCurso;
    this.siglaRamo = siglaRamo;
    this.classificacao = classificacao;
    this.possAceder = possAceder;
    proposta = null;
    ordemPreferencia = 0;
  }

  private Student(long nEstudante) {
    this.nEstudante = nEstudante;
  }

  static public Student getDummyStudent(long nEstudante) {
    return new Student(nEstudante);
  }

  public void associaProposta(Proposal proposta, int preferencia) {
    ordemPreferencia = preferencia;
    this.proposta = proposta;
  }

  public Proposal getProposta() {
    return proposta;
  }

  public double getClassificacao() {
    return classificacao;
  }

  public int getOrdemPreferencia() {
    return ordemPreferencia;
  }

  public long getnEstudante() {
    return nEstudante;
  }

  public String getNome() {
    return nome;
  }

  public String toStringFicheiro() {
    StringBuilder sb = new StringBuilder();

    sb.append(nEstudante).append(',');
    sb.append(nome + ',');
    sb.append(email + ',');
    sb.append(siglaCurso + ',');
    sb.append(siglaRamo + ',');
    sb.append(classificacao + ',');
    sb.append(possAceder);

    return sb.toString();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("Numero: " + nEstudante + ", ");
    sb.append("Nome: " + nome + ", ");
    sb.append("Email: " + email + ", ");
    sb.append("Curso: " + siglaCurso + ", ");
    sb.append("Ramo: " + siglaRamo + ", ");
    sb.append("Classificacao: " + classificacao + ", ");
    sb.append("Pode aceder: " + possAceder);

    return sb.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (!(obj instanceof Student)) {
      return false;
    }

    Student other = (Student) obj;

    if (this.nEstudante == other.nEstudante) {
      return true;
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Long.valueOf(nEstudante).intValue();
  }
}