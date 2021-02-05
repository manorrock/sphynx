FROM azul/zulu-openjdk-alpine:15 as builder
RUN apk add curl
RUN curl -O https://downloads.apache.org/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz
RUN tar xfvz apache-maven-3.6.3-bin.tar.gz -C /opt
RUN ln -s /opt/apache-maven-3.6.3 /opt/maven
ENV PATH /opt/maven/bin:$PATH
COPY . .
RUN mvn clean install

FROM alpine:3.12
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8
EXPOSE 8080
COPY --from=builder jlink/target/jlink /opt/sphynx/hue-lct06
CMD ["/opt/sphynx/hue-lct06/bin/hue-lct06"]
