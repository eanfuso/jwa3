del /Q /F /S \opt\apache-tomcat-9.0.56\webapps\
rmdir /S /Q \opt\apache-tomcat-9.0.56\webapps\ROOT
del /Q /F /S \opt\apache-tomcat-9.0.56\webapps\ROOT.war
mvn clean install -DskipTests