# Usando uma imagem base com Java 17
FROM eclipse-temurin:17-jdk-alpine

# Definindo diretório de trabalho dentro do container
WORKDIR /app

# Copiando e empacotando o projeto
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src/ src/

# Dando permissão ao wrapper do Maven
RUN chmod +x mvnw

# Compilando o projeto
RUN ./mvnw clean package -DskipTests

# Copiando o JAR gerado para dentro do container
COPY target/desafio-tecnico-amcom-0.0.1-SNAPSHOT.jar app.jar

# Expondo a porta do aplicativo
EXPOSE 8080

# Definindo variáveis de ambiente com valores padrão (podem ser sobrescritos)
ENV JAVA_OPTS=""
ENV SPRING_PROFILES_ACTIVE="local"
ENV DB_HOST="postgres"
ENV DB_PORT="5432"
ENV DB_NAME="amcomdb"
ENV DB_USERNAME="amcom_user"
ENV DB_PASSWORD="amcom_password"
ENV KAFKA_BOOTSTRAP_SERVERS="PLAINTEXT://kafka:19092"

# Comando para rodar a aplicação
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar --spring.profiles.active=$SPRING_PROFILES_ACTIVE"]
