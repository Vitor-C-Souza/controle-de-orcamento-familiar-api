package br.com.vitor.controle_de_orcamento_familiar.domain.service;

import br.com.vitor.controle_de_orcamento_familiar.domain.dto.receitaDTORequest;
import br.com.vitor.controle_de_orcamento_familiar.domain.dto.receitaDTOResponse;
import br.com.vitor.controle_de_orcamento_familiar.infra.exception.equalReceitaException;
import br.com.vitor.controle_de_orcamento_familiar.infra.exception.receitaNotFoundException;
import br.com.vitor.controle_de_orcamento_familiar.domain.model.Receita;
import br.com.vitor.controle_de_orcamento_familiar.domain.repository.receitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class receitaService {
    @Autowired
    private receitaRepository repository;

    public receitaDTOResponse cadastrarReceita(receitaDTORequest dto) {
        Receita receita = new Receita(dto);

        validacao(receita);

        repository.save(receita);
        return new receitaDTOResponse(receita);
    }

    public Page<receitaDTOResponse> listarReceitas(Pageable paginacao, String descricao) {
        Page<Receita> receitas = repository.findAllByDescricao(paginacao, descricao);
        return receitas.map(receitaDTOResponse::new);
    }

    public receitaDTOResponse encontrarReceita(Long id) {
        Optional<Receita> receita = repository.findById(id);

        if (receita.isEmpty()) {
            throw new receitaNotFoundException();
        }

        Receita receitaPresent = receita.get();
        return new receitaDTOResponse(receitaPresent);

    }

    public receitaDTOResponse atualizarReceita(Long id, receitaDTORequest dto) {
        Receita receita = repository.getReferenceById(id);
        receita.update(dto);

        validacao(receita);

        repository.save(receita);
        return new receitaDTOResponse(receita);
    }

    public void deletarReceita(Long id) {
        repository.deleteById(id);
    }

    public Page<receitaDTOResponse> listarReceitasPorMes(Pageable paginacao, int ano, int mes) {
        Page<Receita> receitas = repository.listarReceitasPorMes(paginacao, ano, mes);

        return receitas.map(receitaDTOResponse::new);
    }

    private void validacao(Receita receita) {
        boolean exists = repository.existsByDescricaoAndMonth(receita.getDescricao(), receita.getData().getMonthValue(), receita.getData().getYear());
        if (exists) {
            throw new equalReceitaException("Não pode ter receitas iguais no mesmo mês");
        }
    }
}
