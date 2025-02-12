package br.com.fatec.easyDrive.exception.tratamento;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.fatec.easyDrive.exception.NotFoundException;

@RestControllerAdvice
public class NotFoundHandler {
    @ExceptionHandler(NotFoundException.class) 
    public ResponseEntity<DadosInvalidos> tratarDadosInvalidos(NotFoundException ex) {
        DadosInvalidos dadosInvalidos = new DadosInvalidos(ex.getMessage());
        return ResponseEntity.badRequest().body(dadosInvalidos);
    }

    public record DadosInvalidos(String mensagem) {
        public DadosInvalidos(FieldError erro) {
            this(erro.getDefaultMessage());
        }
    }
}
