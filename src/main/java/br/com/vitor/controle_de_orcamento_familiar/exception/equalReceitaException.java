package br.com.vitor.controle_de_orcamento_familiar.exception;

public class equalReceitaException extends RuntimeException {

    private final String message;

    public equalReceitaException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
