package br.com.fatec.easyDrive.DTO.veiculo;

import br.com.fatec.easyDrive.entity.Veiculo;
import br.com.fatec.easyDrive.enumerator.TipoCombustivelEnum;

public record DadosDetalhamentoVeiculo(
        Long id,
        String placa,
        String modelo,
        String marca,
        Integer ano,
        Long quilometragem,
        String categoria,
        String cor,
        TipoCombustivelEnum tipoCombustivel,
        Double valorDiaria,
        String status
) {
    public DadosDetalhamentoVeiculo(Veiculo veiculo) {
        this(
            veiculo.getId(),
            veiculo.getPlaca(),
            veiculo.getModelo(),
            veiculo.getMarca(),
            veiculo.getAno(),
            veiculo.getQuilometragem(),
            veiculo.getCategoria().getDescricao(),
            veiculo.getCor(),
            veiculo.getTipoCombustivel(),
            veiculo.getValorDiaria(),
            veiculo.getStatus().getDescricao()
        );
    }
}
