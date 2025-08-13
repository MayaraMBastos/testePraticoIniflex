package maymb.teste_pratico_iniflex.service;

import maymb.teste_pratico_iniflex.dto.FuncionarioRequestDTO;
import maymb.teste_pratico_iniflex.dto.FuncionarioResponseDTO;
import maymb.teste_pratico_iniflex.entity.Funcionario;
import maymb.teste_pratico_iniflex.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public FuncionarioResponseDTO criarFuncionario(FuncionarioRequestDTO requestDTO) {
        Funcionario funcionario = convertToEntity(requestDTO);
        Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);
        return convertToResponseDTO(funcionarioSalvo);
    }

    private Funcionario convertToEntity(FuncionarioRequestDTO dto) {


        Funcionario funcionario = new Funcionario();

        funcionario.setNome(dto.nome());
        funcionario.setNascimento(dto.nascimento());

        funcionario.setFuncao(dto.funcao());
        funcionario.setSalario(dto.salario());

        return funcionario;
    }

    private FuncionarioResponseDTO convertToResponseDTO(Funcionario funcionario) {

        return new FuncionarioResponseDTO(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getNascimento(),
                funcionario.getSalario(),
                funcionario.getFuncao()
        );
    }
}