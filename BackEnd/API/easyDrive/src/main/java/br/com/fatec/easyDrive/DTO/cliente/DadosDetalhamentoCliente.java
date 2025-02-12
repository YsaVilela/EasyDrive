package br.com.fatec.easyDrive.DTO.cliente;

import java.util.Date;

import br.com.fatec.easyDrive.entity.Cliente;
import br.com.fatec.easyDrive.entity.DadosDetalhamentoPessoa;

public record DadosDetalhamentoCliente(
        Long id,
        String numeroCNH,
        Date validadeCNH,
        DadosDetalhamentoPessoa pessoa
) {
    public DadosDetalhamentoCliente(Cliente cliente) {
        this(
            cliente.getId(),
            cliente.getNumeroCNH(),
            cliente.getValidadeCNH(),
            new DadosDetalhamentoPessoa(cliente.getPessoa())
        );
    }
}
