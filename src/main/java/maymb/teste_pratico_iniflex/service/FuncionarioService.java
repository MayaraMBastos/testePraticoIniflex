package maymb.teste_pratico_iniflex.service;

import maymb.teste_pratico_iniflex.dto.FuncionarioRequestDTO;
import maymb.teste_pratico_iniflex.dto.FuncionarioResponseDTO;
import maymb.teste_pratico_iniflex.entity.Funcionario;
import maymb.teste_pratico_iniflex.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    // Metodo de formatação global para reuso
    private String formatarFuncionario(Funcionario f) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        NumberFormat numberFormatter = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
        numberFormatter.setMinimumFractionDigits(2);
        numberFormatter.setMaximumFractionDigits(2);

        return String.format(
                "Nome: %s, Data Nascimento: %s, Salário: %s, Função: %s",
                f.getNome(),
                f.getNascimento().format(dateFormatter),
                numberFormatter.format(f.getSalario()),
                f.getFuncao()
        );
    }

    // Método para inserir dados iniciais, chamado na inicialização
    public void inserirFuncionariosIniciais() {
        if (funcionarioRepository.count() == 0) {
            funcionarioRepository.saveAll(List.of(
                    new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
                    new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
                    new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
                    new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
                    new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
                    new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"),
                    new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
                    new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
                    new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
                    new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
            ));
        }
    }

    // Remover o funcionario “João”
    public void removerFuncionario(String nome) {
        Optional<Funcionario> funcionario = funcionarioRepository.findByNome(nome);
        funcionario.ifPresent(funcionarioRepository::delete);
    }

    //Imprimir todos os funcionários com formatação
    public String imprimirTodosFuncionarios() {
        StringBuilder sb = new StringBuilder();
        funcionarioRepository.findAll().forEach(f -> sb.append(formatarFuncionario(f)).append("\n"));
        return sb.toString();
    }

    // Aumento de 10%
    public void darAumentoDe10Porcento() {
        List<Funcionario> todosFuncionarios = funcionarioRepository.findAll();
        todosFuncionarios.forEach(f -> {
            BigDecimal novoSalario = f.getSalario().multiply(new BigDecimal("1.10"));
            f.setSalario(novoSalario.setScale(2, RoundingMode.HALF_UP));
        });
        funcionarioRepository.saveAll(todosFuncionarios);
    }

    // Agrupar os funcionários por funcao em um MAP e imprimir
    public String imprimirFuncionariosPorFuncao() {
        StringBuilder sb = new StringBuilder();
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarioRepository.findAll().stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        funcionariosPorFuncao.forEach((funcao, lista) -> {
            sb.append("Função: ").append(funcao).append("\n");
            lista.forEach(f -> sb.append("  - ").append(formatarFuncionario(f)).append("\n"));
        });
        return sb.toString();
    }

    //Imprimir os aniversariantes do mes 10 e 12
    public String imprimirAniversariantesOutubroDezembro() {
        StringBuilder sb = new StringBuilder();
        funcionarioRepository.findAll().stream()
                .filter(f -> f.getNascimento().getMonthValue() == 10 || f.getNascimento().getMonthValue() == 12)
                .forEach(f -> sb.append(formatarFuncionario(f)).append("\n"));
        return sb.toString();
    }

    // Imprimir o funcionário com a maior idade
    public String imprimirFuncionarioMaisVelho() {
        Optional<Funcionario> maisVelho = funcionarioRepository.findAll().stream()
                .min(Comparator.comparing(Funcionario::getNascimento));

        if (maisVelho.isPresent()) {
            Funcionario f = maisVelho.get();
            int idade = Period.between(f.getNascimento(), LocalDate.now()).getYears();
            return String.format("Nome: %s, Idade: %d", f.getNome(), idade);
        }
        return "Nenhum funcionário encontrado.";
    }

    //Imprimir a lista de funcionários por ordem alfabética
    public String imprimirPorOrdemAlfabetica() {
        StringBuilder sb = new StringBuilder();
        funcionarioRepository.findAll().stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(f -> sb.append(formatarFuncionario(f)).append("\n"));
        return sb.toString();
    }

    // Imprimir o total dos salários
    public String imprimirTotalSalarios() {
        BigDecimal total = funcionarioRepository.findAll().stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        NumberFormat numberFormatter = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
        numberFormatter.setMinimumFractionDigits(2);
        numberFormatter.setMaximumFractionDigits(2);
        return numberFormatter.format(total);
    }

    // Imprimir quantos salários mínimos ganha cada funcionário
    public String imprimirSalariosMinimos() {
        StringBuilder sb = new StringBuilder();
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarioRepository.findAll().forEach(f -> {
            BigDecimal salariosMinimos = f.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            sb.append(String.format("Nome: %s, Salários Mínimos: %s\n", f.getNome(), salariosMinimos));
        });
        return sb.toString();
    }

     //METODOS REAIS DE API RESTFUL -------------------------------------
    // Metodo para o DTO de requisição (não faz parte dos requisitos do teste, mas demonstra uma requisicao real de API RestFul)
    public FuncionarioResponseDTO criarFuncionario(FuncionarioRequestDTO dto) {
        Funcionario funcionario = new Funcionario(dto.nome(), dto.nascimento(), dto.salario(), dto.funcao());
        Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);

        return new FuncionarioResponseDTO(
                funcionarioSalvo.getId(),
                funcionarioSalvo.getNome(),
                funcionarioSalvo.getNascimento(),
                funcionarioSalvo.getSalario(),
                funcionarioSalvo.getFuncao()
        );
    }
}