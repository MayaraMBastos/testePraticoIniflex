package maymb.teste_pratico_iniflex.dto;

import java.time.LocalDate;

public record FuncionarioResponseDTO(

        Long id,
        String nome,
        LocalDate dataNascimento,
        Double salario,
        String funcao
) {}
