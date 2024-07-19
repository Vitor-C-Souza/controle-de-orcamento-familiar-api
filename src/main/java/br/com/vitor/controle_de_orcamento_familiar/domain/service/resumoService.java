package br.com.vitor.controle_de_orcamento_familiar.domain.service;

import br.com.vitor.controle_de_orcamento_familiar.domain.dto.resumoDto;
import br.com.vitor.controle_de_orcamento_familiar.domain.model.CategoriasDespesa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class resumoService {

    @Autowired
    private br.com.vitor.controle_de_orcamento_familiar.domain.repository.receitaRepository receitaRepository;

    @Autowired
    private br.com.vitor.controle_de_orcamento_familiar.domain.repository.despesaRepository despesaRepository;

    public resumoDto resumoDoMes(int ano, int mes) {

        double receitaValorTotalMes = receitaRepository.valorTotalMes(ano, mes);
        double despesaValorTotalMes = despesaRepository.valorTotalMes(ano, mes);
        double saldoMes = receitaValorTotalMes - despesaValorTotalMes;

        Map<String, Double> gastoPorCategoria = new HashMap<>();
        var categorias = CategoriasDespesa.values();
        for(CategoriasDespesa categoria: categorias){
            gastoPorCategoria.put(categoria.name(), despesaRepository.valorDaCategoriaDoMes(categoria , ano, mes));
        }

        return new resumoDto(receitaValorTotalMes, despesaValorTotalMes, saldoMes, gastoPorCategoria);
    }
}
