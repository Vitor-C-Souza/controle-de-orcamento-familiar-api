package br.com.vitor.controle_de_orcamento_familiar.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Exceptions {

    @ExceptionHandler(equalReceitaException.class)
    public ResponseEntity receitasIguaisNoMes(equalReceitaException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(receitaNotFoundException.class)
    public ResponseEntity receitaNotFound(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(equalDespesaException.class)
    public ResponseEntity despesasIguaisNoMes(equalDespesaException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(despesaNotFoundException.class)
    public ResponseEntity despesaNotFound(){
        return ResponseEntity.notFound().build();
    }
}
