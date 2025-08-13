package maymb.teste_pratico_iniflex.dto;

import java.time.LocalDate;

public record FuncionarioRequestDTO(
        String nome,
        LocalDate nascimento

) {
}
