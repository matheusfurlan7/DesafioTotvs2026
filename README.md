# Desafio Totvs

API para gestão de Contas a Pagar desenvolvida com:

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Swagger (OpenAPI)
- Arquitetura baseada em DDD + Clean Architecture

---

# Como rodar o projeto localmente

## Pré-requisitos

Antes de começar, você precisa ter instalado:

- Java 21
- Maven 
- Docker

Verifique versões:

```bash
java -version
mvn -v
docker -v
```

# Rodando a aplicação

```bash
docker compose up -d
mvn spring-boot:run
```

Acessando a API

```bash
http://localhost:8080
```

# Documentação Swagger

```bash
http://localhost:8080/swagger-ui/index.html
```

---

## Endpoints da API

A API está disponível em `http://localhost:8080` e segue a estrutura abaixo:

### Autenticação

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/auth/login` | Autentica o usuário e retorna um token JWT. |

### Contas a Pagar
Gerenciamento completo do ciclo de vida das contas.


| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `GET` | `/v1/contas` | Lista contas com paginação e filtros (`descricao`, `dataInicio`, `dataFim`). |
| `POST` | `/v1/contas` | Cadastra uma nova conta. |
| `PATCH` | `/v1/contas/{id}/pagar` | Registra o pagamento de uma conta informando a data via query string. |
| `PATCH` | `/v1/contas/{id}/cancelar` | Cancela uma conta específica. |
| `GET` | `/v1/contas/relatorio` | Extrai relatório consolidado de contas. |

### Importação de Arquivos
Processamento assíncrono de arquivos de contas.


| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/v1/importacoes` | Faz o upload de um arquivo para processamento. Retorna um protocolo (UUID). |
| `GET` | `/v1/importacoes/{id}` | Consulta o status da importação e detalha linhas com sucesso ou erro. |

### Fornecedores

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `GET` | `/v1/fornecedores` | Lista todos os fornecedores cadastrados no sistema. |

---

## Exemplos de Uso

### Autenticando (`POST /auth/login`)
```json
{
  "username": "admin@totvs.com",
  "password": "123"
}
```

### Criar uma Conta (`POST /v1/contas`)
```json
{
  "descricao": "Pagamento Fornecedor de TI",
  "valor": 2500.50,
  "dataVencimento": "2023-12-30",
  "fornecedorId": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
}
```

### Formato do Arquivo de Importação (CSV)

O endpoint `POST /v1/importacoes` aceita arquivos CSV para processamento em lote. O arquivo deve seguir o cabeçalho e formato abaixo:

**Cabeçalho:** `descricao,datavencimento,valor,fornecedorId`

**Exemplo de conteúdo:**
```csv
descricao,datavencimento,valor,fornecedorId
Conta 1,2026-03-01,150.0,f8a55959-158a-40a2-949f-f58c741496a7
,2026-03-02,200.0,7e3c509a-4c28-48b0-8f9f-d007c6504a57
Conta 3,,300.0,f8a55959-158a-40a2-949f-f58c741496a7
Conta 4,2026-03-04,,7e3c509a-4c28-48b0-8f9f-d007c6504a57
Conta 5,2026-03-05,500.0,
Conta 6,2026-03-06,600.0,uuid-invalido
,,,
```

---

# Arquitetura e Decisões
O projeto foi estruturado utilizando princípios de Clean Architecture e DDD (Domain-Driven Design) para garantir testabilidade e baixo acoplamento:

  * domain: Núcleo da aplicação. Contém as entidades de negócio e interfaces (regras puras).
  * application: Camada de orquestração. Implementa os casos de uso (importação, fluxos de pagamento) e serviços.
  * presentation: Ponto de entrada da API. Contém os Controllers (REST) e Mappers de DTOs.
  * infrastructure: Detalhes técnicos. Implementações de persistência (JPA), segurança (JWT) e integrações externas.
  * specification: Utilizada para desacoplar a lógica de filtros dinâmicos e consultas complexas do banco de dados.

**Por que essa estrutura?**

Essa divisão permite que o sistema seja facilmente escalável e que mudanças na infraestrutura (ex: trocar o banco de dados) não afetem as regras de negócio.