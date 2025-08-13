package maymb.teste_pratico_iniflex.controller;

import maymb.teste_pratico_iniflex.dto.FuncionarioRequestDTO;
import maymb.teste_pratico_iniflex.dto.FuncionarioResponseDTO;
import maymb.teste_pratico_iniflex.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private FuncionarioRequestDTO funcionarioRequestDTO;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Retorna o status 201 (Created)
    public FuncionarioResponseDTO criarFuncionario(@RequestBody FuncionarioRequestDTO requestDTO) {
        return funcionarioService.criarFuncionario(requestDTO);
    }
}
