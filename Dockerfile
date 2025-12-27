FROM eclipse-temurin:21-jdk

WORKDIR /app

# 특정 파일명이 아닌 build/libs/ 내부의 jar 파일을 찾도록 수정
COPY build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
