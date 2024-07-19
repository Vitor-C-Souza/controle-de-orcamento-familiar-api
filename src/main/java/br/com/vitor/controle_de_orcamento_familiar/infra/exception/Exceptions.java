package br.com.vitor.controle_de_orcamento_familiar.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Exceptions {

    @ExceptionHandler(equalReceitaException.class)
    public ResponseEntity<String> receitasIguaisNoMes(equalReceitaException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(receitaNotFoundException.class)
    public ResponseEntity<Void> receitaNotFound(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(equalDespesaException.class)
    public ResponseEntity<String> despesasIguaisNoMes(equalDespesaException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(despesaNotFoundException.class)
    public ResponseEntity<Void> despesaNotFound(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> categoriaInvalida(IllegalArgumentException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(usuarioDuplicadoException.class)
    public ResponseEntity<String> criacaoDeUsuarioJaExistente(usuarioDuplicadoException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
