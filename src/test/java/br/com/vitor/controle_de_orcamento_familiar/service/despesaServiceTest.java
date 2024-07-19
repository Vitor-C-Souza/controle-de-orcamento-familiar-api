package br.com.vitor.controle_de_orcamento_familiar.service;

import br.com.vitor.controle_de_orcamento_familiar.domain.dto.despesaDTORequest;
import br.com.vitor.controle_de_orcamento_familiar.domain.dto.despesaDTOResponse;
import br.com.vitor.controle_de_orcamento_familiar.domain.model.Despesa;
import br.com.vitor.controle_de_orcamento_familiar.domain.repository.despesaRepository;
import br.com.vitor.controle_de_orcamento_familiar.domain.service.despesaService;
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
class despesaServiceTest {
    @InjectMocks
    private despesaService service;

    @Mock
    private despesaRepository repository;

    @Captor
    private ArgumentCaptor<Despesa> despesaArgumentCaptor;

    private despesaDTORequest dto;

    private Despesa despesa;

    @BeforeEach
    void setUp() {
        dto = new despesaDTORequest(
                "comida",
                150.00,
                "14/12/2024",
                "lazer");

        despesa = new Despesa(dto);
    }

    @Test
    void deveCadastrarUmaDespesaNova(){
        //ARRANGE
        when(repository.existsByDescricaoAndMonthAndYear(despesa.getDescricao(), despesa.getData().getMonthValue(), despesa.getData().getYear())).thenReturn(false);

        //ACT
        despesaDTOResponse dtoResponse = service.cadastrarDespesa(dto);

        //ASSERT
        then(repository).should().save(despesaArgumentCaptor.capture());
        Despesa despesaSalva = despesaArgumentCaptor.getValue();
        assertEquals(dtoResponse.data(), despesaSalva.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        assertEquals(dtoResponse.descricao(), despesaSalva.getDescricao());
        assertEquals(dtoResponse.id(), despesaSalva.getId());
        assertEquals(dtoResponse.valor(), despesaSalva.getValor());
    }

    @Test
    void DeveListarTodasAsDespesasComDescricaoEnviada(){
        //ARRANGE
        String descricao = "comida";
        Pageable paginacao = PageRequest.of(0, 10);
        Page<Despesa> page = new PageImpl<>(List.of(despesa), paginacao, 1);

        when(repository.findAllByDescricao(paginacao, descricao)).thenReturn(page);

        //ACT
        Page<despesaDTOResponse> dtoResponses = service.listarDespesas(paginacao, descricao);

        //ASSERT
        assertEquals(1, dtoResponses.getTotalElements());
        assertEquals(despesa.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), dtoResponses.getContent().get(0).data());
        assertEquals(despesa.getDescricao(), dtoResponses.getContent().get(0).descricao());
        assertEquals(despesa.getId(), dtoResponses.getContent().get(0).id());
        assertEquals(despesa.getValor(), dtoResponses.getContent().get(0).valor());
    }

    @Test
    void DeveListarTodasAsDespesas(){
        String descricao = null;
        Pageable paginacao = PageRequest.of(0, 10);
        Page<Despesa> page = new PageImpl<>(List.of(despesa), paginacao, 1);

        when(repository.findAllByDescricao(paginacao, descricao)).thenReturn(page);

        //ACT
        Page<despesaDTOResponse> dtoResponses = service.listarDespesas(paginacao, descricao);

        //ASSERT
        assertEquals(1, dtoResponses.getTotalElements());
        assertEquals(despesa.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), dtoResponses.getContent().get(0).data());
        assertEquals(despesa.getDescricao(), dtoResponses.getContent().get(0).descricao());
        assertEquals(despesa.getId(), dtoResponses.getContent().get(0).id());
        assertEquals(despesa.getValor(), dtoResponses.getContent().get(0).valor());
    }

    @Test
    void deveEncontrarUmaDespesaDeterminada(){
        //ARRANGE
        Long id = 1L;
        Optional<Despesa> despesaOptional = Optional.of(despesa);
        when(repository.findById(id)).thenReturn(despesaOptional);

        //ACT
        despesaDTOResponse dtoResponse = service.encontrarDespesa(id);

        //ASSERT
        assertEquals(dtoResponse.valor(), despesaOptional.get().getValor());
        assertEquals(dtoResponse.data(), despesaOptional.get().getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        assertEquals(dtoResponse.descricao(), despesaOptional.get().getDescricao());
        assertEquals(dtoResponse.id(), despesaOptional.get().getId());
    }

    @Test
    void deveAtualizarUmaDespesa(){
        //ARRANGE
        Long id = 1L;
        when(repository.existsByDescricaoAndMonthAndYear(despesa.getDescricao(), despesa.getData().getMonthValue(), despesa.getData().getYear())).thenReturn(false);
        when(repository.getReferenceById(id)).thenReturn(despesa);

        //ACT
        despesaDTOResponse dtoResponse = service.atualizarDespesa(id, dto);

        //ASSERT
        then(repository).should().save(despesaArgumentCaptor.capture());
        Despesa DespesaSalva = despesaArgumentCaptor.getValue();
        assertEquals(dtoResponse.data(), DespesaSalva.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        assertEquals(dtoResponse.descricao(), DespesaSalva.getDescricao());
        assertEquals(dtoResponse.id(), DespesaSalva.getId());
        assertEquals(dtoResponse.valor(), DespesaSalva.getValor());
    }

    @Test
    void deveDeletarUmaDespesa(){
        //ARRANGE
        Long id = 1L;

        //ACT
        service.deletarDespesa(id);

        //ASSERT
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void listeDespesasPorMes(){
        //ARRANGE
        int ano = 2024;
        int mes = 12;
        Pageable paginacao = PageRequest.of(0, 10);
        Page<Despesa> page = new PageImpl<>(List.of(despesa), paginacao, 1);

        when(repository.listarDespesasPorMes(paginacao, ano, mes)).thenReturn(page);

        //ACT
        Page<despesaDTOResponse> dtoResponses = service.listarDespesasPorMes(paginacao, ano, mes);

        //ASSERT
        assertEquals(1, dtoResponses.getTotalElements());
        assertEquals(despesa.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), dtoResponses.getContent().get(0).data());
        assertEquals(despesa.getDescricao(), dtoResponses.getContent().get(0).descricao());
        assertEquals(despesa.getId(), dtoResponses.getContent().get(0).id());
        assertEquals(despesa.getValor(), dtoResponses.getContent().get(0).valor());
    }
}