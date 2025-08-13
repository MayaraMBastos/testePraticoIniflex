package maymb.teste_pratico_iniflex.serviceTest;


import maymb.teste_pratico_iniflex.dto.FuncionarioRequestDTO;
import maymb.teste_pratico_iniflex.dto.FuncionarioResponseDTO;
import maymb.teste_pratico_iniflex.entity.Funcionario;
import maymb.teste_pratico_iniflex.repository.FuncionarioRepository;
import maymb.teste_pratico_iniflex.service.FuncionarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class FuncionarioServiceTest {

    private FuncionarioRepository funcionarioRepository;
    private FuncionarioService funcionarioService;

    @BeforeEach
    void setUp() {
        funcionarioRepository = mock(FuncionarioRepository.class);
        funcionarioService = new FuncionarioService(funcionarioRepository);
    }

    @Test
    void deveFormatarFuncionarioCorretamente() {
        FuncionarioResponseDTO dto = new FuncionarioResponseDTO(1L, "Maria",
                LocalDate.of(2000, 10, 18), new BigDecimal("2000.50"), "Operador");

        String resultado = funcionarioService.formatarFuncionario(dto);

        assertThat(resultado).isEqualTo("Nome: Maria, Data Nascimento: 18/10/2000, Salário: 2.000,50, Função: Operador");
    }

    @Test
    void deveRemoverFuncionarioQuandoEncontrado() {
        Funcionario maria = new Funcionario("Maria", LocalDate.now(), BigDecimal.TEN, "Operador");
        when(funcionarioRepository.findByNome("Maria")).thenReturn(Optional.of(maria));

        funcionarioService.removerFuncionario("Maria");

        verify(funcionarioRepository).delete(maria);
    }

    @Test
    void deveDarAumentoDe10Porcento() {
        Funcionario f = new Funcionario("João", LocalDate.now(), new BigDecimal("1000"), "Operador");
        when(funcionarioRepository.findAll()).thenReturn(List.of(f));

        List<FuncionarioResponseDTO> resultado = funcionarioService.darAumentoDe10Porcento();

        assertThat(resultado.get(0).salario()).isEqualTo(new BigDecimal("1100.00"));
        verify(funcionarioRepository).saveAll(anyList());
    }

    @Test
    void deveAgruparFuncionariosPorFuncao() {
        Funcionario f1 = new Funcionario("João", LocalDate.now(), BigDecimal.TEN, "Operador");
        Funcionario f2 = new Funcionario("Maria", LocalDate.now(), BigDecimal.ONE, "Gerente");
        when(funcionarioRepository.findAll()).thenReturn(List.of(f1, f2));

        Map<String, List<FuncionarioResponseDTO>> resultado = funcionarioService.agruparPorFuncao();

        assertThat(resultado).containsKeys("Operador", "Gerente");
    }

    @Test
    void deveRetornarFuncionarioMaisVelho() {
        Funcionario jovem = new Funcionario("Jovem", LocalDate.of(2000, 1, 1), BigDecimal.ONE, "Operador");
        Funcionario velho = new Funcionario("Velho", LocalDate.of(1980, 1, 1), BigDecimal.ONE, "Gerente");

        when(funcionarioRepository.findAll()).thenReturn(List.of(jovem, velho));

        Optional<FuncionarioResponseDTO> resultado = funcionarioService.getFuncionarioMaisVelho();

        assertThat(resultado).isPresent();
        assertThat(resultado.get().nome()).isEqualTo("Velho");
    }

    @Test
    void deveCalcularTotalDeSalarios() {
        when(funcionarioRepository.findAll()).thenReturn(List.of(
                new Funcionario("A", LocalDate.now(), new BigDecimal("1000"), "X"),
                new Funcionario("B", LocalDate.now(), new BigDecimal("2000"), "Y")
        ));

        BigDecimal total = funcionarioService.getTotalSalarios();

        assertThat(total).isEqualTo(new BigDecimal("3000"));
    }

    @Test
    void deveCalcularSalariosMinimosPorFuncionario() {
        when(funcionarioRepository.findAll()).thenReturn(List.of(
                new Funcionario("A", LocalDate.now(), new BigDecimal("2424.00"), "X")
        ));

        Map<String, BigDecimal> resultado = funcionarioService.getSalariosMinimosPorFuncionario();

        assertThat(resultado.get("A")).isEqualTo(new BigDecimal("2.00"));
    }

    @Test
    void deveCriarNovoFuncionario() {
        FuncionarioRequestDTO request = new FuncionarioRequestDTO("Ana", LocalDate.now(), BigDecimal.TEN, "Tester");
        Funcionario salvo = new Funcionario("Ana", LocalDate.now(), BigDecimal.TEN, "Tester");
        salvo.setId(1L);

        when(funcionarioRepository.save(any())).thenReturn(salvo);

        FuncionarioResponseDTO resposta = funcionarioService.criarFuncionario(request);

        assertThat(resposta.id()).isEqualTo(1L);
        assertThat(resposta.nome()).isEqualTo("Ana");
    }
}
