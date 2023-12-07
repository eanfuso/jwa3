#bin/bash
cd /opt/tomcat/bin/
./shutdown.sh
sleep 1
./catalina.sh run  > /usr/share/tomcat9/tomcat.log 2>&1 &
cd -
