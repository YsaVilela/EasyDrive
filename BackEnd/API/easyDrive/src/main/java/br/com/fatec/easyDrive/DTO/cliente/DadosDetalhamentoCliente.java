package br.com.fatec.easyDrive.DTO.cliente;

import java.time.LocalDate;

import br.com.fatec.easyDrive.DTO.pessoa.DadosDetalhamentoPessoa;
import br.com.fatec.easyDrive.entity.Cliente;

public record DadosDetalhamentoCliente(
        Long id,
        String numeroCNH,
        LocalDate validadeCNH,
        DadosDetalhamentoPessoa pessoa,
        String plano,
        Long pontuacao,
        String status
) {
    public DadosDetalhamentoCliente(Cliente cliente) {
        this(
            cliente.getId(),
            cliente.getNumeroCNH(),
            cliente.getValidadeCNH(),
            new DadosDetalhamentoPessoa(cliente.getPessoa()),
            cliente.getPlanoAssinatura().getDescricao(),
            cliente.getPontuacao(),
            cliente.getStatus().getDescricao()
        );
    }
}
