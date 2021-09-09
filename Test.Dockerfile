FROM openjdk:16-jdk-alpine as build

WORKDIR /app

# Copy maven executable to the image
COPY mvnw .
COPY .mvn .mvn

COPY pom.xml .

COPY eureka/src eureka/src
COPY eureka/pom.xml eureka/pom.xml

COPY user/src user/src
COPY user/pom.xml user/pom.xml

RUN rm -r user/src/main/frontend

COPY restaurant/src restaurant/src
COPY restaurant/pom.xml restaurant/pom.xml

RUN rm -r restaurant/src/main/frontend

COPY gateway/src gateway/src
COPY gateway/pom.xml gateway/pom.xml

COPY configuration/src configuration/src
COPY configuration/pom.xml configuration/pom.xml

COPY notification/src notification/src
COPY notification/pom.xml notification/pom.xml

COPY accounts/src accounts/src
COPY accounts/pom.xml accounts/pom.xml

COPY order/src order/src
COPY order/pom.xml order/pom.xml

COPY delivery/src delivery/src
COPY delivery/pom.xml delivery/pom.xml


RUN rm -r delivery/src/main/frontend

COPY common/src common/src
COPY common/pom.xml common/pom.xml

COPY common/src/main/java/com/foodgrid/common user/src/main/java/com/foodgrid
COPY common/src/main/java/com/foodgrid/common restaurant/src/main/java/com/foodgrid
COPY common/src/main/java/com/foodgrid/common notification/src/main/java/com/foodgrid
COPY common/src/main/java/com/foodgrid/common accounts/src/main/java/com/foodgrid
COPY common/src/main/java/com/foodgrid/common order/src/main/java/com/foodgrid
COPY common/src/main/java/com/foodgrid/common delivery/src/main/java/com/foodgrid

RUN rm notification/src/main/java/com/foodgrid/CommonApplication.java
RUN rm user/src/main/java/com/foodgrid/CommonApplication.java
RUN rm restaurant/src/main/java/com/foodgrid/CommonApplication.java
RUN rm accounts/src/main/java/com/foodgrid/CommonApplication.java
RUN rm order/src/main/java/com/foodgrid/CommonApplication.java
RUN rm delivery/src/main/java/com/foodgrid/CommonApplication.java

# Build all the dependencies in preparation to go offline.
# This is a separate step so the dependencies will be cached unless
# the pom.xml file has changed.
RUN ./mvnw dependency:go-offline -B

# Package the application
RUN ./mvnw clean install test