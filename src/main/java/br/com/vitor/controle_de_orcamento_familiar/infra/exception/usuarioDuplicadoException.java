package br.com.vitor.controle_de_orcamento_familiar.infra.exception;

public class usuarioDuplicadoException extends RuntimeException {
    private final String message;
    public usuarioDuplicadoException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
