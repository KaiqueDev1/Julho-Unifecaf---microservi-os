# Estágio 1: Build (Compila o código e gera o .jar)
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio 2: Run (Executa a aplicação de forma enxuta e segura)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copia o jar gerado no estágio anterior
COPY --from=build /app/target/*.jar app.jar

# Boas práticas de segurança: cria e usa um usuário não-root (sem privilégios administrativos)
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Altere a porta de acordo com o serviço (8081 para pedidos, 8082 para pagamentos, 8083 para estoque)
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]