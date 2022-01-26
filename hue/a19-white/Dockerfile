#
# Run the build through GraalVM
#
FROM ghcr.io/graalvm/graalvm-ce:21.3.0 AS builder
RUN cd /usr/local && \
    curl -O https://archive.apache.org/dist/maven/maven-3/3.8.3/binaries/apache-maven-3.8.3-bin.tar.gz && \
    tar xfvz apache-maven-3.8.3-bin.tar.gz && \
    rm apache-maven-3.8.3-bin.tar.gz
COPY . /root
RUN export PATH=$PATH:/usr/local/apache-maven-3.8.3/bin && \
    gu install native-image && \
    cd /root && \
    export MAVEN_OPTS=-Xmx4G && \
    mvn -DskipTests -DskipITs -ntp clean install && \
    cd hue/a19-white && \
    mvn -DskipTests -DskipITs -ntp -P graalvm clean install 

#
# Create an image with the GraalVM hue-a19-white binary
#
FROM scratch
COPY --from=builder /root/hue/a19-white/target/hue-a19-white /usr/local/bin/hue-a19-white
EXPOSE 8080
CMD ["/usr/local/bin/hue-a19-white"]
