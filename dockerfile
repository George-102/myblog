FROM tomcat:9.0-jre17
COPY target/myblog.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080