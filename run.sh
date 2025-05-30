#!/bin/bash
rm -rf ./bin/* 
mkdir -p ./bin 

# Encontra todos os arquivos .java em src/ e os compila para o diretório bin
find src -name "*.java" -print0 | xargs -0 javac -d bin -sourcepath src

# Verifica se a compilação foi bem-sucedida antes de executar
if [ $? -eq 0 ]; then
    java -cp bin com.example.Main
else
    echo "Erro de compilação. Verifique os erros acima."
fi
