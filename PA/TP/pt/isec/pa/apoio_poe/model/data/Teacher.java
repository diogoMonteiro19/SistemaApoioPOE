package pt.isec.pa.apoio_poe.model.data;

import pt.isec.pa.apoio_poe.model.data.proposals.Proposal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Teacher implements Serializable {
  private static final long serialVersionUID = 1L;
  private String nome;
  private String email;
  private List<Proposal> propostasAtribuidas;

  public Teacher(String nome, String email) {
    this.nome = nome;
    this.email = email;
    propostasAtribuidas = new ArrayList<>();
  }

  protected Teacher(String email) {
    this.email = email;
  }

  public void addProposta(Proposal proposta) {
    propostasAtribuidas.add(proposta);
  }

  public List<Proposal> getPropostasAtribuidas() {
    return propostasAtribuidas;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;

    if (!(o instanceof Teacher)) {
      return false;
    }

    Teacher other = (Teacher) o;

    if (this.email.equalsIgnoreCase(other.email)) {
      return true;
    }

    return false;
  }

  @Override
  public int hashCode() {
    byte[] byteArray = this.email.getBytes();
    int sum = 0;

    for (int i = 0; i < byteArray.length; i++) {
      sum += byteArray[i];
    }

    return sum;
  }

  public String getEmail() {
    return email;
  }

  public String toStringFicheiro() {
    StringBuilder sb = new StringBuilder();

    sb.append(nome + ',');
    sb.append(email + ',');

    return sb.toString();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("Nome: " + nome + ", ");
    sb.append("Email: " + email + ", ");

    return sb.toString();
  }
}
