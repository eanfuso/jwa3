#bin/bash
cd /opt/tomcat/bin/
./catalina.sh stop
./catalina.sh run  > /usr/share/tomcat9/tomcat.log 2>&1 &
cd -
