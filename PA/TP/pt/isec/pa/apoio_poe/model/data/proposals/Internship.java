package pt.isec.pa.apoio_poe.model.data.proposals;

public class Internship extends Proposal {
  private String entidadeAcolhimento;
  String ramos;

  public Internship(String id, String titulo, String ramos, long nEstudante, String entidadeAcolhimento) {
    super(id, titulo, nEstudante);
    this.entidadeAcolhimento = entidadeAcolhimento;
    this.ramos = ramos;
  }

  @Override
  public String toStringFicheiro() {
    StringBuilder sb = new StringBuilder();

    sb.append("T2,");
    sb.append(id + ',');
    sb.append(ramos + ',');
    sb.append(titulo + ',');
    sb.append(entidadeAcolhimento);
    if (nEstudante != 0L) {
      sb.append(',' + nEstudante);
    }

    return sb.toString();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (nEstudante != 0) {
      sb.append("T1," + id + "," + ramos + ","
          + titulo + "," + entidadeAcolhimento + "," + nEstudante);
    } else {
      sb.append("T1," + id + "," + ramos + ","
          + titulo + "," + entidadeAcolhimento);
    }
    return sb.toString();
  }
}
