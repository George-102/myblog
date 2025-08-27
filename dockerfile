FROM tomcat:9-jre17
COPY target/my-blog.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080