#### Stage 1: Build the application
FROM openjdk:16-jdk-alpine as build

# Set the current working directory inside the image
WORKDIR /app

# Copy maven executable to the image
COPY mvnw .
COPY .mvn .mvn

# Copy the pom.xml file
COPY pom.xml .

# Copy the project source
COPY eureka_server/src eureka_server/src
COPY eureka_server/pom.xml eureka_server/pom.xml

COPY user_service/src user_service/src
COPY user_service/pom.xml user_service/pom.xml

COPY restaurant_service/src restaurant_service/src
COPY restaurant_service/pom.xml restaurant_service/pom.xml

COPY gateway/src gateway/src
COPY gateway/pom.xml gateway/pom.xml

COPY configuration/src configuration/src
COPY configuration/pom.xml configuration/pom.xml


# Build all the dependencies in preparation to go offline.
# This is a separate step so the dependencies will be cached unless
# the pom.xml file has changed.
RUN ./mvnw dependency:go-offline -B


# Package the application
RUN ./mvnw package -DskipTests

RUN mkdir -p eureka_server/target/dependency && (cd eureka_server/target/dependency; jar -xf ../*.jar)

RUN mkdir -p user_service/target/dependency && (cd user_service/target/dependency; jar -xf ../*.jar)

RUN mkdir -p restaurant_service/target/dependency && (cd restaurant_service/target/dependency; jar -xf ../*.jar)

RUN mkdir -p configuration/target/dependency && (cd configuration/target/dependency; jar -xf ../*.jar)

RUN mkdir -p configuration/target/dependency && (cd configuration/target/dependency; jar -xf ../*.jar)

#### Stage 2: A  docker image with command to run the eureka_server
FROM openjdk:16-jdk-alpine as configuration

ARG DEPENDENCY=/app/configuration/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8888
ENTRYPOINT ["/bin/sh", "-c", "sleep 40 && java -cp app:app/lib/* com.foodPuppy.configuration.ConfigurationApplication"] configuration


#### Stage 2: A  docker image with command to run the eureka_server
FROM openjdk:16-jdk-alpine as eureka

ARG DEPENDENCY=/app/eureka_server/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8761
ENTRYPOINT ["/bin/sh", "-c", "sleep 40 && java -cp app:app/lib/* com.foodPuppy.eureka_server.EurekaServerApplication"] eureka

#### Stage 2: A  docker image with command to run the user_service
FROM openjdk:16-jdk-alpine as user

ARG DEPENDENCY=/app/user_service/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8081
# ENTRYPOINT ["java","-cp","app:app/lib/*","com.foodPuppy.user_service.UserServiceApplication"] user
# "/bin/sh", "-c", "sleep 10 && java -cp app:app/lib/* com.foodPuppy.user_service.UserServiceApplication"
ENTRYPOINT ["/bin/sh", "-c", "sleep 60 && java -cp app:app/lib/* com.foodPuppy.user_service.UserServiceApplication"] user

#### Stage 2: A docker image with command to run the restaurant_service
FROM openjdk:16-jdk-alpine as restaurant

ARG DEPENDENCY=/app/restaurant_service/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8082
# ENTRYPOINT ["java","-cp","app:app/lib/*","com.foodPuppy.restaurant_service.RestaurantServiceApplication"] restaurant
ENTRYPOINT ["/bin/sh","-c", "sleep 80 && java -cp app:app/lib/* com.foodPuppy.restaurant_service.RestaurantServiceApplication"] restaurant

#### Stage 2: A  docker image with command to run the gateway
FROM openjdk:16-jdk-alpine as gateway

ARG DEPENDENCY=/app/gateway/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8080
ENTRYPOINT ["/bin/sh", "-c", "sleep 100 && java -cp app:app/lib/* com.foodPuppy.gateway.GatewayApplication"] gateway
