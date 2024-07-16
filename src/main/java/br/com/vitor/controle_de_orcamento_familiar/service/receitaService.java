package br.com.vitor.controle_de_orcamento_familiar.service;

import br.com.vitor.controle_de_orcamento_familiar.dto.receitaDTORequest;
import br.com.vitor.controle_de_orcamento_familiar.dto.receitaDTOResponse;
import br.com.vitor.controle_de_orcamento_familiar.exception.equalReceitaException;
import br.com.vitor.controle_de_orcamento_familiar.exception.receitaNotFoundException;
import br.com.vitor.controle_de_orcamento_familiar.model.Receita;
import br.com.vitor.controle_de_orcamento_familiar.repository.receitaRepository;
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

        boolean exists = repository.existsByDescricaoAndMonth(receita.getDescricao(), receita.getData().getMonthValue());
        if (exists) {
            throw new equalReceitaException("Não pode ter receitas iguais no mesmo mês");
        }

        repository.save(receita);
        return new receitaDTOResponse(receita);
    }

    public Page<receitaDTOResponse> listarReceitas(Pageable paginacao) {
        Page<Receita> receitas = repository.findAll(paginacao);
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

        boolean exists = repository.existsByDescricaoAndMonth(receita.getDescricao(), receita.getData().getMonthValue());
        if (exists) {
            throw new equalReceitaException("Não pode ter receitas iguais no mesmo mês");
        }

        repository.save(receita);
        return new receitaDTOResponse(receita);
    }

    public void deletarReceita(Long id) {
        repository.deleteById(id);
    }
}
