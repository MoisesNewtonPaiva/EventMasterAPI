# 📅 EventMaster API

API REST para gerenciamento de eventos, atividades e participantes, desenvolvida com foco em **Clean Architecture** e boas práticas de desenvolvimento Java.

## 🚀 Tecnologias e Ferramentas
* **Java 21** 
* **Spring Boot 3**
* **Spring Data JPA**
* **H2 Database** (Banco de dados em memória para fácil execução)
* **Bean Validation** (Validação de dados de entrada)
* **SpringDoc OpenAPI (Swagger)** (Documentação da API)

## 🛠️ Diferenciais deste Projeto
- **Tratamento de Erros Profissional:** Respostas HTTP customizadas para recursos não encontrados e erros de integridade de banco de dados.
- **Diferenciação de Camadas:** Uso de **DTOs** para evitar a exposição de entidades de banco de dados na camada de controle.
- **Lógica de Negócio Centralizada:** Validações de datas de atividades e regras de unicidade implementadas na camada de Service.
- **Relacionamentos Complexos:** Gerenciamento de relações 1:N (Evento/Atividade) e N:N (Atividade/Participante).

## 🏁 Como Rodar o Projeto
1. Clone o repositório.
2. Certifique-se de ter o Maven instalado.
3. Execute o comando: `mvn spring-boot:run`.
4. Acesse a documentação interativa em: `http://localhost:8081/swagger-ui/index.html`
