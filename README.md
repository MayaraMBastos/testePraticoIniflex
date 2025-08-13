# ⚙️ Teste Prático Iniflex

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)
[![H2 Database](https://img.shields.io/badge/H2-Database-blue.svg)](https://www.h2database.com/html/main.html)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Sobre o Projeto

O **Teste Prático Iniflex** é uma aplicação Spring Boot desenvolvida para o desafio técnico.  
A aplicação gerencia uma lista de funcionários em um banco de dados em memória **H2**, executando diversas operações de negócio a partir de endpoints REST.

O projeto segue **arquitetura em camadas** (Controller, Service, Repository), utiliza **Spring Data JPA** e está pronto para ser executado localmente.

### 🎯 Principais Funcionalidades

- ✅ **API REST** com um endpoint que executa todas as operações do teste.
- ✅ **Inicialização automática** com 10 funcionários pré-cadastrados.
- ✅ **Persistência de dados** com H2 Database.
- ✅ **Remoção** de funcionários por nome.
- ✅ **Aumento automático** de salários em 10%.
- ✅ **Documentacao de Api** com Swagger.
- ✅ **Teste Unitários e de Integração** com JUnit e Mockito.
- ✅ **Agrupamento** de funcionários por função.
- ✅ **Relatórios detalhados** (aniversariantes, mais velho, total de salários, salários em salários mínimos, etc.).
- ✅ **Arquitetura organizada** seguindo boas práticas.

---

## 📁 Estrutura do Projeto

```
teste_pratico_iniflex/
├── pom.xml
├── README.md
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── maymb/teste_pratico_iniflex/
│   │   │       ├── TestePraticoIniflexApplication.java
│   │   │       ├── controller/FuncionarioController.java
│   │   │       ├── config/DadosIniciaisRunner.java
│   │   │       ├── dto/
│   │   │       │   ├── FuncionarioRequestDTO.java
│   │   │       │   └── FuncionarioResponseDTO.java
│   │   │       ├── entity/
│   │   │       │   ├── Funcionario.java
│   │   │       │   └── Pessoa.java
│   │   │       ├── repository/FuncionarioRepository.java
│   │   │       └── service/FuncionarioService.java
│   │   └── resources/application.properties
│   └── test/java/maymb/teste_pratico_iniflex/...
└── target/
```

---

## 🚀 Como Executar o Projeto

### Pré-requisitos

Certifique-se de ter instalado:

- **Java 21** ou superior
- **Maven 3.6+**
- **Git** (para clonar o repositório)

### Passo 1 - Clonando o Repositório

```bash
git clone https://github.com/MayaraMBastos/testePraticoIniflex
cd teste_pratico_iniflex
```

### Passo 2 - Opção 1: Executar com Maven

```bash
mvn spring-boot:run
```

### Opção 2: Gerar e Executar o JAR

```bash
# Gerar pacote
mvn clean package

# Executar
java -jar target/teste_pratico_iniflex-0.0.1-SNAPSHOT.jar
```
### Passo 3 - Executar com Swagger ou direto no navegador
```bash
#Executar com swagger
http://localhost:8080/swagger-ui.html

ou

#Executor pelo endpoint
http://localhost:8080/api/funcionarios/executar-teste

```
---

## 📚 Documentação da API

### Endpoints da API REST (`/api/funcionarios`)

A aplicação oferece endpoints RESTful para interagir com os dados de forma granular.

#### Documentação com Swagger
A documentação interativa da API, gerada automaticamente com o Springdoc OpenAPI, está disponível nos seguintes endpoints:

-   **Swagger UI**: `http://localhost:8080/swagger-ui.html`
-   **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

Com o **Swagger UI**, você pode visualizar todos os endpoints, ler suas descrições, e até mesmo testar as requisições diretamente do seu navegador.
### Endpoint de Teste Completo

Executa toda a lógica de negócio do desafio em uma única requisição.

- **Método:** GET
- **URL:** `http://localhost:8080/api/funcionarios/executar-teste`

#### Exemplo de requisição:

```bash
curl http://localhost:8080/api/funcionarios/executar-teste
```

#### Resposta (exemplo):
```
--- Lista de Funcionários ---
Nome: Maria, Data Nascimento: 18/10/2000, Salário: R$ 2.009,44, Função: Operador
...
--- Funcionário com Maior Idade ---
Nome: Caio, Idade: 64
...
```

---

### Endpoints da API REST (`/api/funcionarios`)

#### 1. Criar Funcionário(s)
- **Método:** POST
- **URL:** `http://localhost:8080/api/funcionarios`
- **Descrição:** Cria um novo funcionário.

**Corpo da requisição (JSON):**
```json
{
  "nome": "Fulano",
  "nascimento": "1991-03-15",
  "salario": 3500.00,
  "funcao": "Analista"
}
```
**Resposta:** `201 Created` + objeto criado.

---

#### 2. Listar Todos os Funcionários
- **Método:** GET
- **URL:** `http://localhost:8080/api/funcionarios`
- **Descrição:** Lista todos os funcionários.

```bash
curl http://localhost:8080/api/funcionarios
```
**Resposta:** `200 OK` + array JSON.

---

#### 3. Remover Funcionário
- **Método:** DELETE
- **URL:** `http://localhost:8080/api/funcionarios/{nome}`
- **Descrição:** Remove um funcionário pelo nome.

```bash
curl -X DELETE http://localhost:8080/api/funcionarios/João
```
**Resposta:** `204 No Content`.

---

#### 4. Dar Aumento de Salário
- **Método:** PUT
- **URL:** `http://localhost:8080/api/funcionarios/aumento-de-salario`
- **Descrição:** Aumenta em 10% o salário de todos os funcionários.

```bash
curl -X PUT http://localhost:8080/api/funcionarios/aumento-de-salario
```
**Resposta:** `200 OK` + lista atualizada.

---

#### 5. Agrupar por Função
- **Método:** GET
- **URL:** `http://localhost:8080/api/funcionarios/por-funcao`
- **Descrição:** Retorna funcionários agrupados por função.

**Resposta:** `200 OK` + objeto JSON.

---

#### 6. Obter Aniversariantes
- **Método:** GET
- **URL:** `http://localhost:8080/api/funcionarios/aniversariantes`
- **Descrição:** Retorna funcionários que fazem aniversário em outubro e dezembro.

**Resposta:** `200 OK` + array JSON.

---

#### 7. Obter Funcionário Mais Velho
- **Método:** GET
- **URL:** `http://localhost:8080/api/funcionarios/mais-velho`
- **Descrição:** Retorna o funcionário mais velho.

**Resposta:** `200 OK` + objeto JSON.

---

#### 8. Obter Total de Salários
- **Método:** GET
- **URL:** `http://localhost:8080/api/funcionarios/total-salarios`
- **Descrição:** Retorna a soma total dos salários.

**Resposta:** `200 OK` + valor numérico.

---

#### 9. Obter Salários em Mínimos
- **Método:** GET
- **URL:** `http://localhost:8080/api/funcionarios/salarios-minimos`
- **Descrição:** Retorna quantos salários mínimos cada funcionário recebe.

**Resposta:** `200 OK` + objeto JSON.

---

## 🧪 Testes

```bash
mvn test
```
