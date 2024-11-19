# WindRose

-- deia

## Pré-requisitos

- Java 21+
- Maven

## Estrutura do Projeto
  ```
    src/main/java
     └── /com/fiap/br/globalSolution
         ├──Domain
         │  └──Model
         ├──Infra
         │  └──Repository
         └──Application
            ├──Config
            ├──Controller
            ├──Dto
            |  ├───auth
            |  ├───device
            |  ├───user
            |  └───userDevice
            ├──Errors
            └──Service
               ├──Mapper
               └──Middleware
  ```

## Funcionalidades 

- Registrar e autenticar usuários.
- Associar, listar, atualizar e remover dispositivos de usuários.
- Gerenciar informações de dispositivos individualmente.
- Gerenciar Usuarios.
- Navegação dinâmica com HATEAOS
- Documentação com Swagger: http://localhost:8080/api/swagger-ui/index.html#/

## Como Rodar o Projeto

1. Clone este repositório:

    ```
      git clone https://github.com/usuario/gs-spring.git
      cd gs-spring
    ```

2. Compile e inicie o projeto:
   Utilizando o a IDEA de sua preferencia execulte a main ou rode o sprint

   ```
      mvn spring-boot:run
   ```

3. Acesse a API em http://localhost:8080/api.

