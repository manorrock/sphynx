#
# Use Debian
#
FROM debian

#
# Update Debian and install curl, gnupg2, gzip, software-properties-common and 
# tar.
#
RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y curl gnupg2 gzip software-properties-common tar && \
    rm -rf /var/lib/apt/lists/*

#
# Install AdoptOpenJDK 11.
#
RUN cd /usr/local && \
    curl --insecure -L -O https://github.com/AdoptOpenJDK/openjdk11-binaries/releases/download/jdk-11.0.7%2B10/OpenJDK11U-jdk_x64_linux_hotspot_11.0.7_10.tar.gz && \
    tar xfvz OpenJDK11U-jdk_x64_linux_hotspot_11.0.7_10.tar.gz && \
    mv jdk-11.0.7+10 jdk && \
    rm OpenJDK11U-jdk_x64_linux_hotspot_11.0.7_10.tar.gz

#
# Install Tomcat 9.
#
RUN cd /usr/local && \
    curl --insecure -L -O https://downloads.apache.org/tomcat/tomcat-9/v9.0.34/bin/apache-tomcat-9.0.34.tar.gz && \
    tar xfvz apache-tomcat-9.0.34.tar.gz && \
    mv apache-tomcat-9.0.34 tomcat && \
    rm apache-tomcat-9.0.34.tar.gz && \
    rm -rf tomcat/webapps/*docs* && \
    rm -rf tomcat/webapps/*examples* && \
    rm -rf tomcat/webapps/*manager* && \
    rm -rf tomcat/webapps/ROOT*

#
# Setup PATH
#
ENV PATH=$PATH:/usr/local/jdk/bin

#
# Set work directory.
#
WORKDIR /usr/local/tomcat

#
# Setup command to start Tomcat with console output.
#
CMD ["bin/catalina.sh", "run"]

#
# Copy Collectivae into Tomcat 9.
#
COPY target/collectivae.war /usr/local/tomcat/webapps/ROOT.war
