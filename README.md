# Controle de orçamento familiar API
Api para ser usado para controle de orçamento familiar

## Descrição
Uma API RESTful contruída em Java para o controle de orçamento familiar, onde permite adicionar a receita do mes e as despesas e listar as depesas do mes e a receita pro mês inteiro facilitando o controle e a organização da renda mensal.

## Índice
1. [Técnologias](#tecnologias)
2. [Instalação](#instalação)
3. [Uso](#uso)
4. [Testes](#testes)
5. [Contribuição](#contribuição)

## Tecnologias
- Java 17
- Mysql
- Spring framework
- Spring Boot
- Flyway
- SpringSecurity
- Lombok
- SpringDoc
- Validation
- nginx

## Instalação
### Pré-requisitos
- Java 17+
- maven
- docker (opcional)
- docker-compose (opcional)
- mysql (opcional se não tiver docker instalado)

### Passos
1. Clone o repositório:
   ```bash
    git clone https://github.com/Vitor-C-Souza/controle-de-orcamento-familiar-api.git
    cd aluraflix-api
    ```

## Uso
Para iniciar o servidor execute o seguinte na raiz do projeto
1. Se tiver docker instalado:
    ```bash
    docker-compose up
    ```
2. Se não tiver docker instalado:
   ```bash
    mvn package
    java -jar target/*.jar
   ```

## Testes

Se acessar a rota localmente no seu navegador tera uma explicação de como funciona as rotas da api mais detalhadamente com springdoc.</br>
Rota localmente: `http://localhost:8080/swagger-ui/index.html#/`

## Contribuição

Contribuições são bem-vindas! Siga os passos abaixo:

1. Fork o repositório.
2. Crie uma nova branch (git checkout -b feature/nova-feature).
3. Commit suas alterações (git commit -m 'Adiciona nova feature').
4. Push para a branch (git push origin feature/nova-feature).
5. Abra um Pull Request.
