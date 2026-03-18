# Qualiscapes

Protótipo de uma aplicação para consulta e visualização da classificação **QUALIS CAPES** de periódicos científicos por área de avaliação.

O objetivo do projeto é oferecer uma interface simples para:
- Consultar periódicos por área;
- Filtrar por classificação QUALIS;
- Buscar por título ou ISSN;
- Visualizar a distribuição das classificações encontradas.

## Tecnologias utilizadas

- **Java 21**
- **Spring Boot 4.0.3**
- **Spring MVC**
- **Spring Data JPA**
- **Thymeleaf**
- **Spring Security**
- **H2 Database**
- **Apache POI** para leitura de arquivos `.xlsx`
- **Bootstrap 5**
- **Tom Select**

## Funcionalidades

### Backend API

A aplicação expõe os seguintes endpoints:

- `GET /api/periodicos/areas`  
  Lista todas as áreas disponíveis.

- `GET /api/periodicos/tiers`  
  Lista as classificações QUALIS disponíveis.

- `GET /api/periodicos/busca`  
  Permite consultar periódicos com os seguintes parâmetros opcionais:
    - `area`
    - `tiers`
    - `search`

## Exemplos:

- `GET /api/periodicos/areas`
- `GET /api/periodicos/tiers`
- `GET /api/periodicos/busca?area=CIÊNCIA DA COMPUTAÇÃO`
- `GET /api/periodicos/busca?area=CIÊNCIA DA COMPUTAÇÃO&tiers=A1&tiers=B3`
- `GET /api/periodicos/busca?search=nature`

## Frontend

### A interface web permite:
- Selecionar a área de avaliação; 
- Selecionar uma ou mais classificações QUALIS;
- Buscar por título ou ISSN;
- Visualizar os resultados em tabela;
- Visualizar a distribuição por classificação em um painel lateral.

## Base de dados

Os dados são carregados automaticamente a partir do arquivo:
`src/main/resources/qualis_capes.xlsx`

A importação é realizada no startup da aplicação.

## Como executar
**Pré-requisitos**
- `Java 21`
- `Maven 3.9+`

## Passos:

**Clone o repositório:**
```
git clone https://github.com/Leonam-Miranda/qualiscapes.git
cd qualiscapes
```
Execute a aplicação:
`./mvnw spring-boot:run`

No Windows:
`mvnw.cmd spring-boot:run`

**A aplicação ficará disponível em:**
`http://localhost:8080`

## Banco H2

**O projeto usa banco em memória H2.**

Configuração atual:

- JDBC URL: `jdbc:h2:mem:qualisdb`
- User Name: `admin`
- Password: `admin123`

O console do H2 fica disponível em:
`http://localhost:8080/h2-console`

### Credenciais do H2 Console

- JDBC URL: `jdbc:h2:mem:qualisdb`
- User Name: `admin`
- Password: `admin123`
- 

### Autenticação

A aplicação possui autenticação configurada com usuário em memória:
- usuário: `admin`
- senha: `admin123`

**Atualmente:**
- A página inicial `"/"` é pública;
- Recursos estáticos são públicos;
- O `/h2-console/**` exige perfil ADMIN;
- Demais rotas exigem autenticação.


## Estrutura do projeto
```
src/main/java/io/github/qualiscapes
├── config
├── controller
├── exception
├── model
├── repository
└── service
```
### Principais componentes:

- `WebController:`
Responsável pela renderização da interface principal.

- `PeriodicoRestController`
Expõe a API REST para consulta dos periódicos.

- `PeriodicoService`
Centraliza a lógica de busca e de distribuição por classificação.

- `ExcelDataLoaderService`
Faz a leitura da planilha .xlsx e popula o banco H2 na inicialização.

## Decisões Tecnicas

- H2 em memória foi utilizado para simplificar a execução local e evitar dependências externas.
- Apache POI foi escolhido para ler diretamente a base QUALIS em formato .xlsx.
- Thymeleaf foi utilizado para montar uma interface simples, integrada ao backend Spring.
- A busca foi centralizada em uma camada de serviço para manter consistência entre a UI e a API REST.
- A distribuição por classificação foi implementada no backend e exibida na interface como resumo visual.

## Tratamento de erros:

A aplicação possui tratamento global para erros relacionados à importação da planilha e para falhas inesperadas, redirecionando o usuário para a página inicial com uma mensagem amigável.

## Autor
Leonam Miranda de Oliveira