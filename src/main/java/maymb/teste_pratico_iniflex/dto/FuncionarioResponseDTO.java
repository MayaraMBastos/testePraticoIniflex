package maymb.teste_pratico_iniflex.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FuncionarioResponseDTO(

        Long id,
        String nome,
        LocalDate dataNascimento,
        BigDecimal salario,
        String funcao
) {}
