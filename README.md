
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
- [Estratégia Aplicada](#estrategia)
- [Estratégia de Execução](#estrategia-execucao)
- [Por onde começar?](#inicio)
- [Como usar?](#como_usar)
- [Testes](#testes)
- [Swagger](#swagger)
- [Tecnologias utilizadas](#techs)
- [Melhorias Futuras](#melhorias)
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

O desafio pode ser acessado em: [Instruções do desafio](https://github.com/robertotics4/desafio-amcom/blob/main/docs/instrucoes.png)

## 🚀 Estratégia Aplicada <a name = "estrategia"></a>

Para atender os requisitos do desafio, foram adotadas as seguintes estratégias:

- **Kafka para comunicação assíncrona**: Utilizei **Apache Kafka** para comunicação entre produtores e consumidores externos, garantindo a escalabilidade do sistema.
- **Configuração para réplicas**: Deixei a estrutura preparada para suportar múltiplas réplicas nas configurações do Kafka.
- **Otimização de buscas e inserts**: Foram implementadas consultas e operações otimizadas no banco de dados para garantir um alto desempenho.
- **Uso de transações quando necessário**: Para manter a consistência dos dados, em algumas situações utilizei transações no banco de dados.
- **Pool de conexões com HikariCP**: Utilizei **HikariCP** para gerenciar o pool de conexões com o banco de dados, melhorando a performance e eficiência do sistema.

## 🚀 Estratégia de Execução <a name = "estrategia-execucao"></a>

A estratégia abordada para a execução do desafio foi focada na comunicação assíncrona com Kafka e na persistência de dados de pedidos. O fluxo de dados segue a sequência descrita abaixo:

- **Entrada de pedidos**:  
  Os pedidos entram inicialmente pelo tópico `amcom.external.available_order`, com a seguinte estrutura de mensagem:
  ```json
  {
    "externalId": "e15da8b8-5f07-4fbb-92f7-6902bcb54f1f",
    "products": [
      {
        "id": "3ed2d326-6588-4731-b2f1-7b49b8ccfe8a",
        "quantity": 1
      }
    ]
  }
  ```
  Ao consumir a mensagem do Kafka, o pedido passa por toda a validação e é criado no banco de dados. Deixei um seed pronto para que sejam criados alguns produtos de exemplo.

- **Gerenciamento do pedido**:  
  A aplicação oferece algumas rotas para gerenciar o estado dos pedidos, com ações como:
  - Concluir o pedido
  - Cancelar o pedido

- **Envio do pedido processado**:  
  Quando o pedido é concluído, ele é automaticamente enviado para o tópico `amcom.external.processed_order` para que o sistema externo B possa processar a informação.

Essa estratégia de uso do Kafka permite garantir uma comunicação eficiente e escalável entre os sistemas, além de permitir a persistência e manipulação dos dados dos pedidos de forma consistente.

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
- [HikariCP](https://github.com/brettwooldridge/HikariCP) - Pool de conexões com banco de dados

## 🔮 Melhorias Futuras <a name="melhorias"></a>

- **Implementação de autenticação/autorização com token JWT**: Melhorar a segurança do sistema garantindo que apenas usuários autenticados possam acessar os recursos da API.
- **Uso de Protobuf/Schemas para comunicação Kafka**: Melhorar a eficiência e compatibilidade da comunicação assíncrona, garantindo uma estrutura de dados mais compacta e padronizada.

## ✍️ Autores <a name = "autores"></a>

- [@robertotics4](https://github.com/robertotics4)

## 🎉 Agradecimentos <a name = "agradecimentos"></a>

Agradeço à equipe da AMcom pela oportunidade de participar deste desafio técnico. Foi uma excelente experiência!