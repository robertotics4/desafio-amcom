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
- [Rodando com Docker Compose](#docker_compose)
- [Kafdrop - Monitoramento do Kafka](#kafdrop)
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

## 🚀 Rodando com Docker Compose <a name="docker_compose"></a>

A aplicação pode ser executada utilizando **Docker Compose**, garantindo um ambiente padronizado com todas as dependências corretamente configuradas.

### Requisitos:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

### Passos para rodar a aplicação:

1. Certifique-se de que o Docker e o Docker Compose estão instalados em seu ambiente.
2. Navegue até o diretório raiz do projeto.
3. Execute o seguinte comando para iniciar os containers:

```bash
 docker-compose up --build
```

Isso irá:
- Criar e iniciar os containers do PostgreSQL, Kafka e da aplicação.
- Compilar a aplicação e rodá-la dentro do container.
- Expor as portas necessárias para que a aplicação possa ser acessada.

### Acessando a aplicação:
Após iniciar os containers, a API estará disponível no seguinte endereço:

```
http://localhost:8080
```

Para verificar os logs da aplicação, utilize:
```bash
 docker-compose logs -f
```

Para parar e remover os containers:
```bash
 docker-compose down
```

## 📊 Kafdrop - Monitoramento do Kafka <a name = "kafdrop"></a>

O **Kafdrop** é uma ferramenta de UI para monitorar tópicos, mensagens e consumidores do Kafka. Ele está incluído no `docker-compose.yml` para facilitar o monitoramento do Kafka na aplicação.

### Acessando o Kafdrop

Após iniciar os containers com `docker-compose up`, o Kafdrop estará disponível no seguinte endereço:

```
http://localhost:19000
```

Com ele, você pode visualizar:
- Tópicos do Kafka
- Mensagens enviadas
- Status dos consumidores
- Partições e offsets

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
- [Kafdrop](https://github.com/obsidiandynamics/kafdrop) - UI para monitoramento do Kafka

## ✍️ Autores <a name = "autores"></a>

- [@robertotics4](https://github.com/robertotics4)

## 🎉 Agradecimentos <a name = "agradecimentos"></a>

Agradeço à equipe da AMcom pela oportunidade de participar deste desafio técnico. Foi uma excelente experiência!
