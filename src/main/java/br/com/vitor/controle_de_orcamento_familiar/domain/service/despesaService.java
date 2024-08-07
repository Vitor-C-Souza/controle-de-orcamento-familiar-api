package br.com.vitor.controle_de_orcamento_familiar.domain.service;

import br.com.vitor.controle_de_orcamento_familiar.domain.model.Despesa;
import br.com.vitor.controle_de_orcamento_familiar.domain.dto.despesaDTORequest;
import br.com.vitor.controle_de_orcamento_familiar.domain.dto.despesaDTOResponse;
import br.com.vitor.controle_de_orcamento_familiar.infra.exception.despesaNotFoundException;
import br.com.vitor.controle_de_orcamento_familiar.infra.exception.equalDespesaException;
import br.com.vitor.controle_de_orcamento_familiar.domain.repository.despesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class despesaService {

    @Autowired
    private despesaRepository repository;

    public despesaDTOResponse cadastrarDespesa(despesaDTORequest dto) {
        Despesa despesa = new Despesa(dto);

        validacao(despesa);

        repository.save(despesa);
        return new despesaDTOResponse(despesa);
    }

    public Page<despesaDTOResponse> listarDespesas(Pageable paginacao, String descricao) {
        Page<Despesa> despesas = repository.findAllByDescricao(paginacao, descricao);
        return despesas.map(despesaDTOResponse::new);
    }

    public despesaDTOResponse encontrarDespesa(Long id) {
        Optional<Despesa> despesa = repository.findById(id);
        if (despesa.isEmpty()) {
            throw new despesaNotFoundException();
        }
        Despesa despesaPresent = despesa.get();
        return new despesaDTOResponse(despesaPresent);
    }

    public despesaDTOResponse atualizarDespesa(Long id, despesaDTORequest dto) {
        Despesa despesa = repository.getReferenceById(id);
        despesa.update(dto);

        validacao(despesa);

        repository.save(despesa);
        return new despesaDTOResponse(despesa);
    }

    public void deletarDespesa(Long id) {
        repository.deleteById(id);
    }

    public Page<despesaDTOResponse> listarDespesasPorMes(Pageable paginacao, int ano, int mes) {
        Page<Despesa> despesa = repository.listarDespesasPorMes(paginacao, ano, mes);

        return despesa.map(despesaDTOResponse::new);
    }

    private void validacao(Despesa despesa){
        boolean exists = repository.existsByDescricaoAndMonthAndYear(despesa.getDescricao(), despesa.getData().getMonthValue(), despesa.getData().getYear());
        if (exists) {
            throw new equalDespesaException("Não pode ter despesas iguais no mesmo mês");
        }
    }
}
