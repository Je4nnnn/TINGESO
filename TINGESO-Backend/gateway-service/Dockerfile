FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN sed -i 's/\r$//' gradlew && chmod +x gradlew
RUN ./gradlew clean bootJar -x test --no-daemon

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
