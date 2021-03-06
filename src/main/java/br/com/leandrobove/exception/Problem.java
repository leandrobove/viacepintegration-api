package br.com.leandrobove.exception;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

//não permite serializar atributos nulos
@JsonInclude(value = Include.NON_NULL)

@Getter
@Builder
public class Problem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer status;
	private String title;
	private String detail;

	private List<FieldProblem> fields;

	private String userMessage; // UI message
	private OffsetDateTime timestamp;
}
