# ⚙️ Teste Prático Iniflex

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)
[![H2 Database](https://img.shields.io/badge/H2-Database-blue.svg)](https://www.h2database.com/html/main.html)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Sobre o Projeto

O **Teste Prático Iniflex** é uma aplicação Spring Boot desenvolvida como parte de um desafio técnico para gerenciar uma lista de funcionários. A aplicação utiliza o banco de dados em memória H2 para persistir os dados dos funcionários e implementa uma série de operações de negócio, como remoção de funcionários, aumento salarial, e relatórios de dados.

### 🎯 Principais Funcionalidades

- ✅ **API REST** com um endpoint para executar todas as operações do teste.
- ✅ **Inicialização automática** com 10 funcionários iniciais.
- ✅ **Persistência de dados** utilizando Spring Data JPA e H2 Database.
- ✅ **Remoção de funcionário** por nome.
- ✅ **Aumento de salários** de 10% para todos os funcionários.
- ✅ **Agrupamento de funcionários** por função.
- ✅ **Relatórios detalhados** sobre funcionários (aniversariantes, mais velho, total de salários, salários em mínimos, etc.).
- ✅ **Arquitetura em camadas** (Controller, Service, Repository) seguindo as melhores práticas.

### 🏗️ Arquitetura

A aplicação segue uma arquitetura em camadas simples, utilizando **Dependency Injection** para gerenciar as dependências entre os componentes.


### 📁 Estrutura do Projeto

A estrutura de diretórios do projeto segue o padrão Maven e as convenções do Spring Boot.
