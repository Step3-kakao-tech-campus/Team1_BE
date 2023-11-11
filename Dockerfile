FROM krmp-d2hub-idock.9rum.cc/goorm/gradle:7.3.1-jdk17 AS builder

WORKDIR project

COPY . .

RUN echo "systemProp.http.proxyHost=krmp-proxy.9rum.cc\nsystemProp.http.proxyPort=3128\nsystemProp.https.proxyHost=krmp-proxy.9rum.cc\nsystemProp.https.proxyPort=3128" > /root/.gradle/gradle.properties

RUN chmod +x ./gradlew

RUN ./gradlew clean build

# 빌드 결과 jar 파일을 실행
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "/home/gradle/project/build/libs/Team1_BE-0.0.1-SNAPSHOT.jar"]