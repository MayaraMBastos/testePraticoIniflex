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

    // Construtor vazio é obrigatório para JPA/Hibernate
    public Funcionario() {
        super();
    }

    // Construtor para criar uma instância de Funcionario
    public Funcionario(String nome, LocalDate dataNascimento, Double salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
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

    // A herança cuida dos atributos de Pessoa, não é necessário um getPessoa/setPessoa.
}