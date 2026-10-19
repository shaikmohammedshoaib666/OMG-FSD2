# Use Tomcat 9 as base image
FROM tomcat:9.0-jdk21

# Set working directory
WORKDIR /usr/local/tomcat

# Copy WAR file to Tomcat webapps
# We'll build the WAR file and copy it here
COPY target/fsd2-web.war /usr/local/tomcat/webapps/

# Expose port 8080
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
