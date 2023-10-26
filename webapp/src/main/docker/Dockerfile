FROM ghcr.io/piranhacloud/webprofile:23.10.0
USER piranha
COPY target/sphynx.war /home/piranha/ROOT.war
CMD ["java", "-jar", "piranha-dist-webprofile.jar", "--war-file", "ROOT.war","--verbose"]
