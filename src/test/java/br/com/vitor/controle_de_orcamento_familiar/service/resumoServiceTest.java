package br.com.vitor.controle_de_orcamento_familiar.service;

import br.com.vitor.controle_de_orcamento_familiar.domain.dto.resumoDto;
import br.com.vitor.controle_de_orcamento_familiar.domain.model.CategoriasDespesa;
import br.com.vitor.controle_de_orcamento_familiar.domain.repository.despesaRepository;
import br.com.vitor.controle_de_orcamento_familiar.domain.repository.receitaRepository;
import br.com.vitor.controle_de_orcamento_familiar.domain.service.resumoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class resumoServiceTest {
    @InjectMocks
    private resumoService service;

    @Mock
    private receitaRepository receitaRepository;

    @Mock
    private despesaRepository despesaRepository;

    @Test
    void DeveRetornarUmResumoDoMes(){
        //ARRANGE
        int ano = 2024;
        int mes = 12;

        double receitaValorTotalMes = 6000.00;
        double despesaValorTotalMes = 5500.00;

        when(receitaRepository.valorTotalMes(ano, mes)).thenReturn(receitaValorTotalMes);
        when(despesaRepository.valorTotalMes(ano, mes)).thenReturn(despesaValorTotalMes);

        Map<String, Double> gastoPorCategoria = new HashMap<>();
        for (CategoriasDespesa categoria : CategoriasDespesa.values()) {
            gastoPorCategoria.put(categoria.name(), 100.0);
            when(despesaRepository.valorDaCategoriaDoMes(categoria, ano, mes)).thenReturn(100.0);
        }

        //ACT
        resumoDto dto = service.resumoDoMes(ano, mes);

        //ASSERT
        assertEquals(receitaValorTotalMes, dto.receitaValorTotalMes());
        assertEquals(despesaValorTotalMes, dto.despesaValorTotalMes());
        assertEquals((receitaValorTotalMes - despesaValorTotalMes), dto.SaldoMes());
        assertEquals(gastoPorCategoria, dto.gastoPorCategoria());
    }
}