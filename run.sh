#!/bin/bash

# Navega para o diretório do script para garantir que os caminhos estejam corretos
cd "$(dirname "$0")"

# Compila o projeto com o Maven Wrapper
echo "Limpando e compilando o projeto com Maven..."
./mvnw clean package

# Verifica se a compilação foi bem-sucedida
if [ $? -eq 0 ]; then
    echo "Compilação concluída com sucesso."
    # Executa o arquivo JAR gerado
    echo "Executando a aplicação..."
    java -jar target/betmaster-1.0.0.jar
else
    echo "Erro durante a compilação do Maven. A execução foi abortada."
    exit 1
fi
