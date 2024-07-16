package br.com.vitor.controle_de_orcamento_familiar.exception;

public class equalDespesaException extends RuntimeException {
    private final String message;
    public equalDespesaException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
