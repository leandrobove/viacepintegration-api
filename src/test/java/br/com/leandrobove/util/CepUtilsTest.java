package br.com.leandrobove.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CepUtilsTest {

	private CepUtils cepUtils;

	@BeforeEach
	public void beforeEach() {
		cepUtils = new CepUtils();
	}

	@Test
	public void deveRetornarUmCepFormatado() {

		String expected = "55555-555";
		String input = "55555555";

		String exec = cepUtils.setCep(input).getCepFormatado();

		assertEquals(expected, exec);
	}

	@Test
	public void deveRetornarUmCepLimpo() {
		String expected = "55555555";
		String input = "55555-555";

		String exec = cepUtils.setCep(input).getCepLimpo();

		assertEquals(expected, exec);
	}

	@Test
	public void deveValidarUmCep() {
		String expected = "55555555";

		boolean validaCepLimpo = cepUtils.setCep(expected).validaCep();

		assertTrue(validaCepLimpo);
	}

	@Test
	public void deveValidarUmCepComDadosInvalidos() {
		String expected = "asdasdasd";

		boolean validaCepLimpo = cepUtils.setCep(expected).validaCep();

		assertFalse(validaCepLimpo);
	}

}
