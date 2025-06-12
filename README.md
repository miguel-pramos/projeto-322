# SmartShelf - Gerenciador de Livros JavaFX

## Descrição

SmartShelf é uma aplicação desktop JavaFX para gerenciamento de livros pessoais. A aplicação permite cadastrar, editar, excluir e gerenciar o status de leitura de livros em uma interface gráfica intuitiva.

## Tecnologias Utilizadas

-   **JavaFX 21** - Interface gráfica
-   **SQLite** - Banco de dados local
-   **JDBC** - Acesso ao banco de dados
-   **Maven** - Gerenciamento de dependências

## Funcionalidades

-   ✅ Cadastro de livros com descrição
-   ✅ Marcar livros como lidos/não lidos
-   ✅ Edição de livros existentes
-   ✅ Exclusão de livros
-   ✅ Filtros por status (Todos, Lidos, Não lidos)
-   ✅ Busca por descrição
-   ✅ Estatísticas em tempo real
-   ✅ Interface responsiva e moderna

## Estrutura do Projeto

```
src/main/java/com/unicamp/smartshelf/
├── SmartshelfApplication.java          # Classe principal JavaFX
├── controller/
│   └── MainController.java             # Controlador da interface
├── service/
│   └── BookService.java                # Lógica de negócio
├── repository/
│   └── BookRepository.java             # Acesso aos dados
├── model/entity/
│   └── Book.java                       # Entidade Book
└── database/
    └── DatabaseManager.java            # Gerenciamento do SQLite

src/main/resources/
├── fxml/
│   └── main.fxml                       # Layout da interface
└── css/
    └── style.css                       # Estilos da aplicação
```

## Como Executar

### Pré-requisitos

-   Java 21 ou superior
-   Maven 3.6 ou superior

### Compilação e Execução

```bash
# Compilar o projeto
mvn clean compile

# Executar a aplicação
mvn javafx:run

# Ou usar o script de conveniência
./run.sh
```

## Banco de Dados

A aplicação utiliza SQLite como banco de dados local. O arquivo `db.sqlite` é criado automaticamente na raiz do projeto na primeira execução.

### Estrutura da Tabela

```sql
CREATE TABLE book (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    description TEXT NOT NULL,
    read BOOLEAN NOT NULL DEFAULT FALSE
);
```

## Interface da Aplicação

-   **Cabeçalho**: Título da aplicação
-   **Filtros**: Campo de busca e filtro por status
-   **Tabela**: Listagem dos livros com ID, descrição e status
-   **Formulário**: Área para adicionar/editar livros
-   **Botões de Ação**: Adicionar, Atualizar, Deletar, Marcar como Lido/Não Lido
-   **Estatísticas**: Total de livros, lidos e não lidos
-   **Status**: Mensagens de feedback das operações

## Histórico de Mudanças

-   **v2.0** - Migração para JavaFX puro, remoção do Spring Boot
-   **v1.0** - Aplicação web inicial com Spring Boot e Thymeleaf

## Desenvolvimento

Esta aplicação foi desenvolvida como projeto educacional para demonstrar:

-   Desenvolvimento de aplicações desktop com JavaFX
-   Integração com banco de dados SQLite usando JDBC
-   Arquitetura em camadas (Controller, Service, Repository)
-   Interface responsiva e moderna
-   Gerenciamento de estado da aplicação
