package br.com.vitor.controle_de_orcamento_familiar.dto;

public record resumoDto(
        double receitaValorTotalMes,
        double despesaValorTotalMes,
        double SaldoMes,
        java.util.Map<String, Double> gastoPorCategoria
) {
}
