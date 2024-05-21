ARG BUILD_ENV=local

FROM maven:3.6.3-jdk-11 AS maven

## Build in developer's local machine, assume jar file already created.
FROM maven AS build-local
COPY ./target/application.jar /build/application.jar

## Build in develop environment
FROM maven AS build-develop
COPY settings.xml /root/.m2/settings.xml
COPY . /sources
WORKDIR /build
RUN mvn -f /sources/pom.xml clean verify
RUN cp /sources/web/target/application.jar ./application.jar

## Build in staging environment
FROM maven AS build-staging
COPY settings.xml /root/.m2/settings.xml
COPY devopswizards.gpg /root/devopswizards.gpg
RUN gpg --batch --import /root/devopswizards.gpg
RUN gpg --list-secret-keys --keyid-format=long
COPY . /sources
RUN chown -R root.root /sources
WORKDIR /build
RUN mvn -f /sources/pom.xml -P docker -B clean release:clean release:prepare release:perform
RUN cp /sources/web/target/application.jar ./application.jar

## Extract built jar file into layers
FROM build-${BUILD_ENV} AS extractor
WORKDIR /build/layers
RUN java -Djarmode=layertools -jar /build/application.jar extract

## Create final docker image.
FROM asia-southeast2-docker.pkg.dev/ajaib-devops-dev/base-image/distroless-java11
WORKDIR /app
COPY --from=extractor build/layers/dependencies/ ./
COPY --from=extractor build/layers/spring-boot-loader ./
COPY --from=extractor build/layers/snapshot-dependencies/ ./
COPY --from=extractor build/layers/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
