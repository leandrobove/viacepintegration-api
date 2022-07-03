package br.com.leandrobove.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CepUtils {

	private static final Integer TAMANHO_CEP_ESPERADO = 8;
	private String cep;

	public CepUtils setCep(String cep) {
		this.cep = cep;

		return this;
	}

	public boolean validaCep() {
		if (!cep.matches("\\d{" + TAMANHO_CEP_ESPERADO + "}")) {
			return false;
		}
		return true;
	}

	public void removerCaracteresEspeciais() {
		this.cep = cep.replaceAll("[^0-9]", "");
	}

	public String getCepLimpo() {
		this.removerCaracteresEspeciais();

		return this.cep;
	}

	public String getCepFormatado() {
		String cepLimpo = this.getCepLimpo();
		return cepLimpo.substring(0, 5) + "-" + cepLimpo.substring(5);
	}

}
