package com.yoaceng.authapp.domain.person;

import java.time.LocalDate;

public record PersonDTO(
        String nome,
        String rg,
        String cpf,
        LocalDate dataEmissao,
        LocalDate dataNascimento,
        Integer genero,
        String nomePai,
        String nomeMae
) {}
