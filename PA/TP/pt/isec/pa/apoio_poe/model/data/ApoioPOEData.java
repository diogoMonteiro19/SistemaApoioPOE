package pt.isec.pa.apoio_poe.model.data;

import pt.isec.pa.apoio_poe.model.data.proposals.Proposal;

import java.io.Serializable;
import java.util.*;

public class ApoioPOEData implements Serializable {
  private static final long serialVersionUID = 1L;
  private List<Proposal> propostas;
  private List<Teacher> docentes;
  private List<Student> alunos;
  private List<Application> candidaturas;

  public ApoioPOEData() {
    this.alunos = new ArrayList<>();
    this.docentes = new ArrayList<>();
    this.propostas = new ArrayList<>();
    this.candidaturas = new ArrayList<>();
  }

  private Teacher getDummyTeacher(String email) {
    return new Teacher(email);
  }

  public boolean adicionaAluno(Student student) {
    return alunos.add(student);
  }

  public boolean adicionaProposta(Proposal proposal) {
    return propostas.add(proposal);
  }

  public Teacher findTeacher(String email) {
    if(docentes.contains(getDummyTeacher(email))){
      return docentes.get(docentes.indexOf(getDummyTeacher(email)));
    }
    return null;
  }

  public List<Student> getAlunos() {
    return alunos;
  }
  public Student findStudent(long nEstudante){
    Student dummy = Student.getDummyStudent(nEstudante);

    if (alunos.contains(dummy)) {
      return alunos.get(alunos.indexOf(dummy));
    }

    return null;
  }

  public List<Teacher> getDocentes() {
    return docentes;
  }

    public boolean adicionaDocente(Teacher teacher) {return docentes.add(teacher);}

  public List<Proposal> getPropostas() {
    return propostas;
  }

  public List<Application> getCandidaturas() {
    return candidaturas;
  }


  public Proposal findProposal(String id) {
    Proposal dummy = Proposal.getDummyProposal(id);

    if (propostas.contains(dummy)) {
      return propostas.get(propostas.indexOf(dummy));
    }
    return null;
  }
  public boolean adicionaCandidatura(long nEstudante, String [] idPropostas) {
    Student aluno = findStudent(nEstudante);
    if (findApplication(aluno) != null) {
      return false;
    }

    List<Proposal> proposals = new ArrayList<>();

    for (String id : idPropostas) {
      proposals.add(findProposal(id));
    }

    candidaturas.add(new Application(aluno, proposals));

    return true;
  }

  public Application findApplication(Student aluno) {
    Application dummy = Application.getDummyApplication(aluno);

    if (candidaturas.contains(dummy)) {
      return candidaturas.get(candidaturas.indexOf(dummy));
    }
    return null;
  }

  public List<Student> getAlunosCandidaturasSorted(){
    List<Student> alunosCandidaturas = new ArrayList<>();
    for(Application a : candidaturas){
      alunosCandidaturas.add(a.getAluno());
    }
    Collections.sort(alunosCandidaturas,Comparator.comparingDouble(Student::getClassificacao));
    return alunosCandidaturas;
  }
}
