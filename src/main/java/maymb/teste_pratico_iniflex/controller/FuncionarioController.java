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

    // Post de exemplo de uma API Restful
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FuncionarioResponseDTO criarFuncionario(@RequestBody FuncionarioRequestDTO requestDTO) {
        return funcionarioService.criarFuncionario(requestDTO);
    }

    // Endpoint principal que executa todas as acoes do teste
    @GetMapping(value = "/executar-teste", produces = "text/plain")
    public String executarTesteCompleto() {
        StringBuilder sb = new StringBuilder();

        sb.append("<pre>");

        // 3.1 – Inserir todos os funcionarios
        funcionarioService.inserirFuncionariosIniciais();

        // 3.2 - Remover funcionrio "João"
        funcionarioService.removerFuncionario("João");
        sb.append("--- Funcionário 'João' Removido ---\n\n");

        // 3.3 - Imprimir todos os funcionarios
        sb.append("--- Lista de Funcionários ---\n");
        sb.append(funcionarioService.imprimirTodosFuncionarios()).append("\n");

        // 3.4 - Aumento de 10%
        funcionarioService.darAumentoDe10Porcento();
        sb.append("--- Salários Aumentados em 10% ---\n\n");
        sb.append(funcionarioService.imprimirTodosFuncionarios()).append("\n");

        // 3.5 e 3.6 - Agrupar e Imprimir por Funcao
        sb.append("--- Funcionários Agrupados por Função ---\n");
        sb.append(funcionarioService.imprimirFuncionariosPorFuncao()).append("\n");

        // 3.8 - Aniversariantes
        sb.append("--- Aniversariantes de Outubro e Dezembro ---\n");
        sb.append(funcionarioService.imprimirAniversariantesOutubroDezembro()).append("\n");

        // 3.9 - Funcionario mais velho
        sb.append("--- Funcionário com Maior Idade ---\n");
        sb.append(funcionarioService.imprimirFuncionarioMaisVelho()).append("\n\n");

        // 3.10 - lista de funcionarios emm ordem alfabetica
        sb.append("--- Funcionários em Ordem Alfabética ---\n");
        sb.append(funcionarioService.imprimirPorOrdemAlfabetica()).append("\n");

        // 3.11 - Total de salarios
        sb.append("--- Total de Salários ---\n");
        sb.append(funcionarioService.imprimirTotalSalarios()).append("\n\n");

        // 3.12 - qtd de salarios minimos por funcionario
        sb.append("--- Quantidade de salarios minimos ---\n");
        sb.append(funcionarioService.imprimirSalariosMinimos());

        sb.append("</pre>");

        return sb.toString();
    }

}
