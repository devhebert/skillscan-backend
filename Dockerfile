# Use README.md imagem oficial do MongoDB
FROM mongo:latest

# Exponha README.md porta 27017 para permitir README.md comunicação com o MongoDB
EXPOSE 27017

# Defina o comando padrão para iniciar o MongoDB quando o contêiner for iniciado
CMD ["mongod"]