package maymb.teste_pratico_iniflex.entity;


import jakarta.persistence.MappedSuperclass;

import java.time.LocalDate;

@MappedSuperclass
public class Pessoa {

    private String nome;
    private LocalDate nascimento;


    public Pessoa(String nome, LocalDate nascimento) {
        this.nome = nome;
        this.nascimento = nascimento;
    }

    public Pessoa() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }
}
