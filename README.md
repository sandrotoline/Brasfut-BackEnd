[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)
## Brasfut

Projeto Brasfut é uma uma API REST criada usando Spring Boot e Hibernate. A autenticação/autorização é realizada pelo Spring Security e utiliza JWT, com o intuito de armanezar o consumo de água do usuário.

Há duas autorizações: ROLE_ADMIN e ROLE_USER.

- ROLE_USER:
	- inserir o consumo 
	- alterar meta diária
	- alterar a senha

- ROLE_ADMIN:
	- todas as permissões do user
	- inserir usuário
	- remover usuário
	- listar todos os usuários e suas respectivas metas diárias de consumo


## Banco de dados

Utilize o script MySQL contido no arquivo  **MySQL_Script.sql**  para criar e popular o Banco de Dados.

Usuários

Os seguintes usuários/senhas estão disponíveis para login:

-   admin/admin
-   teste/teste

Project setup

```
mvn install
```
