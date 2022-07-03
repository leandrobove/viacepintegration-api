package br.com.leandrobove.exception;

import br.com.leandrobove.util.CepUtils;

public class CepInvalidoException extends ApiException {

	private static final String MSG_ERRO_CEP_INVALIDO = "O cep %s informado não existe ou é inválido.";

	private static final long serialVersionUID = 1L;

	public CepInvalidoException(String cep) {
		super(String.format(MSG_ERRO_CEP_INVALIDO, new CepUtils().setCep(cep).getCepFormatado()));
	}

	public CepInvalidoException(String mensagem, Throwable e) {
		super(MSG_ERRO_CEP_INVALIDO, e);
	}

}
