FROM openjdk:21

WORKDIR /app

COPY . /app

RUN ./mvnw -B clean package -DskipTests

EXPOSE 8777

CMD ["java", "-jar", "target/desafioApiProjetos-0.0.1-SNAPSHOT.jar"]