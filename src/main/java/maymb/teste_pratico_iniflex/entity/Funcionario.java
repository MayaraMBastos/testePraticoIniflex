package maymb.teste_pratico_iniflex.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "funcionarios")
public class Funcionario extends Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double salario;
    private String funcao;

    private Pessoa pessoa;

    public Funcionario(String nome, LocalDate nascimento, Long id, Double salario, String funcao, Pessoa pessoa) {
        super(nome, nascimento);
        this.id = id;
        this.salario = salario;
        this.funcao = funcao;
        this.pessoa = pessoa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
