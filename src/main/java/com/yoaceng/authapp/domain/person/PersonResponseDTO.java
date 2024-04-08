package com.yoaceng.authapp.domain.person;

import java.time.LocalDate;

public record PersonResponseDTO(
        String id,
        String nome,
        String rg,
        String cpf,
        LocalDate dataEmissao,
        LocalDate dataNascimento,
        Integer genero,
        String nomePai,
        String nomeMae
) {
    public PersonResponseDTO  (Person person) {
        this(
                person.getId(),
                person.getNome(),
                person.getRg(),
                person.getCpf(),
                person.getDataEmissao(),
                person.getDataNascimento(),
                person.getGenero(),
                person.getNomePai(),
                person.getNomeMae()
        );
    }
}
