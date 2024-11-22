# WindRose

## Integrantes:

Natan Junior Rodrigues Lopes   RM: 552626  Turma: 2TDSPA

Pedro Lucca Medeiros Miranda   RM: 553873  Turma: 2TDSPA

Pedro Moreira de Jesus         RM: 553912  Turma: 2TDSPA

# Sumário

1. [Integrantes](#integrantes)
2. [Apresentação do Projeto](#apresentação-do-projeto)
3. [Pré-requisitos](#pré-requisitos)
4. [Funcionalidades](#funcionalidades)
5. [Como Rodar o Projeto Local](#como-rodar-o-projeto-local)
6. [Diagramas](#diagramas)  
   6.1. [DER (Diagrama de Entidade-Relacionamento)](#der-diagrama-de-entidade-relacionamento)  
   6.2. [Modelo Lógico do Banco de Dados](#modelo-logico-do-banco-de-dados)
7. [Estrutura do Projeto](#estrutura-do-projeto)
8. [Link do Vídeo Pitch](#link-video-pitch)
9. [Link do Vídeo Software](#link-video-software)

## Apresentação do projeto

O Wind Rose é um aplicativo que permite o cadastro e acompanhamento de consumo de 
dispositivos eletrodomésticos com o intuito de conscientizar os seus usuários do uso 
apropriado da energia elétrica para reduzir a necessidade do uso de energia não sustentável 
que agride o meio ambiente

## Pré-requisitos

- Java 21+
- Maven
- Git
- Docker

## Funcionalidades

- Registrar e autenticar usuários.
- Associar, listar, atualizar e remover dispositivos de usuários.
- Gerenciar informações de dispositivos individualmente.
- Gerenciar Usuarios.
- Navegação dinâmica com HATEAOS
- Documentação com Swagger: http://localhost:8080/api/swagger-ui/index.html#/
- Deploy em nuvem com Azure: [acesse aqui](http://windrose-spring.brazilsouth.cloudapp.azure.com/api/swagger-ui/index.html#/)

## Como Rodar o Projeto Local

1. Clone este repositório:

    ```
      git clone https://github.com/usuario/gs-spring.git
      cd gs-spring
    ```

2. Compile e inicie o projeto:
   Utilizando o a IDEA de sua preferencia execute a main ou rode o sprint dentro do cmd

   ```
      mvn spring-boot:run
   ```

   Caso queira rodar a partir do docker execute o comandos:

   ```
      docker build -t windrose-app .
      docker run -p 8080:8080 windrose-app
   ```

3. Acesse a API em http://localhost:8080/api ou acesse o swagger a partir http://localhost:8080/api/swagger-ui/index.html#/.

## Diagramas

### DER (Diagrama de Entidade-Relacionamento)
![Class Diagram.png](docs%2FClass%20Diagram.png)

### Modelo Logico do Banco de Dados
![bd_logico.png](docs%2Fbd_logico.png)

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
            │  ├───auth
            │  ├───device
            │  ├───user
            │  └───userDevice
            ├──Errors
            └──Service
               ├──Mapper
               └──Middleware
  ```

## Link Video Pitch

https://www.youtube.com/watch?v=RJH1z7ax50o

## Link Video Software

https://youtu.be/sDJ-10UO8js