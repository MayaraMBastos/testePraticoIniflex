package maymb.teste_pratico_iniflex.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FuncionarioRequestDTO(
        @NotBlank(message = "O nome não pode estar em branco")
        String nome,

        @NotNull(message = "A data de nascimento é obrigatória")
        @PastOrPresent(message = "A data de nascimento não pode ser no futuro")
        LocalDate nascimento,

        @NotNull(message = "O salário é obrigatório")
        @Positive(message = "O salário deve ser um valor positivo")
        BigDecimal salario,

        @NotBlank(message = "A função não pode estar em branco")
        String funcao
) {
}