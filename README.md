# Qualiscapes

Protótipo de uma aplicação para consulta e visualização da classificação **QUALIS CAPES** de periódicos científicos por área de avaliação.

## Objetivo

Disponibilizar uma interface simples e direta para:

- consultar periódicos por área de avaliação;
- filtrar por classificação QUALIS;
- buscar por título ou ISSN;
- visualizar a distribuição das classificações encontradas;
- navegar pelos resultados com paginação.

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
    - `area:` área de avaliação
    - `tiers:` classificações QUALIS
    - `search:` busca por título ou ISSN
    - `page:` número da página
    - `size:` quantidade de itens por página

## Exemplos:

- `GET /api/periodicos/areas`
- `GET /api/periodicos/tiers`
- `GET /api/periodicos/busca?area=CIÊNCIA DA COMPUTAÇÃO`
- `GET /api/periodicos/busca?area=CIÊNCIA DA COMPUTAÇÃO&tiers=A1&tiers=B3`
- `GET /api/periodicos/busca?search=nature&page=0&size=20`

## Frontend

### A interface web permite:
- Selecionar a área de avaliação; 
- Selecionar uma ou mais classificações QUALIS;
- Buscar por título ou ISSN;
- Visualizar os resultados em tabela paginada;
- Visualizar a distribuição por classificação em um painel lateral.

## Base de dados

Os dados são carregados automaticamente a partir do arquivo:
`src/main/resources/qualis_capes.xlsx`

A leitura da planilha é feita com Apache POI no startup da aplicação, e os registros são persistidos em banco H2 em memória.

## Como executar
### Pré-requisitos:
- `Java 21`
- `Maven 3.9+`

### Clone o repositório:
```
git clone https://github.com/Leonam-Miranda/qualiscapes.git
cd qualiscapes
```

### Execute a aplicação:

No Linux/macOS:
`./mvnw spring-boot:run`

No Windows:
`mvnw.cmd spring-boot:run`

**A aplicação ficará disponível em:**
`http://localhost:8080`

## Teste rápido

- Inicie a aplicação
- Acesse `http://localhost:8080`
- Selecione uma área de avaliação
- Opcionalmente selecione uma ou mais classificações
- Consulte os resultados
- Navegue pelas páginas e visualize a distribuição lateral

## Banco H2

O projeto usa banco em memória H2.

**Configuração atual:**

- JDBC URL: `jdbc:h2:mem:qualisdb`
- User Name: `admin`
- Password: `admin123`

O console do H2 fica disponível em:
`http://localhost:8080/h2-console`

### Credenciais do H2 Console

- JDBC URL: `jdbc:h2:mem:qualisdb`
- User Name: `admin`
- Password: `admin123`


### Autenticação

A aplicação possui autenticação configurada com usuário em memória:
- usuário: `admin`
- senha: `admin123`

### Aviso:
Essas credenciais são usadas apenas para desenvolvimento local. Em um ambiente real, senhas não devem ser versionadas no repositório e devem ser externalizadas via variáveis de ambiente ou outro mecanismo seguro de gerenciamento de segredos.


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

- `PeriodicoRepository`
Executa as consultas no banco de dados.

- `ExcelDataLoaderService`
Faz a leitura da planilha .xlsx e popula o banco H2 na inicialização.

- `GlobalExceptionHandler`
Faz o tratamento global de erros e redireciona o usuário com mensagens amigáveis.

## Decisões Técnicas

- Foi utilizado H2 em memória para simplificar a execução local e evitar dependência de banco externo.
- Foi utilizado Apache POI para importar diretamente a base em formato Excel.
- A busca foi centralizada em uma camada de serviço para manter consistência entre a interface web e a API REST.
- A paginação foi adicionada para evitar carregamento excessivo de registros e melhorar a performance da navegação.
- A interface foi construída com Thymeleaf + Bootstrap, priorizando simplicidade e clareza visual.
## Tratamento de erros:

A aplicação possui tratamento global para erros relacionados à importação da planilha e para falhas inesperadas, redirecionando o usuário para a página inicial com uma mensagem amigável.

## Observações
- O projeto foi pensado como um protótipo funcional, com foco em simplicidade de uso.
- A base é importada automaticamente a partir do arquivo Excel disponível em src/main/resources.
- A interface e a API compartilham a mesma lógica de busca, garantindo consistência entre backend e frontend.

## Autor
Leonam Miranda de Oliveira