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
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }


    // Método de formatação de um único funcionário, agora público para ser acessado pelo Controller
    public String formatarFuncionario(FuncionarioResponseDTO f) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        NumberFormat numberFormatter = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
        numberFormatter.setMinimumFractionDigits(2);
        numberFormatter.setMaximumFractionDigits(2);

        return String.format(
                "Nome: %s, Data Nascimento: %s, Salário: %s, Função: %s",
                f.nome(),
                f.dataNascimento().format(dateFormatter),
                numberFormatter.format(f.salario()),
                f.funcao()
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

    public void removerFuncionario(String nome) {
        funcionarioRepository.findByNome(nome).ifPresent(funcionarioRepository::delete);
    }

    public List<FuncionarioResponseDTO> listarTodosFuncionarios() {
        return funcionarioRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<FuncionarioResponseDTO> darAumentoDe10Porcento() {
        List<Funcionario> todosFuncionarios = funcionarioRepository.findAll();
        todosFuncionarios.forEach(f -> {
            BigDecimal novoSalario = f.getSalario().multiply(new BigDecimal("1.10"));
            f.setSalario(novoSalario.setScale(2, RoundingMode.HALF_UP));
        });
        funcionarioRepository.saveAll(todosFuncionarios);
        return todosFuncionarios.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Map<String, List<FuncionarioResponseDTO>> agruparPorFuncao() {
        List<Funcionario> todosFuncionarios = funcionarioRepository.findAll();
        return todosFuncionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao,
                        Collectors.mapping(this::toResponseDTO, Collectors.toList())));
    }

    public List<FuncionarioResponseDTO> getAniversariantesOutubroDezembro() {
        return funcionarioRepository.findAll().stream()
                .filter(f -> f.getNascimento().getMonthValue() == 10 || f.getNascimento().getMonthValue() == 12)
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<FuncionarioResponseDTO> getFuncionarioMaisVelho() {
        return funcionarioRepository.findAll().stream()
                .min(Comparator.comparing(Funcionario::getNascimento))
                .map(this::toResponseDTO);
    }

    public List<FuncionarioResponseDTO> getFuncionariosPorOrdemAlfabetica() {
        return funcionarioRepository.findAll().stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public BigDecimal getTotalSalarios() {
        return funcionarioRepository.findAll().stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<String, BigDecimal> getSalariosMinimosPorFuncionario() {
        return funcionarioRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Funcionario::getNome,
                        f -> f.getSalario().divide(SALARIO_MINIMO, 2, RoundingMode.HALF_UP)
                ));
    }

    // Método para converter Entity para DTO
    private FuncionarioResponseDTO toResponseDTO(Funcionario funcionario) {
        return new FuncionarioResponseDTO(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getNascimento(),
                funcionario.getSalario(),
                funcionario.getFuncao()
        );
    }

    // Método para converter DTO para Entity (Adicionado)
    private Funcionario toEntity(FuncionarioRequestDTO dto) {
        return new Funcionario(dto.nome(), dto.nascimento(), dto.salario(), dto.funcao());
    }

    // Método para criar um novo funcionário (Adicionado)
    public FuncionarioResponseDTO criarFuncionario(FuncionarioRequestDTO requestDTO) {
        Funcionario funcionario = toEntity(requestDTO);
        Funcionario savedFuncionario = funcionarioRepository.save(funcionario);
        return toResponseDTO(savedFuncionario);
    }
}