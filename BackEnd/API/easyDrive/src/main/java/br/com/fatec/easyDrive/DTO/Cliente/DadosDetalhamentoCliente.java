package br.com.fatec.easyDrive.DTO.Cliente;

import br.com.fatec.easyDrive.Entity.Cliente;
import br.com.fatec.easyDrive.Entity.Pessoa;

public record DadosDetalhamentoCliente(
        Long id,
        String numeroCNH,
        String validadeCNH,
        Pessoa pessoa
) {
    public DadosDetalhamentoCliente(Cliente cliente) {
        this(
            cliente.getId(),
            cliente.getNumeroCNH(),
            cliente.getValidadeCNH(),
            cliente.getPessoa()
        );
    }
}
