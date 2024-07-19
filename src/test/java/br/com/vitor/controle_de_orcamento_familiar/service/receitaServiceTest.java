package br.com.vitor.controle_de_orcamento_familiar.service;

import br.com.vitor.controle_de_orcamento_familiar.domain.dto.receitaDTORequest;
import br.com.vitor.controle_de_orcamento_familiar.domain.dto.receitaDTOResponse;
import br.com.vitor.controle_de_orcamento_familiar.domain.model.Receita;
import br.com.vitor.controle_de_orcamento_familiar.domain.repository.receitaRepository;
import br.com.vitor.controle_de_orcamento_familiar.domain.service.receitaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class receitaServiceTest {

    @InjectMocks
    private receitaService service;

    @Mock
    private receitaRepository receitaRepository;

    @Captor
    private ArgumentCaptor<Receita> receitaArgumentCaptor;

    private receitaDTORequest dto;

    private Receita receita;

    @BeforeEach
    void setUp() {
        dto = new receitaDTORequest(
                "comida",
                150.00,
                "14/12/2024");

        receita = new Receita(dto);
    }

    @Test
    void deveCadastrarUmaReceitaNova(){
        //ARRANGE
        when(receitaRepository.existsByDescricaoAndMonth(receita.getDescricao(), receita.getData().getMonthValue(), receita.getData().getYear())).thenReturn(false);

        //ACT
        receitaDTOResponse dtoResponse = service.cadastrarReceita(dto);

        //ASSERT
        then(receitaRepository).should().save(receitaArgumentCaptor.capture());
        Receita receitaSalva = receitaArgumentCaptor.getValue();
        assertEquals(dtoResponse.data(), receitaSalva.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        assertEquals(dtoResponse.descricao(), receitaSalva.getDescricao());
        assertEquals(dtoResponse.id(), receitaSalva.getId());
        assertEquals(dtoResponse.valor(), receitaSalva.getValor());
    }

    @Test
    void DeveListarTodasAsReceitasComDescricaoEnviada(){
        //ARRANGE
        String descricao = "comida";
        Pageable paginacao = PageRequest.of(0, 10);
        Page<Receita> page = new PageImpl<>(List.of(receita), paginacao, 1);

        when(receitaRepository.findAllByDescricao(paginacao, descricao)).thenReturn(page);

        //ACT
        Page<receitaDTOResponse> dtoResponses = service.listarReceitas(paginacao, descricao);

        //ASSERT
        assertEquals(1, dtoResponses.getTotalElements());
        assertEquals(receita.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), dtoResponses.getContent().get(0).data());
        assertEquals(receita.getDescricao(), dtoResponses.getContent().get(0).descricao());
        assertEquals(receita.getId(), dtoResponses.getContent().get(0).id());
        assertEquals(receita.getValor(), dtoResponses.getContent().get(0).valor());
    }

    @Test
    void DeveListarTodasAsReceitas(){
        String descricao = null;
        Pageable paginacao = PageRequest.of(0, 10);
        Page<Receita> page = new PageImpl<>(List.of(receita), paginacao, 1);

        when(receitaRepository.findAllByDescricao(paginacao, descricao)).thenReturn(page);

        //ACT
        Page<receitaDTOResponse> dtoResponses = service.listarReceitas(paginacao, descricao);

        //ASSERT
        assertEquals(1, dtoResponses.getTotalElements());
        assertEquals(receita.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), dtoResponses.getContent().get(0).data());
        assertEquals(receita.getDescricao(), dtoResponses.getContent().get(0).descricao());
        assertEquals(receita.getId(), dtoResponses.getContent().get(0).id());
        assertEquals(receita.getValor(), dtoResponses.getContent().get(0).valor());
    }

    @Test
    void deveEncontrarUmaReceitaDeterminada(){
        //ARRANGE
        Long id = 1L;
        Optional<Receita> receitaOptional = Optional.of(receita);
        when(receitaRepository.findById(id)).thenReturn(receitaOptional);

        //ACT
        receitaDTOResponse dtoResponse = service.encontrarReceita(id);

        //ASSERT
        assertEquals(dtoResponse.valor(), receitaOptional.get().getValor());
        assertEquals(dtoResponse.data(), receitaOptional.get().getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        assertEquals(dtoResponse.descricao(), receitaOptional.get().getDescricao());
        assertEquals(dtoResponse.id(), receitaOptional.get().getId());
    }

    @Test
    void deveAtualizarUmaReceita(){
        //ARRANGE
        Long id = 1L;
        when(receitaRepository.existsByDescricaoAndMonth(receita.getDescricao(), receita.getData().getMonthValue(), receita.getData().getYear())).thenReturn(false);
        when(receitaRepository.getReferenceById(id)).thenReturn(receita);

        //ACT
        receitaDTOResponse dtoResponse = service.atualizarReceita(id, dto);

        //ASSERT
        then(receitaRepository).should().save(receitaArgumentCaptor.capture());
        Receita receitaSalva = receitaArgumentCaptor.getValue();
        assertEquals(dtoResponse.data(), receitaSalva.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        assertEquals(dtoResponse.descricao(), receitaSalva.getDescricao());
        assertEquals(dtoResponse.id(), receitaSalva.getId());
        assertEquals(dtoResponse.valor(), receitaSalva.getValor());
    }

    @Test
    void deveDeletarUmaReceita(){
        //ARRANGE
        Long id = 1L;

        //ACT
        service.deletarReceita(id);

        //ASSERT
        verify(receitaRepository, times(1)).deleteById(id);
    }

    @Test
    void listeReceitasPorMes(){
        //ARRANGE
        int ano = 2024;
        int mes = 12;
        Pageable paginacao = PageRequest.of(0, 10);
        Page<Receita> page = new PageImpl<>(List.of(receita), paginacao, 1);

        when(receitaRepository.listarReceitasPorMes(paginacao, ano, mes)).thenReturn(page);

        //ACT
        Page<receitaDTOResponse> dtoResponses = service.listarReceitasPorMes(paginacao, ano, mes);

        //ASSERT
        assertEquals(1, dtoResponses.getTotalElements());
        assertEquals(receita.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), dtoResponses.getContent().get(0).data());
        assertEquals(receita.getDescricao(), dtoResponses.getContent().get(0).descricao());
        assertEquals(receita.getId(), dtoResponses.getContent().get(0).id());
        assertEquals(receita.getValor(), dtoResponses.getContent().get(0).valor());
    }
}