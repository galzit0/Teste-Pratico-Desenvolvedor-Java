# Controle de Agendamentos

## Descrição

Este projeto é um **sistema de agendamento** desenvolvido em **Java** com **JSF (JavaServer Faces)** e **Spring Framework**. Ele permite que os usuários realizem agendamentos e consultas, incluindo a verificação da disponibilidade de vagas para um período específico e controle do limite máximo de agendamentos por solicitante.

## Funcionalidades

- **Cadastro de Agendamentos**: Possibilita o cadastro dos agendamentos, selecionando um solicitante e verificando a disponibilidade de vagas no período especificado.
- **Consulta de Agendamentos**: Possibilita a consulta dos agendamentos com base em um período de tempo e filtrando por solicitante.
- **Controle de Vagas**: Possibilita que o número de agendamentos não ultrapasse a quantidade de vagas disponíveis e que o limite máximo de 25% das vagas não seja ultrapassado para cada solicitante.
- **Cadastro de Vagas**: Possibilita o cadastro de vagas disponíveis para agendamento, definindo o período e a quantidade de vagas disponíveis.
- **Consulta do Total de Agendamentos por Solicitante**: Possibilita a consulta do total de agendamentos realizados por solicitante, juntamente com a quantidade de vagas e o percentual de utilização para um determinado período.
- **Cadastro de Solicitantes**: Possibilita cadastrar solicitantes que podem realizar agendamentos no sistema.

## Tecnologias Utilizadas

- **Java**: Linguagem de programação principal do projeto.
- **Spring Framework**: Usado para injeção de dependências e gerenciamento de ciclo de vida dos beans.
- **JSF (JavaServer Faces)**: Utilizado para a criação da interface de usuário.
- **PrimeFaces**: Biblioteca de componentes visuais para JSF.
- **JPA/Hibernate**: Usado para persistência de dados no banco de dados relacional.
- **HSQLBD Database**: Banco de dados em memória.

## Requisitos

- **Java 17**: Certifique-se de ter o Java JDK 17 instalado.
- **Maven**: Usado para gerenciar dependências e construir o projeto.
- **IDE**: IntelliJ, Eclipse ou qualquer outra IDE que suporte Maven.

## Instalação

1. Clone este repositório para sua máquina local:
   ```bash
   git clone https://github.com/galzit0/Teste-Pratico-Desenvolvedor-Java
   ```
2. Instale as dependências do projeto usando o Maven:
    ```bash
   mvn clean install
   ```
3. Execute o main ou rode o seguinte comando.
    ```
   java -jar -Dserver.port=9292 target/Teste-Pratico-Desenvolvedor-Java-0.0.2-SNAPSHOT.jar
   ```
4. Acesse a aplicação no seu navegador:
   ```
   http://localhost:9292/index.xhtml
   ```
## Estrutura do Projeto

- **src/main/java**: Contém os arquivos fonte da aplicação.
  - **bean**:  Contém os managed beans (JSF) para interação com a interface de usuário.
  - **model**: Contém as entidades que representam as tabelas do banco de dados.
  - **service**: Contém as regras de negócio e serviços.
  - **repository**: Contém as interfaces que estendem JpaRepository para comunicação com o banco de dados.
  - **utils**: Contém utilitários usados na aplicação.
- **src/main/resources**: Contém arquivos de configuração, como application.properties, e os templates JSF.