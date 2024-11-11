package br.com.monitoramento.config.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ErroDeValidacaoHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroDeFormularioDto> handle(MethodArgumentNotValidException exception) {
		List<ErroDeFormularioDto> dto = new ArrayList<>();
		
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e -> {
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErroDeFormularioDto erro = new ErroDeFormularioDto(e.getField(), mensagem);
			dto.add(erro);
		});
		
		return dto;
	}
	
	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<Object> handleAnyException(Exception e, WebRequest request) {
		/*
		 * String message = NestedExceptionUtils.getRootCause(e).getMessage();
		 * HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; if
		 * (message.matches("Duplicate entry(.*)")) { message =
		 * "Já exste um registro com os dados informados."; status =
		 * HttpStatus.FORBIDDEN; }
		 */
//		return new ResponseEntity<>(message, new HttpHeaders(), status);
		Throwable rootCause = NestedExceptionUtils.getRootCause(e);
		String message = e.getMessage();
		if (rootCause != null) {
			message = rootCause.getMessage();
		}
		return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.FORBIDDEN);
	}

}
