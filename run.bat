@echo off
REM Remove todos os arquivos da pasta bin
if exist bin (
    del /q bin\*
) else (
    mkdir bin
)

REM Garante que a pasta bin existe
if not exist bin (
    mkdir bin
)

REM Compila todos os arquivos .java em src para bin
for /r src %%f in (*.java) do (
    javac -d bin -sourcepath src "%%f"
    if errorlevel 1 (
        echo Erro de compilacao. Verifique os erros acima.
        exit /b 1
    )
)

REM Executa o programa principal
java -cp bin com.example.Main
