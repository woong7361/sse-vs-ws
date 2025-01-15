# 1. OpenJDK 17을 베이스 이미지로 사용
FROM openjdk:17-jdk-alpine

# 2. 작업 디렉토리를 설정
WORKDIR /app

# 3. JAR 파일을 컨테이너로 복사
COPY build/libs/demo-0.0.1-SNAPSHOT.jar app.jar

# 4. 기본 포트 노출
EXPOSE 8080

# 5. 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]