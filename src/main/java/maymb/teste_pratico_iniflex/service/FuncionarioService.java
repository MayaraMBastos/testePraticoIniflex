package maymb.teste_pratico_iniflex.service;

import maymb.teste_pratico_iniflex.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository pessoaRepository) {
        this.funcionarioRepository = pessoaRepository;
    }

    public void salvarDadosDoFuncionario(){

    }
}
