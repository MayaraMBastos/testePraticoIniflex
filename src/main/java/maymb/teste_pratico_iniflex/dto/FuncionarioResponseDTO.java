package maymb.teste_pratico_iniflex.dto;

import java.time.LocalDate;

public record FuncionarioResponseDTO(
        String nome,
        LocalDate nacimento,
        Double salario,
        String funcao) {
}
