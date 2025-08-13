package maymb.teste_pratico_iniflex.serviceTest;


import maymb.teste_pratico_iniflex.dto.FuncionarioRequestDTO;
import maymb.teste_pratico_iniflex.dto.FuncionarioResponseDTO;
import maymb.teste_pratico_iniflex.entity.Funcionario;
import maymb.teste_pratico_iniflex.repository.FuncionarioRepository;
import maymb.teste_pratico_iniflex.service.FuncionarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(FuncionarioService.class)
class FuncionarioServiceIntegrationTest {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    @BeforeEach
    void setup() {
        funcionarioRepository.deleteAll();
        funcionarioRepository.save(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2000"), "Operador"));
        funcionarioRepository.save(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("3000"), "Gerente"));
    }

    @Test
    void deveListarTodosFuncionarios() {
        List<FuncionarioResponseDTO> lista = funcionarioService.listarTodosFuncionarios();
        assertThat(lista).hasSize(2);
    }

    @Test
    void deveDarAumentoDe10PorcentoNoBanco() {
        funcionarioService.darAumentoDe10Porcento();
        BigDecimal salarioMaria = funcionarioRepository.findByNome("Maria").get().getSalario();
        assertThat(salarioMaria).isEqualTo(new BigDecimal("2200.00"));
    }

    @Test
    void deveCriarFuncionarioNoBanco() {
        FuncionarioRequestDTO request = new FuncionarioRequestDTO("Ana", LocalDate.now(), BigDecimal.TEN, "Tester");
        funcionarioService.criarFuncionario(request);

        assertThat(funcionarioRepository.findByNome("Ana")).isPresent();
    }

    @Test
    void deveRetornarTotalDeSalariosDoBanco() {
        BigDecimal total = funcionarioService.getTotalSalarios();
        assertThat(total).isEqualTo(new BigDecimal("5000"));
    }
}
