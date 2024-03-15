# Skill Scan (Diagnóstico de Conhecimento)
1. [Sobre o projeto](#skill-scan)
2. [Arquitetura](#arquitetura)
3. [Testes](#testes)
4. [Setup](#setup)
5. [Documentação da API](#api)
    - [User Endpoints](#user-endpoints)
        - [Criar Usuário](#criar-usuario)
        - [Obter Todos os Usuários](#obter-todos-os-usuarios)
        - [Deletar Usuário](#deletar-usuario)
        - [Obter Usuário por ID](#obter-usuario-por-id)
        - [Atualizar Usuário por ID](#atualizar-usuario-por-id)
    - [Course Endpoints](#course-endpoints)
        - [Criar Curso](#criar-curso)
        - [Obter Todos os Cursos](#obter-todos-os-cursos)
        - [Deletar Curso](#deletar-curso)
        - [Obter Curso por ID](#obter-curso-por-id)
        - [Atualizar Curso por ID](#atualizar-curso-por-id)
    - [Technology Endpoints](#technology-endpoints)
        - [Criar Tecnologia](#criar-tecnologia)
        - [Obter Todas as Tecnologias](#obter-todas-as-tecnologias)
        - [Deletar Tecnologia](#deletar-tecnologia)
        - [Obter Tecnologia por ID](#obter-tecnologia-por-id)
        - [Atualizar Tecnologia por ID](#atualizar-tecnologia-por-id)
    - [Question Endpoints](#question-endpoints)
        - [Criar Questão](#criar-questao)
        - [Obter Todas as Questões](#obter-todas-as-questoes)
        - [Deletar Questão](#deletar-questao)
        - [Obter Questão por ID](#obter-questao-por-id)
        - [Atualizar Questão por ID](#atualizar-questao-por-id)
    - [Random Question Endpoints](#random-question-endpoints)
        - [Obter Questão Aleatória por Tecnologia](#obter-questao-aleatoria-por-tecnologia)
    - [Quiz Attempt Endpoints](#quiz-attempt-endpoints)
        - [Criar Tentativa de Quiz](#criar-tentativa-de-quiz)
        - [Obter Todas as Tentativas de Quiz](#obter-todas-as-tentativas-de-quiz)
        - [Obter Tentativa de Quiz por ID](#obter-tentativa-de-quiz-por-id)
    - [Get User Test Results Endpoints](#get-user-test-results-endpoints)
    - [Login Endpoints](#login-endpoints)
        - [Login](#login)
    - [ChartData Endpoints](#chart-data-endpoints)
        - [Get Chart Data](#get-chart-data)

---

## <a name="skill-scan"></a>Skill Scan(Diagnóstico de Conhecimento)
O Skill Scan (Diagnóstico de Conhecimento) é um micro-serviço projetado para avaliar o nível de conhecimento em uma determinada tecnologia de novos colaboradores. Seu objetivo central é proporcionar uma análise precisa e eficaz das habilidades técnicas dos profissionais.

No Skill Scan, cada colaborador dispõe de uma aba personalizada, na qual pode selecionar a tecnologia desejada e responder a 10 questões de múltipla escolha. Após a submissão, o sistema fornecerá uma pontuação. Em caso de erro em alguma questão, serão sugeridos cursos relacionados para aprimorar o conhecimento do colaborador.

No âmbito da gestão, esses testes funcionam como parâmetros valiosos para acompanhar o progresso individual dos colaboradores. Ao analisar as pontuações ao longo do tempo, os gestores podem observar melhorias graduais e identificar áreas de desenvolvimento. Além disso, os resultados podem ser utilizados para criar insights, permitindo a elaboração de planos de carreira personalizados com base no perfil de cada colaborador. Essa abordagem estratégica facilita a promoção do desenvolvimento profissional e a otimização do desempenho da equipe.


---

## <a name="arquitetura"></a>Arquitetura
A arquitetura empregada neste projeto adota a Clean Architecture, combinada com a abordagem Maven Multimodule. Essa sinergia entre as duas arquiteturas traz diversas vantagens significativas para o desenvolvimento do sistema. Aqui estão algumas razões para a escolha dessa combinação:

- **Separação de Responsabilidades:**
    A Clean Architecture preconiza uma divisão clara e hierárquica das camadas do sistema, como Entidades, Casos de Uso, Adaptadores e Frameworks. Isso contribui para a manutenção de um código mais organizado e de fácil compreensão.
    O Maven Multimodule complementa essa abordagem ao permitir a criação de módulos independentes, cada um responsável por uma parte específica do sistema. Isso facilita a gestão de dependências e promove uma melhor separação de responsabilidades entre os diferentes componentes do projeto.


- **Reusabilidade de Código:**
    Com a utilização de módulos no Maven, é possível criar componentes reutilizáveis que podem ser compartilhados entre diferentes partes do sistema ou até mesmo em projetos futuros.
    A Clean Architecture favorece a criação de componentes independentes, tornando mais fácil isolar e reutilizar lógicas de negócios específicas em diferentes contextos da aplicação.


- **Testabilidade Aprimorada:**
    A Clean Architecture promove a testabilidade ao isolar a lógica de negócios em camadas independentes, facilitando a execução de testes unitários e a aplicação de práticas como TDD (Test-Driven Development).
    A estrutura modular do Maven possibilita a execução de testes de forma granular, focando em módulos específicos, o que é fundamental para garantir a integridade e confiabilidade do sistema como um todo.


- **Escalabilidade:**
    A arquitetura limpa e os módulos do Maven proporcionam uma base sólida para a escalabilidade do projeto. À medida que a aplicação cresce, novos módulos podem ser adicionados ou existentes podem ser expandidos sem comprometer a estrutura global do sistema.
    A divisão clara de responsabilidades facilita a adição de novos recursos, evolução da aplicação e manutenção sustentável ao longo do tempo.

#### Estrutrura base do projeto
ROOT  
  ├ __api|web__ (API ou Aplicação Web)  
  │   pom.xml  
  ├ modules  
  │   ├ __application__ (Casos de uso / Serviços de Aplicação)  
  │   │   pom.xml  
  │   ├ __domain__ (Model's / Repositórios e Serviços associados aos Models)  
  │   │   pom.xml  
  │   └ __infrastructure__ (Implementações utilizando dependências externas)  
  │       pom.xml  
  └ pom.xml

#### API
Projeto web com os pontos de interação com o usuário, irá conter:
- `Endpoint's` de entrada
- `Configuration` para injetar as implementações das dependências
- `Authentication`, configurações de autenticação

#### Aplication
Projeto contendo:
- `UseCases` da aplicação (Interface e Implementação)
- `Services` da aplicação, que não estão diretamento ligados aos `models`
- `Adapters` para o Domain

#### Domain
Projeto contendo:
- `Models`
- `Repositories`, definição de interfaces

#### Infrastructure
Projeto contendo as implementações:
- `Repository` de _domain_
- `Service` de _domain_ e _aplication_ que possuem dependência de implementação específica


Link: https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html

---

## <a name="testes"></a>Testes
Para os testes neste projeto, será adotado o padrão AAA(Triple A), que representa Arrange, Act, e Assert. Esse padrão é uma convenção estrutural comum em testes unitários e tem como objetivo fornecer uma estrutura clara e consistente para a organização dos testes. Aqui está o que cada parte do padrão AAA significa:

- **Arrange (Organizar):**
    Nesta fase, são preparados todos os dados e contextos necessários para a execução do teste. Isso inclui a criação de objetos, a configuração de estados iniciais e a definição de quaisquer condições prévias necessárias para a operação que será testada.


- **Act (Agir):**
    Durante esta etapa, a operação ou função que está sendo testada é executada. Isso envolve a interação com o sistema ou a chamada de métodos específicos que estão sendo avaliados. O objetivo é observar o comportamento do sistema sob condições controladas.


- **Assert (Verificar):**
    Na fase de Assert, são feitas as verificações para confirmar se o comportamento do sistema está de acordo com o esperado. São utilizadas as asserções (assertions) para comparar o resultado real da operação com o resultado esperado. Se as condições definidas não forem atendidas, o teste é considerado falho.

Link: https://java-design-patterns.com/patterns/arrange-act-assert/

---

## <a name="setup"></a>Setup
- **Instalando Java 21 LTS:**
    https://www.oracle.com/br/java/technologies/downloads/#java21


- **Instalando o Docker:**
    Certifique-se de ter o Docker instalado no seu sistema. Você pode baixá-lo aqui(https://www.docker.com/products/docker-desktop/).
    
    No terminal, navegue até o diretório onde está o arquivo Dockerfile e execute o seguinte comando para construir a imagem:
    ```docker build -t my-mongodb-image .```


- **Subindo o MongoDB com Docker:**
    Execute o seguinte comando para iniciar um contêiner usando a imagem do MongoDB que acabou de ser criada:
    ```docker run -d -p 27017:27017 --name my-mongodb-container my-mongodb-image```

    **Entendendo o que o comando acima faz:**
    
    *-d: Executa o contêiner em segundo plano.*

    *-p 27017:27017: Mapeia a porta 27017 do host para a porta 27017 do contêiner.*

    *--name my-mongodb-container: Define um nome para o contêiner.*


- **Gerenciando o Contêiner MongoDB:**

    Para iniciar o contêiner (caso esteja parado), execute:
    ```docker start my-mongodb-container```

    Para pausar o contêiner, execute:
    ```docker pause my-mongodb-container```

    Para retomar a execução do contêiner após pausá-lo, execute:
    ```docker unpause my-mongodb-container```


- **Instalando o MongoDB Compass:**
    Para visualizar seu banco de dados, você tem duas opções: usar o Mongosh para consultas via terminal ou, se preferir, 
    optar pelo Mongo Compass. O Mongo Compass oferece uma interface gráfica amigável que permite interagir com o banco de dados de
    forma intuitiva e fácil de usar. Você pode baixá-lo aqui(https://www.mongodb.com/try/download/compass).

---

## <a name="api"></a>Documentação da API

Você pode acessar a documentação da API em: http://localhost:8888/swagger-ui.html quando o projeto estiver em execução.

### <a name="user-endpoints"></a>User Endpoints

#### <a name="criar-usuario"></a>`POST /api/v1/user/create`
**Descrição:** Cria um novo usuário.

**Request Body:**
```json
{
    "username": "string",
    "password": "string",
    "role": "ENUM"
}
```

#### <a name="obter-todos-os-usuarios"></a>`GET /api/v1/user/get-all`
**Descrição:** Retorna uma lista de todos os usuários.

#### <a name="deletar-usuario"></a>`DELETE /api/v1/user/{id}`
**Descrição:** Deleta um usuário com o ID especificado.  Parâmetros:  
- *id (UUID): O ID do usuário a ser deletado.*

#### <a name="obter-usuario-por-id"></a>`GET /api/v1/user/{id}`
**Descrição:** Retorna um usuário com o ID especificado.  Parâmetros:
- *id (UUID): O ID do usuário a ser retornado.*

#### <a name="atualizar-usuario-por-id"></a>`PUT /api/v1/user/{id}`
**Descrição:** Atualiza um usuário com o ID especificado.  Parâmetros:  
- *id (UUID): O ID do usuário a ser atualizado.*

**Request Body:**
```json
{
    "username": "string",
    "password": "string",
    "role": "ENUM"
}
```

### <a name="course-endpoints"></a>Course Endpoints

#### <a name="criar-curso"></a>`POST /api/v1/course/create`
**Descrição:** Cria um novo curso.

**Request Body:**
```json
{
    "technologyId": "UUID",
    "name": "string",
    "content": "string",
    "keywords": ["string"]
}
```

#### <a name="obter-todos-os-cursos"></a>`GET /api/v1/course/get-all`
**Descrição:** Retorna uma lista de todos os cursos.

#### <a name="deletar-curso"></a>`DELETE /api/v1/course/{id}`
**Descrição:** Deleta um curso com o ID especificado.  Parâmetros:
- *id (UUID): O ID do curso a ser deletado.*

#### <a name="obter-curso-por-id"></a>`GET /api/v1/course/{id}`
**Descrição:** Retorna um curso com o ID especificado.  Parâmetros:
- *id (UUID): O ID do curso a ser retornado.*

#### <a name="atualizar-curso-por-id"></a>`PUT /api/v1/course/{id}`
**Descrição:** Atualiza um curso com o ID especificado.  Parâmetros:
- *id (UUID): O ID do curso a ser atualizado.*

**Request Body:**
```json
{
    "technologyId": "UUID",
    "name": "string",
    "content": "string",
    "keywords": ["string"]
}
```

### <a name="technology-endpoints"></a>Technology Endpoints

#### <a name="criar-tecnologia"></a>`POST /api/v1/technology/create`
**Descrição:** Cria uma nova tecnologia.

**Request Body:**
```json
{
    "name": "string"
}
```

#### <a name="obter-todas-as-tecnologias"></a>`GET /api/v1/technology/get-all`
**Descrição:** Retorna uma lista de todas as tecnologias.
    
#### <a name="deletar-tecnologia"></a>`DELETE /api/v1/technology/{id}`
**Descrição:** Deleta uma tecnologia com o ID especificado.  Parâmetros:
- *id (UUID): O ID da tecnologia a ser deletada.*

#### <a name="obter-tecnologia-por-id"></a>`GET /api/v1/technology/{id}`
**Descrição:** Retorna uma tecnologia com o ID especificado.  Parâmetros:
- *id (UUID): O ID da tecnologia a ser retornada.*
    
#### <a name="atualizar-tecnologia-por-id"></a>`PUT /api/v1/technology/{id}`
**Descrição:** Atualiza uma tecnologia com o ID especificado.  Parâmetros:
- *id (UUID): O ID da tecnologia a ser atualizada.*

**Request Body:**
```json
{
    "name": "string"
}
```

### <a name="question-endpoints"></a>Question Endpoints

#### <a name="criar-questao"></a>`POST /api/v1/question/create`
**Descrição:** Cria uma nova questão.

**Request Body:**
```json
{
    "questionText": "string",
    "answers": ["string"],
    "difficulty": "ENUM",
    "technologyId": "UUID"
}
```

#### <a name="obter-todas-as-questoes"></a>`GET /api/v1/question/get-all`
**Descrição:** Retorna uma lista de todas as questões.

#### <a name="deletar-questao"></a>`DELETE /api/v1/question/{id}`   
**Descrição:** Deleta uma questão com o ID especificado.  Parâmetros:
- *id (UUID): O ID da questão a ser deletada.*

#### <a name="obter-questao-por-id"></a>`GET /api/v1/question/{id}`
**Descrição:** Retorna uma questão com o ID especificado.  Parâmetros:
- *id (UUID): O ID da questão a ser retornada.*

#### <a name="atualizar-questao-por-id"></a>`PUT /api/v1/question/{id}`
**Descrição:** Atualiza uma questão com o ID especificado.  Parâmetros:
- *id (UUID): O ID da questão a ser atualizada.*

**Request Body:**
```json
{
    "questionText": "string",
    "answers": ["string"],
    "difficulty": "ENUM",
    "technologyId": "UUID"
}
```

### <a name="random-question-endpoints"></a>Random Question Endpoints

#### <a name="obter-questao-aleatoria-por-tecnologia"></a>`GET /api/v1/random-question/{technologyId}`
**Descrição:** Retorna uma questão aleatória com a tecnologia especificada.  Parâmetros:
- *technologyId (UUID): O ID da tecnologia da qual a questão será retornada.*


### <a name="quiz-attempt-endpoints"></a>Quiz Attempt Endpoints

#### <a name="criar-tentativa-de-quiz"></a>`POST /api/v1/quiz-attempt/create`
**Descrição:** Cria uma nova tentativa de quiz.

**Request Body:**
```json
{
    "userId": "UUID",
    "technologyId": "UUID",
    "questionResponses": ["QuestionResponse"]
}
```

#### <a name="obter-todas-as-tentativas-de-quiz"></a>`GET /api/v1/quiz-attempt/get-all`
**Descrição:** Retorna uma lista de todas as tentativas de quiz.
    
#### <a name="obter-tentativa-de-quiz-por-id"></a>`GET /api/v1/quiz-attempt/{id}`
**Descrição:** Retorna uma tentativa de quiz com o ID especificado.  Parâmetros:
- *id (UUID): O ID da tentativa de quiz a ser retornada.*


### <a name="get-user-test-results-endpoints"></a>Get User Test Results Endpoints

#### <a name="get-all-user-test-results"></a>`GET /api/v1/user-test-results/get-all`
**Description:** Retorna uma lista de todos os resultados de teste dos usuários.

### <a name="login-endpoints"></a>Login Endpoints


#### <a name="login"></a>`POST /api/v1/auth/login`
**Descrição:** Cria uma tentativa de de login.

**Request Body:**
```json
{
    "username": "String",
    "password": "String"
}
```

### <a name="chart-data-endpoints"></a>ChartData Endpoints

#### <a name="get-chart-data"></a>`GET /api/v1/chart-data/{id}`
**Descrição:** Retorna os dados do gráfico para o usuário com o ID especificado.
- *id (UUID): O ID do usuário a ser retornado.*

```json
{
  "userId": "UUID",
  "technologyName": "String",
  "score": "Integer",
  "testDate": "LocalDateTime"
}
```