# ------------------------------------
# ETAPA 1: BUILD (Compilação do Projeto)
# ------------------------------------
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo pom.xml para baixar dependências antes de compilar o código
# Isso é crucial para o cache eficiente do Docker
COPY pom.xml .

# Baixa as dependências. Se o pom.xml não mudar, o Docker usa o cache.
RUN mvn dependency:go-offline

# Copia o código-fonte restante
COPY src ./src

# Empacota o projeto em um arquivo JAR executável
RUN mvn package -DskipTests

# ------------------------------------
# ETAPA 2: PRODUCTION (Criação da Imagem Final)
# ------------------------------------
# Usamos uma JRE (Java Runtime Environment) baseada em Alpine ou Debian Slim
# É menor e mais segura que a imagem de build completa
FROM eclipse-temurin:17-jre-focal

# Argumento para pegar o nome do JAR que foi criado na etapa 'build'
# O nome do JAR geralmente segue o padrão: <artifactId>-<version>.jar
ARG JAR_FILE=target/gerenciadorweb-0.0.1-SNAPSHOT.jar 
# **ATENÇÃO:** Ajuste o nome do JAR acima se for diferente no seu pom.xml

# Copia o JAR compilado da etapa 'build' para a nova imagem
COPY --from=build /app/${JAR_FILE} app.jar

# Expõe a porta que o Spring Boot usa (8080 por padrão)
EXPOSE 8080

# Comando para iniciar a aplicação
# Render.com e a maioria das nuvens esperam que a aplicação escute na porta que eles fornecem (via variável PORT),
# mas o Spring Boot geralmente precisa do comando explícito.
ENTRYPOINT ["java", "-jar", "app.jar"]

# Você pode usar este ENTRYPOINT alternativo se precisar injetar variáveis de ambiente como a porta
# ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]