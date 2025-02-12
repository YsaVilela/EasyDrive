package br.com.fatec.easyDrive.DTO.veiculo;

import br.com.fatec.easyDrive.entity.Veiculo;

public record DadosDetalhamentoVeiculo(
        Long id,
        String placa,
        String modelo,
        String marca,
        int ano,
        String categoria,
        String cor,
        String status
) {
    public DadosDetalhamentoVeiculo(Veiculo veiculo) {
        this(
            veiculo.getId(),
            veiculo.getPlaca(),
            veiculo.getModelo(),
            veiculo.getMarca(),
            veiculo.getAno(),
            veiculo.getCategoria(),
            veiculo.getCor(),
            veiculo.getStatus()
        );
    }
}
