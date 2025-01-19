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
  git clone https://github.com/IgorCastro-dev/restaurante-fiap.git
```

Entre no diretório do projeto

```bash
  cd restaurante-fiap
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

[restaurante-fiap.postman_collection.json](https://github.com/user-attachments/files/18470160/restaurante-fiap.postman_collection.json)


## Informações adicionais 
Tirando os endpoints de cadastro de usuários , swagger e login os demais endpoints estão bloqueados sendo necessário passar o token válido gerado no login no Bearer Token para permitir o acesso.
Outra informação é que o docker compose está no caminho \restaurante\compose.yaml da aplicação









