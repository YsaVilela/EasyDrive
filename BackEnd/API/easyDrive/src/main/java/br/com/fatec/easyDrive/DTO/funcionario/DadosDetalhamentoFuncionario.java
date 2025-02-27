package br.com.fatec.easyDrive.DTO.funcionario;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.fatec.easyDrive.DTO.pessoa.DadosDetalhamentoPessoa;
import br.com.fatec.easyDrive.entity.Funcionario;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DadosDetalhamentoFuncionario(
			Long id,
			String cargo,
        	DadosDetalhamentoPessoa pessoa,
            String status
		) {
	 public DadosDetalhamentoFuncionario(Funcionario funcionario) {
		this(
				funcionario.getId(),
				funcionario.getCargo().getDescricao(),
				new DadosDetalhamentoPessoa(funcionario.getPessoa()),
				funcionario.getStatus().getDescricao()
		);
	 }

}
