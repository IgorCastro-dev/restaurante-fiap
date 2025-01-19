[restaurante-fiap.postman_collection.json](https://github.com/user-attachments/files/18470160/restaurante-fiap.postman_collection.json)
# Uma Api em Java de gerenciamento de restaurantes

Uma API de um sistema robusto que permite que todos os restaurantes gerenciem eficientemente suas operações, enquanto os clientes podem consultar informações, deixar avaliações e fazer pedidos online.


![Logo](https://github.com/user-attachments/assets/44d811ba-d0c0-4e9d-bcc4-bcfd8ad9ea1d)

## Stack utilizada

**Back-end:** Java, Spring-Boot,JPA, My-SQL, Swagger, Docker, flyway, Segurança (JWT Oauth2)


## Funcionalidades

- CRUD de dados
- Autenticação com jwt
- atualização de senha
- login de usuários


## Rodando localmente

Entre no terminal

Clone o projeto

```bash
  git clone https://github.com/IgorCastro-dev/UFF-Materias.git
```

Entre no diretório do projeto

```bash
  cd restaurante
```

Execute o Docker Compose:

```bash
  docker-compose up -d
```

Compile e Execute sua API Java:

```bash
  ./mvnw spring-boot:run
```


## Screenshots

![Swagger](https://github.com/user-attachments/assets/6d468bfe-6948-471a-b127-4231f0b21455)

![Swagger](https://github.com/user-attachments/assets/9d2a1a37-ae4f-4e22-aa60-35e4d9fc4481)

![Swagger](https://github.com/user-attachments/assets/f8139efc-7f1b-44e7-917f-624e8c61c4db)

## Documentação

Ao rodar a API acesse a url: http://localhost:8080/restaurante/swagger-ui/index.html#/

## Collections do Postman


[Uploading {
	"info": {
		"_postman_id": "cb492927-affe-49e9-836e-7b5cd2073477",
		"name": "restaurante-fiap",
		"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json",
		"_exporter_id": "15545233"
	},
	"item": [
		{
			"name": "Validação",
			"item": [
				{
					"name": "login",
					"request": {
						"method": "POST",
						"header": [],
						"body": {
							"mode": "raw",
							"raw": "{\r\n    \"username\":\"moliveira\",\r\n    \"password\":\"M123\"\r\n}",
							"options": {
								"raw": {
									"language": "json"
								}
							}
						},
						"url": {
							"raw": "http://localhost:8080/restaurante/login",
							"protocol": "http",
							"host": [
								"localhost"
							],
							"port": "8080",
							"path": [
								"restaurante",
								"login"
							]
						}
					},
					"response": []
				},
				{
					"name": "Troca senha",
					"request": {
						"auth": {
							"type": "bearer",
							"bearer": [
								{
									"key": "token",
									"value": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJpY2FzdHJvIiwiaXNzIjoiVG9rZW4gZG8gYXBwIiwiaWF0IjoxNzM3MjkzNTYxLCJleHAiOjE3MzcyOTQ0NjF9.7lVzyDedME5bsr-iSI-TdbB0CHRi0aZbfEfUNFEBqgY",
									"type": "string"
								}
							]
						},
						"method": "POST",
						"header": [],
						"body": {
							"mode": "raw",
							"raw": "{\r\n    \"senhaAtual\":\"M123\",\r\n    \"novaSenha\":\"M1234\",\r\n    \"confirmaSenha\":\"M1234\"\r\n}",
							"options": {
								"raw": {
									"language": "json"
								}
							}
						},
						"url": {
							"raw": "http://localhost:8080/restaurante/troca-senha",
							"protocol": "http",
							"host": [
								"localhost"
							],
							"port": "8080",
							"path": [
								"restaurante",
								"troca-senha"
							]
						}
					},
					"response": []
				}
			]
		},
		{
			"name": "gerenciamento de usuários",
			"item": [
				{
					"name": "listar usuarios",
					"protocolProfileBehavior": {
						"disableBodyPruning": true
					},
					"request": {
						"auth": {
							"type": "bearer",
							"bearer": [
								{
									"key": "token",
									"value": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJpY2FzdHJvIiwiaXNzIjoiVG9rZW4gZG8gYXBwIiwiaWF0IjoxNzM2ODk5OTEwLCJleHAiOjE3MzY5MDA4MTB9.wpbpouS5TxCxpj_iDmL75VIlgNPwuTjmg3O26xezCpU",
									"type": "string"
								}
							]
						},
						"method": "GET",
						"header": [],
						"body": {
							"mode": "raw",
							"raw": "",
							"options": {
								"raw": {
									"language": "json"
								}
							}
						},
						"url": {
							"raw": "http://localhost:8080/restaurante/usuario/listar",
							"protocol": "http",
							"host": [
								"localhost"
							],
							"port": "8080",
							"path": [
								"restaurante",
								"usuario",
								"listar"
							]
						}
					},
					"response": []
				},
				{
					"name": "cadastrar usuario",
					"request": {
						"method": "POST",
						"header": [],
						"body": {
							"mode": "raw",
							"raw": "{\r\n  \"nome\": \"Marcelo Oliveira\",\r\n  \"email\": \"marceloliveira@gmail.com\",\r\n  \"login\": \"moliveira\",\r\n  \"senha\": \"M123\",\r\n  \"endereco\": \"Rua Exemplo, 123, Bairro Centro\",\r\n  \"tipoUsuario\": \"CLIENTE\"\r\n}\r\n",
							"options": {
								"raw": {
									"language": "json"
								}
							}
						},
						"url": {
							"raw": "http://localhost:8080/restaurante/usuario/cadastrar",
							"protocol": "http",
							"host": [
								"localhost"
							],
							"port": "8080",
							"path": [
								"restaurante",
								"usuario",
								"cadastrar"
							]
						}
					},
					"response": []
				},
				{
					"name": "buscar usuario por id",
					"request": {
						"method": "GET",
						"header": [],
						"url": {
							"raw": "http://localhost:8080/restaurante/usuario/1",
							"protocol": "http",
							"host": [
								"localhost"
							],
							"port": "8080",
							"path": [
								"restaurante",
								"usuario",
								"1"
							]
						}
					},
					"response": []
				},
				{
					"name": "deletar usuario por id",
					"request": {
						"method": "DELETE",
						"header": [],
						"url": {
							"raw": "http://localhost:8080/restaurante/usuario/1",
							"protocol": "http",
							"host": [
								"localhost"
							],
							"port": "8080",
							"path": [
								"restaurante",
								"usuario",
								"1"
							]
						}
					},
					"response": []
				},
				{
					"name": "atualiza usuário",
					"request": {
						"method": "PUT",
						"header": [],
						"body": {
							"mode": "raw",
							"raw": "{\r\n    \"nome\": \"João Silva de Castro\",\r\n    \"email\": \"joao.silva@example.com\",\r\n    \"login\": \"joaosilva\",\r\n    \"endereco\": \"Rua Exemplo, 123, Bairro Centro\",\r\n    \"tipoUsuario\": \"CLIENTE\"\r\n}",
							"options": {
								"raw": {
									"language": "json"
								}
							}
						},
						"url": {
							"raw": "http://localhost:8080/restaurante/usuario/1",
							"protocol": "http",
							"host": [
								"localhost"
							],
							"port": "8080",
							"path": [
								"restaurante",
								"usuario",
								"1"
							]
						}
					},
					"response": []
				}
			]
		}
	]
}restaurante-fiap.postman_collection.json…]()


## Informações adicionais 
Tirando os endpoints de cadastro de usuários , swagger e login os demais endpoints estão bloqueados sendo necessário passar o token válido gerado no login no Bearer Token para permitir o acesso.
Outra informação é que o docker compose está no caminho \restaurante\compose.yaml da aplicação









