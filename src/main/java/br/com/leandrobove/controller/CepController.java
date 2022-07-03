package br.com.leandrobove.controller;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.leandrobove.dto.CepResponse;
import br.com.leandrobove.service.CepService;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("/api/v1/cep")
public class CepController {

	@Autowired
	private CepService cepService;

	@GetMapping(value = "/{cep}")
	public Mono<CepResponse> getCep(
			@PathVariable 
			@NotBlank 
			@Size(min = 8, max = 8) 
			@Pattern(regexp = "[0-9]+") String cep) {
		return cepService.findByCep(cep);
	}

}
