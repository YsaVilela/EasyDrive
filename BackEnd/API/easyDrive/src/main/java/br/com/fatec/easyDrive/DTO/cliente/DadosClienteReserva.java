package br.com.fatec.easyDrive.DTO.cliente;

import java.time.LocalDate;

import br.com.fatec.easyDrive.entity.Cliente;

public record DadosClienteReserva(
		Long id,
		String nome,
		String cpf,
        String numeroCNH,
        LocalDate validadeCNH,
		String telefone,
		String email,
		String plano
	) {
	public DadosClienteReserva(Cliente cliente) {
		this(
			cliente.getId(),
			cliente.getPessoa().getNome(),
			cliente.getPessoa().getCpf(),
			cliente.getNumeroCNH(),
			cliente.getValidadeCNH(),
			cliente.getPessoa().getTelefone(),
			cliente.getPessoa().getEmail(),
			cliente.getPlanoAssinatura().getDescricao()
		);
	}

}
