package maymb.teste_pratico_iniflex.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FuncionarioRequestDTO(
        String nome,
        LocalDate nascimento,
        BigDecimal salario,
        String funcao

) {
}
