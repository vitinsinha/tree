FROM java:8
RUN mkdir -p /usr/src/myapp
COPY tree-1.0.0.jar /usr/src/myapp/
WORKDIR /usr/src/myapp
EXPOSE 8090
ENTRYPOINT ["java","-jar","tree-1.0.0.jar"]