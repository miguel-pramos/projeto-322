#!/bin/bash

# SmartShelf - Swing Desktop Application
# Script para compilar e executar a aplicação

set -e # Sair em caso de erro

echo "=== SmartShelf - Gerenciador de Livros ==="
echo "📚 Aplicação desktop Swing com FlatLaf"
echo ""

# Verificar se Java está instalado
if ! command -v java &>/dev/null; then
    echo "❌ Java não encontrado. Por favor, instale Java 21 ou superior."
    exit 1
fi

# Verificar se Maven está instalado
if ! command -v mvn &>/dev/null; then
    echo "❌ Maven não encontrado. Por favor, instale Maven."
    exit 1
fi

# Verificar versão do Java
JAVA_VERSION=$(java -version 2>&1 | grep -oP 'version "([0-9]+)' | grep -oP '([0-9]+)$')
if [ "$JAVA_VERSION" -lt 21 ]; then
    echo "⚠️  Aviso: Java $JAVA_VERSION detectado. Recomenda-se Java 21 ou superior."
fi

echo "🔧 Compilando o projeto..."
mvn clean compile -q

if [ $? -eq 0 ]; then
    echo "✅ Compilação concluída com sucesso!"
    echo ""
    echo "🚀 Iniciando SmartShelf..."
    echo "💡 Dica: Use o menu 'Temas' para alternar entre diferentes estilos visuais"
    echo ""

    # Executar a aplicação
    mvn exec:java -Dexec.mainClass="com.unicamp.smartshelf.FicticiousBet" -q
else
    echo "❌ Erro na compilação. Verifique os logs acima."
    exit 1
fi
