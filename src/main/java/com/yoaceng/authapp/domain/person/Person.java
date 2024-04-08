package com.yoaceng.authapp.domain.person;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="person")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String nome;

    private String rg;

    private String cpf;

    @Column(name="data_emissao")
    private LocalDate dataEmissao;

    @Column(name="data_nascimento")
    private LocalDate dataNascimento;

    private Integer genero;

    @Column(name="nome_pai")
    private String nomePai;

    @Column(name="nome_mae")
    private String nomeMae;

    public Person(PersonDTO data) {
        this.nome = data.nome();
        this.rg = data.rg();
        this.cpf = data.cpf();
        this.dataEmissao = data.dataEmissao();
        this.dataNascimento = data.dataNascimento();
        this.genero = data.genero();
        this.nomePai = data.nomePai();
        this.nomeMae = data.nomeMae();
    }
}
