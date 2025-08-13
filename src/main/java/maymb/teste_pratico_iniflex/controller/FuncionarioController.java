package maymb.teste_pratico_iniflex.controller;

import maymb.teste_pratico_iniflex.dto.FuncionarioRequestDTO;
import maymb.teste_pratico_iniflex.dto.FuncionarioResponseDTO;
import maymb.teste_pratico_iniflex.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;


    //criar gets para listagem

    //get para listar todos

    //get por ordem alfabetica

    //get para agrupados por funcao

    //get para mes de aniversario

    // get maior idade

    // get total/soma de todos os salarios

    // get quantos salarios minimos

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Retorna o status 201 (Created)
    public FuncionarioResponseDTO criarFuncionario(@RequestBody FuncionarioRequestDTO requestDTO) {
        return funcionarioService.criarFuncionario(requestDTO);
    }

    // Endpoint principal que executa todas as ações do teste
    @GetMapping("/executar-teste")
    public String executarTesteCompleto() {
        StringBuilder sb = new StringBuilder();

        // Inserir todos os funcionários
        funcionarioService.inserirFuncionariosIniciais();

        // Remover funcionário "João"
        funcionarioService.removerFuncionario("João");
        sb.append("--- Funcionário 'João' Removido ---\n\n");

        // Imprimir todos os funcionários
        sb.append("--- Lista de Funcionários ---\n");
        sb.append(funcionarioService.imprimirTodosFuncionarios()).append("\n");

        // Aumento de 10%
        funcionarioService.darAumentoDe10Porcento();
        sb.append("--- Salários Aumentados em 10% ---\n\n");
        sb.append(funcionarioService.imprimirTodosFuncionarios()).append("\n");

        // Agrupar e Imprimir por Função
        sb.append("--- Funcionários Agrupados por Função ---\n");
        sb.append(funcionarioService.imprimirFuncionariosPorFuncao()).append("\n");

        // Aniversariantes
        sb.append("--- Aniversariantes de Outubro e Dezembro ---\n");
        sb.append(funcionarioService.imprimirAniversariantesOutubroDezembro()).append("\n");

        // Funcionário mais velho
        sb.append("--- Funcionário com Maior Idade ---\n");
        sb.append(funcionarioService.imprimirFuncionarioMaisVelho()).append("\n\n");

        // Ordem alfabética
        sb.append("--- Funcionários em Ordem Alfabética ---\n");
        sb.append(funcionarioService.imprimirPorOrdemAlfabetica()).append("\n");

        // Total de salários
        sb.append("--- Total de Salários ---\n");
        sb.append(funcionarioService.imprimirTotalSalarios()).append("\n\n");

        // Salários em mínimos
        sb.append("--- Salários em Mínimos ---\n");
        sb.append(funcionarioService.imprimirSalariosMinimos());

        return sb.toString();
    }
}
