package maymb.teste_pratico_iniflex.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Funcionários", description = "Endpoints para gerenciar os funcionários da empresa Iniflex")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;


    @PostMapping
    @Operation(summary = "Cria um novo funcionário", description = "Cria e salva um único funcionário no banco de dados.")
    @ApiResponse(responseCode = "201", description = "Funcionário criado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Requisição inválida (JSON malformado, dados incorretos, etc.)")
    public ResponseEntity<FuncionarioResponseDTO> criarFuncionario(@RequestBody @Valid FuncionarioRequestDTO requestDTO) {
        FuncionarioResponseDTO responseDTO = funcionarioService.criarFuncionario(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Lista todos os funcionários", description = "Retorna uma lista completa de todos os funcionários cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de funcionários retornada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = FuncionarioResponseDTO.class)))
    public ResponseEntity<List<FuncionarioResponseDTO>> listarTodosFuncionarios() {
        List<FuncionarioResponseDTO> funcionarios = funcionarioService.listarTodosFuncionarios();
        return ResponseEntity.ok(funcionarios);
    }

    @DeleteMapping("/{nome}")
    @Operation(summary = "Remove um funcionário por nome", description = "Exclui um funcionário do banco de dados a partir do seu nome.")
    @ApiResponse(responseCode = "204", description = "Funcionário removido com sucesso")
    public ResponseEntity<Void> removerFuncionario(
            @Parameter(description = "Nome do funcionário a ser removido", example = "João") @PathVariable String nome) {
        funcionarioService.removerFuncionario(nome);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/aumento-de-salario")
    @Operation(summary = "Aplica aumento de 10% nos salários", description = "Aumenta o salário de todos os funcionários em 10% e retorna a lista atualizada.")
    @ApiResponse(responseCode = "200", description = "Salários atualizados com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = FuncionarioResponseDTO.class)))
    public ResponseEntity<List<FuncionarioResponseDTO>> darAumentoDe10Porcento() {
        List<FuncionarioResponseDTO> funcionariosAtualizados = funcionarioService.darAumentoDe10Porcento();
        return ResponseEntity.ok(funcionariosAtualizados);
    }

    @GetMapping("/por-funcao")
    @Operation(summary = "Agrupa funcionários por função", description = "Retorna um mapa onde a chave é a função e o valor é a lista de funcionários daquela função.")
    @ApiResponse(responseCode = "200", description = "Mapa de funcionários por função retornado com sucesso")
    public ResponseEntity<Map<String, List<FuncionarioResponseDTO>>> agruparPorFuncao() {
        Map<String, List<FuncionarioResponseDTO>> funcionariosPorFuncao = funcionarioService.agruparPorFuncao();
        return ResponseEntity.ok(funcionariosPorFuncao);
    }

    @GetMapping("/aniversariantes")
    @Operation(summary = "Lista aniversariantes", description = "Retorna uma lista de funcionários que fazem aniversário nos meses de outubro e dezembro.")
    @ApiResponse(responseCode = "200", description = "Lista de aniversariantes retornada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = FuncionarioResponseDTO.class)))
    public ResponseEntity<List<FuncionarioResponseDTO>> listarAniversariantes() {
        List<FuncionarioResponseDTO> aniversariantes = funcionarioService.getAniversariantesOutubroDezembro();
        return ResponseEntity.ok(aniversariantes);
    }

    // O correto seria criar uma DTO response especifica com os atributos nome e idade
    @GetMapping("/mais-velho")
    @Operation(summary = "Encontra o funcionário mais velho", description = "Retorna os dados do funcionário mais velho e sua idade.")
    @ApiResponse(responseCode = "200", description = "Funcionário mais velho encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Nenhum funcionário encontrado")
    public ResponseEntity<FuncionarioResponseDTO> encontrarFuncionarioMaisVelho() {
        Optional<FuncionarioResponseDTO> maisVelho = funcionarioService.getFuncionarioMaisVelho();
        return maisVelho.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/ordenados")
    @Operation(summary = "Lista funcionários em ordem alfabética", description = "Retorna a lista de todos os funcionários ordenada por nome, em ordem alfabética.")
    @ApiResponse(responseCode = "200", description = "Lista ordenada de funcionários retornada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = FuncionarioResponseDTO.class)))
    public ResponseEntity<List<FuncionarioResponseDTO>> listarPorOrdemAlfabetica() {
        List<FuncionarioResponseDTO> funcionariosOrdenados = funcionarioService.getFuncionariosPorOrdemAlfabetica();
        return ResponseEntity.ok(funcionariosOrdenados);
    }

    @GetMapping("/total-salarios")
    @Operation(summary = "Obtém o total de salários", description = "Calcula e retorna a soma de todos os salários dos funcionários.")
    @ApiResponse(responseCode = "200", description = "Total de salários retornado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BigDecimal.class)))
    public ResponseEntity<BigDecimal> getTotalSalarios() {
        BigDecimal total = funcionarioService.getTotalSalarios();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/salarios-minimos")
    @Operation(summary = "Obtém salários em múltiplos de salários mínimos", description = "Retorna um mapa com o nome de cada funcionário e quantos salários mínimos ele recebe.")
    @ApiResponse(responseCode = "200", description = "Mapa de salários em mínimos retornado com sucesso")
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
