package br.com.fatec.easyDrive.DTO.Veiculo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record DadosVeiculo(

    @NotBlank(message = "Placa é obrigatória")
    @Pattern(regexp = "^[A-Z]{3}-\\d{4}$", message = "Formato de placa inválido. Utilize o formato AAA-0000")
    String placa,

    @NotBlank(message = "Modelo é obrigatório")
    String modelo,

    @NotBlank(message = "Marca é obrigatória")
    String marca,

    @NotNull(message = "Ano é obrigatório")
    @Positive(message = "O ano deve ser maior que zero")
    Integer ano,

    @NotBlank(message = "Categoria é obrigatória")
    String categoria,

    @NotBlank(message = "Cor é obrigatória")
    String cor,

    @NotBlank(message = "Status é obrigatório")
    String status
) {
}
