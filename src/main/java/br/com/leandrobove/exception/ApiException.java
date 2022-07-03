package br.com.leandrobove.exception;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ApiException() {
		super();
	}

	public ApiException(String mensagem) {
		super(mensagem);
	}

	public ApiException(String mensagem, Throwable e) {
		super(mensagem, e);
	}

}
