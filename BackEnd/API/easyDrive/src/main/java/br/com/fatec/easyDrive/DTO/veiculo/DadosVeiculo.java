package br.com.fatec.easyDrive.DTO.veiculo;

import br.com.fatec.easyDrive.enumerator.CategoriaEnum;
import br.com.fatec.easyDrive.enumerator.TipoCombustivelEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DadosVeiculo(

    @NotBlank(message = "Placa é obrigatória")
    String placa,

    @NotBlank(message = "Modelo é obrigatório")
    String modelo,

    @NotBlank(message = "Marca é obrigatória")
    String marca,

    @NotNull(message = "Ano é obrigatório")
    @Positive(message = "O ano deve ser maior que zero")
    Integer ano,
    
    @NotNull(message = "Quilometragem é obrigatório")
    @Positive(message = "A quilometragem deve ser maior que zero")
    Long quilometragem,

    @NotNull(message = "Categoria é obrigatória")
    CategoriaEnum categoria,

    @NotBlank(message = "Cor é obrigatória")
    String cor,
    
    @NotNull(message = "Tipo do Combustivel é obrigatório")
    TipoCombustivelEnum tipoCombustivel,
    
    @NotNull(message = "O valor da diária é obrigatório")
    @Positive(message = "O valor da diária deve ser maior que zero")
    Double valorDiaria
) {
}
