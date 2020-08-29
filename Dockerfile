FROM gradle:6.6.1-jdk14 AS BUILD
COPY --chown=gradle:gradle . /home/jar/src
WORKDIR /home/jar/src
RUN gradle bootJar

FROM adoptopenjdk/openjdk14:alpine-jre
RUN mkdir /opt/app
COPY --from=BUILD /home/jar/src/build/libs/*.jar /opt/app/app.jar
CMD ["java", "--enable-preview", "-jar", "/opt/app/app.jar" ]
