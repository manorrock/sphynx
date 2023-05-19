FROM piranhacloud/webprofile:v23.5.0
USER piranha
COPY target/sphynx.war /home/piranha/ROOT.war
CMD ["java", "-jar", "piranha-dist-webprofile.jar", "--war-file", "ROOT.war","--verbose"]
