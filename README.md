#Projeto de API para consulta de CEP com Cache
O objetivo deste projeto é criar uma REST API para consulta de CEP utilizando cache local e distribuído e consumir a API do ViaCep ([https://viacep.com.br/](https://viacep.com.br/))

## Tecnologias Utilizadas
* Java 11
* Spring WebFlux
* Spring WebClient (Reactive HTTP Client)
* Spring Data Reactive Redis
* Spring Scheduler
* Redis
* Maven

## Pré-requisitos
É necessário ter as ferramentas instaladas:

* Redis
* Java 11
* Maven


## Como Executar

1- Execute o Redis na porta padrão 6379.

2 - Execute o comando na raiz do projeto:

Para utilizar o cache em memória: 

```
mvn spring-boot:run -D spring-boot.run.arguments="--app-config.cache.strategy=local"
```

Para utilizar o cache usando o Redis: 

```
mvn spring-boot:run -D spring-boot.run.arguments="--app-config.cache.strategy=redis"
```

3 - Realizar uma requisição HTTP para o endpoint abaixo.

## Endpoint
* GET -> http://localhost:8080/api/v1/cep/{cep}
* Exemplo de requisição: GET -> http://localhost:8080/api/v1/cep/01001000

Exemplo de Resposta:

```json
{
	"cep": "01001-000",
	"logradouro": "Praça da Sé",
	"complemento": "lado ímpar",
	"bairro": "Sé",
	"localidade": "São Paulo",
	"uf": "SP"
}
```

## Agradecimentos
Projeto didático desenvolvido baseado nos vídeos do canal do YouTube [Comics & Code](https://youtube.com/playlist?list=PLTnZgkfXPBX5uJSZxIVMPbNxNXfdRu15p).