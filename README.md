<p align="center">
  <a href="" rel="noopener">
 <img src="https://amcom.com.br/wp-content/uploads/2023/10/MicrosoftTeams-image-116.png" alt="Logo da AMcom" style="width: 400px; height: auto;">
</p>

<h3 align="center">Desafio Técnico - AMcom</h3>

<div align="center">

[![Status](https://img.shields.io/badge/status-active-success.svg)]()
[![GitHub Pull Requests](https://img.shields.io/github/issues-pr/kylelobo/The-Documentation-Compendium.svg)](https://github.com/kylelobo/The-Documentation-Compendium/pulls)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](/LICENSE)

</div>

---

## 📝 Índice

- [Sobre a aplicação](#sobre)
- [Desafio](#desafio)
- [Por onde começar?](#inicio)
- [Como usar?](#como_usar)
- [Testes](#testes)
- [Swagger](#swagger)
- [Tecnologias utilizadas](#techs)
- [Autores](#autores)
- [Agradecimentos](#agradecimentos)

## 🧐 Sobre a aplicação <a name = "sobre"></a>

O desafio consiste no desenvolvimento de um serviço para gerenciamento e cálculo de pedidos. A aplicação foi construída utilizando **Java com Spring Boot**, seguindo boas práticas de desenvolvimento, incluindo **SOLID**, **separação de camadas**, e **boas práticas de organização de código**.

### Estrutura do Projeto

A aplicação foi organizada na seguinte estrutura de pastas:

- **src/main/java/com/amcom/desafiotecnicoamcom/domain**: contém o core da aplicação, como entidades de negócio, serviços e contratos.
- **src/main/java/com/amcom/desafiotecnicoamcom/infra**: contém a comunicação com serviços externos ao negócio, como bancos de dados e stream.
- **src/main/java/com/amcom/desafiotecnicoamcom/controller**: responsável pela exposição dos recursos da aplicação.
- **src/main/java/com/amcom/desafiotecnicoamcom/config**: contém todas as configurações do projeto

### Funcionalidades Implementadas

#### 📦 Pedidos

- Receber pedidos de um sistema externo A.
- Gerenciar e calcular o total dos pedidos.
- Disponibilizar os pedidos para o sistema externo B.
- Cada pedido contém:
    - id (autogerado)
    - lista de produtos (com quantidade de cada um)
    - total do pedido
    - status
- Validações ao criar um pedido:
    - Verificação de duplicação de pedidos.
    - Garantia de disponibilidade do serviço para alta volumetria.
    - Garantia de consistência dos dados e concorrência.
    - Análise de impacto no banco de dados.

## 📄 Conhecendo o desafio <a name = "desafio"></a>

O desafio pode ser acessado em: [Instruções do desafio](https://github.com/robertotics4/desafio-tecnico-amcom/blob/master/docs/instrucoes.png)

## 🏁 Por onde começar? <a name = "inicio"></a>

A aplicação foi desenvolvida com **Spring Boot** e utiliza um **banco de dados relacional**.

### Pré-requisitos

- Java 17+
- Maven
- Docker

### Instalando as dependências

Para instalar as dependências, execute:

```bash
mvn clean install
```

## 🎈 Como usar? <a name="como_usar"></a>

### Iniciar a API

```bash
mvn spring-boot:run
```

OBS: para iniciar a aplicação sem docker será necessário criar um arquivo `.env` com as seguintes variáveis de ambiente (exemplo):

```env
# Database Configuration
DB_HOST=localhost
DB_PORT=5432
DB_NAME=amcomdb
DB_USERNAME=amcom_user
DB_PASSWORD=amcom_password

# Kafka Configuration
KAFKA_BOOTSTRAP_SERVERS=PLAINTEXT://localhost:19092
```  

### Usando Docker

```bash
docker-compose up
```

## ✅ Testes <a name = "testes"></a>

Foram implementados **testes unitários** utilizando `JUnit` e `Mockito`.

```bash
mvn test
```

Para verificar a cobertura de código:

```bash
mvn jacoco:report
```

## 📚 Swagger <a name = "swagger"></a>

A documentação da API está disponível em:

```
http://localhost:[PORTA]/swagger-ui/index.html
```

Exemplo: http://localhost:8080/swagger-ui/index.html

## ⛏️ Tecnologias utilizadas <a name = "techs"></a>

- [Java](https://www.java.com/) - Linguagem de programação
- [Spring Boot](https://spring.io/projects/spring-boot) - Framework para aplicações Java
- [Swagger](https://swagger.io/) - Documentação da API
- [Docker](https://www.docker.com/) - Containerização
- [JUnit](https://junit.org/) - Testes unitários
- [Mockito](https://site.mockito.org/) - Mocking de dependências
- [JPA](https://jakarta.ee/specifications/persistence/) - Persistência de dados
- [PostgreSQL](https://www.postgresql.org/) - Banco de dados relacional
- [Kafka](https://kafka.apache.org/) - Mensageria para escalabilidade

## ✍️ Autores <a name = "autores"></a>

- [@robertotics4](https://github.com/robertotics4)

## 🎉 Agradecimentos <a name = "agradecimentos"></a>

Agradeço à equipe da AMcom pela oportunidade de participar deste desafio técnico. Foi uma excelente experiência!
