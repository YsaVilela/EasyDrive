package br.com.fatec.easyDrive.DTO.cliente;

import java.time.LocalDate;

import br.com.fatec.easyDrive.DTO.pessoa.DadosDetalhamentoPessoa;
import br.com.fatec.easyDrive.entity.Cliente;
import br.com.fatec.easyDrive.enumerator.PlanoAssinaturaEnum;
import br.com.fatec.easyDrive.enumerator.StatusEnum;

public record DadosDetalhamentoCliente(
        Long id,
        String numeroCNH,
        LocalDate validadeCNH,
        DadosDetalhamentoPessoa pessoa,
        PlanoAssinaturaEnum plano,
        Long pontuacao,
        StatusEnum status
) {
    public DadosDetalhamentoCliente(Cliente cliente) {
        this(
            cliente.getId(),
            cliente.getNumeroCNH(),
            cliente.getValidadeCNH(),
            new DadosDetalhamentoPessoa(cliente.getPessoa()),
            cliente.getPlanoAssinatura(),
            cliente.getPontuacao(),
            cliente.getStatus()
        );
    }
}
