# .bashrc

front=Front-1.0-SNAPSHOT.jar
crawler=Crawler-1.0-SNAPSHOT.jar
cache=GraphsCache-0.0.1-SNAPSHOT.jar

if [ $1 == "start" ]; then
  echo 'Starting processes...'
  java -jar ${front} --spring.config.location=config/front.properties 1>front.log 2>front.err  &
  java -jar ${crawler} --spring.config.location=config/crawler.properties 1>crawler.log 2> crawler.err &
  java -jar ${cache} --spring.config.location=config/cache.properties 1>cache.log 2>cache.err &
elif [ $1 == "stop" ]; then
  echo 'Killing processes...'
  ps axf | grep Front | grep -v grep | `awk '{print "kill -9 " $1}'`
  ps axf | grep Crawler | grep -v grep | `awk '{print "kill -9 " $1}'`
  ps axf | grep GraphsCache | grep -v grep | `awk '{print "kill -9 " $1}'`
fi