package maymb.teste_pratico_iniflex.controller;

import maymb.teste_pratico_iniflex.dto.FuncionarioRequestDTO;
import maymb.teste_pratico_iniflex.dto.FuncionarioResponseDTO;
import maymb.teste_pratico_iniflex.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;


    // Endpoint para criar um novo funcionário (POST)
    @PostMapping
    public ResponseEntity<FuncionarioResponseDTO> criarFuncionario(@RequestBody FuncionarioRequestDTO requestDTO) {
        FuncionarioResponseDTO responseDTO = funcionarioService.criarFuncionario(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // Requisito 3.3 - Endpoint para listar todos os funcionários (GET)
    @GetMapping
    public ResponseEntity<List<FuncionarioResponseDTO>> listarTodosFuncionarios() {
        List<FuncionarioResponseDTO> funcionarios = funcionarioService.listarTodosFuncionarios();
        return ResponseEntity.ok(funcionarios);
    }

    // Requisito 3.2 - Endpoint para remover um funcionário por nome (DELETE)
    @DeleteMapping("/{nome}")
    public ResponseEntity<Void> removerFuncionario(@PathVariable String nome) {
        funcionarioService.removerFuncionario(nome);
        return ResponseEntity.noContent().build();
    }

    // Requisito 3.4 - Endpoint para dar aumento de 10% (PUT)
    @PutMapping("/aumento-de-salario")
    public ResponseEntity<List<FuncionarioResponseDTO>> darAumentoDe10Porcento() {
        List<FuncionarioResponseDTO> funcionariosAtualizados = funcionarioService.darAumentoDe10Porcento();
        return ResponseEntity.ok(funcionariosAtualizados);
    }

    // Requisito 3.5 & 3.6 - Endpoint para agrupar funcionários por função (GET)
    @GetMapping("/por-funcao")
    public ResponseEntity<Map<String, List<FuncionarioResponseDTO>>> agruparPorFuncao() {
        Map<String, List<FuncionarioResponseDTO>> funcionariosPorFuncao = funcionarioService.agruparPorFuncao();
        return ResponseEntity.ok(funcionariosPorFuncao);
    }

    // Requisito 3.8 - Endpoint para listar aniversariantes (GET)
    @GetMapping("/aniversariantes")
    public ResponseEntity<List<FuncionarioResponseDTO>> listarAniversariantes() {
        List<FuncionarioResponseDTO> aniversariantes = funcionarioService.getAniversariantesOutubroDezembro();
        return ResponseEntity.ok(aniversariantes);
    }

    // Requisito 3.9 - Endpoint para encontrar o funcionário mais velho (GET)
    @GetMapping("/mais-velho")
    public ResponseEntity<FuncionarioResponseDTO> encontrarFuncionarioMaisVelho() {
        Optional<FuncionarioResponseDTO> maisVelho = funcionarioService.getFuncionarioMaisVelho();
        return maisVelho.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Requisito 3.10 - Endpoint para listar todos os funcionários em ordem alfabética (GET)
    @GetMapping("/ordenados")
    public ResponseEntity<List<FuncionarioResponseDTO>> listarPorOrdemAlfabetica() {
        List<FuncionarioResponseDTO> funcionariosOrdenados = funcionarioService.getFuncionariosPorOrdemAlfabetica();
        return ResponseEntity.ok(funcionariosOrdenados);
    }

    // Requisito 3.11 - Endpoint para obter o total de salários (GET)
    @GetMapping("/total-salarios")
    public ResponseEntity<BigDecimal> getTotalSalarios() {
        BigDecimal total = funcionarioService.getTotalSalarios();
        return ResponseEntity.ok(total);
    }

    // Requisito 3.12 - Endpoint para obter salários em mínimos por funcionário (GET)
    @GetMapping("/salarios-minimos")
    public ResponseEntity<Map<String, BigDecimal>> getSalariosMinimos() {
        Map<String, BigDecimal> salariosMinimos = funcionarioService.getSalariosMinimosPorFuncionario();
        return ResponseEntity.ok(salariosMinimos);
    }

    // Endpoint principal que executa todas as acoes do teste
    @GetMapping("/executar-teste")
    public String executarTesteCompleto() {
        StringBuilder sb = new StringBuilder();
        sb.append("<pre>");

        // 3.1 – Inserir todos os funcionários
        funcionarioService.inserirFuncionariosIniciais();

        // 3.2 - Remover funcionário "João"
        funcionarioService.removerFuncionario("João");
        sb.append("--- Funcionário 'João' Removido ---\n\n");

        // 3.3 - Imprimir todos os funcionários
        sb.append("--- Lista de Funcionários ---\n");
        funcionarioService.listarTodosFuncionarios().forEach(f -> sb.append(funcionarioService.formatarFuncionario(f)).append("\n"));
        sb.append("\n");

        // 3.4 - Aumento de 10%
        funcionarioService.darAumentoDe10Porcento();
        sb.append("--- Salários Aumentados em 10% ---\n\n");
        funcionarioService.listarTodosFuncionarios().forEach(f -> sb.append(funcionarioService.formatarFuncionario(f)).append("\n"));
        sb.append("\n");

        // 3.5 & 3.6 - Agrupar e Imprimir por Função
        sb.append("--- Funcionários Agrupados por Função ---\n");
        Map<String, List<FuncionarioResponseDTO>> funcionariosPorFuncao = funcionarioService.agruparPorFuncao();
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            sb.append("Função: ").append(funcao).append("\n");
            lista.forEach(f -> sb.append("  - ").append(funcionarioService.formatarFuncionario(f)).append("\n"));
        });
        sb.append("\n");

        // 3.8 - Aniversariantes
        sb.append("--- Aniversariantes de Outubro e Dezembro ---\n");
        funcionarioService.getAniversariantesOutubroDezembro().forEach(f -> sb.append(funcionarioService.formatarFuncionario(f)).append("\n"));
        sb.append("\n");

        // 3.9 - Funcionário mais velho
        sb.append("--- Funcionário com Maior Idade ---\n");
        funcionarioService.getFuncionarioMaisVelho().ifPresent(f -> {
            LocalDate hoje = LocalDate.now();
            Period idade = Period.between(f.dataNascimento(), hoje);
            sb.append("Nome: ").append(f.nome()).append(", Idade: ").append(idade.getYears()).append("\n");
        });
        sb.append("\n");

        // 3.10 - Ordem alfabética
        sb.append("--- Funcionários em Ordem Alfabética ---\n");
        funcionarioService.getFuncionariosPorOrdemAlfabetica().forEach(f -> sb.append(funcionarioService.formatarFuncionario(f)).append("\n"));
        sb.append("\n");

        // 3.11 - Total de salários
        sb.append("--- Total de Salários ---\n");
        BigDecimal totalSalarios = funcionarioService.getTotalSalarios();
        NumberFormat numberFormatter = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
        numberFormatter.setMinimumFractionDigits(2);
        numberFormatter.setMaximumFractionDigits(2);
        sb.append(numberFormatter.format(totalSalarios)).append("\n\n");

        // 3.12 - Salários em mínimos
        sb.append("--- Salários em Mínimos ---\n");
        Map<String, BigDecimal> salariosMinimos = funcionarioService.getSalariosMinimosPorFuncionario();
        salariosMinimos.forEach((nome, salMin) -> {
            sb.append("Nome: ").append(nome)
                    .append(", Salários Mínimos: ").append(salMin).append("\n");
        });
        sb.append("\n");

        sb.append("</pre>");
        return sb.toString();

    }

}
