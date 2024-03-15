## Orientações para padronização de testes unitários:

### Estrutura do teste: Seguimos o padrão AAA (Arrange, Act, Assert) para estruturar nossos testes.

     Isso torna nossos testes previsíveis e fáceis de entender.
    - Arrange: Configuramos o cenário do teste, definindo os dados de entrada e configurando os comportamentos dos mocks.
    - Act: Executamos a ação que desejamos testar.
    - Assert: Verificamos se o resultado da ação está correto.

### Nomes de métodos de teste:
    - Os nomes dos nossos métodos de teste são descritivos e indicam claramente o que cada teste está verificando.

### Uso de mocks:
    - Usamos mocks para isolar o código que está sendo testado. Isso nos permite controlar o comportamento das dependências e nos concentrar no código que está sendo testado.

### Asserts: 
     - Usamos asserts para verificar se o comportamento do código está correto. Isso é essencial em qualquer teste unitário.
