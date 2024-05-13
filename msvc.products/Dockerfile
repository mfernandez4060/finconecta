# dependency image
FROM openjdk:17-jdk-alpine as builder
WORKDIR /app/msvc.products

COPY ./pom.xml /app
COPY ./msvc.products/.mvn ./.mvn
COPY ./msvc.products/mvnw .
COPY ./msvc.products/pom.xml .

RUN ./mvnw dependency:go-offline 

COPY ./msvc.products/src ./src

RUN ./mvnw clean package -DskipTests

# Package image
FROM openjdk:17-jdk-alpine

WORKDIR /app

RUN mkdir ./logs

COPY --from=builder /app/msvc.products/target/msvc.products-0.0.1-SNAPSHOT.jar .

ARG PORT_APP=8002
ENV PORT $PORT_APP
EXPOSE $PORT

CMD [ "java", "-jar", "./msvc.products-0.0.1-SNAPSHOT.jar" ]
