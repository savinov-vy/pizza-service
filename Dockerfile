FROM maven:3.9.5-eclipse-temurin-17-alpine as build-container

USER root

RUN mkdir -p /home/service

COPY . /home/service

WORKDIR /home/service

RUN mvn install -Dmaven.test.skip=true



FROM ubuntu/jre:17-22.04_edge

WORKDIR /

COPY --from=build-container /home/service/target/pizza-service-1.0.jar .