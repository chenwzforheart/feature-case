rem 1.mvn
rem mvn clean package -Dmaven.test.skip=true
rem 2.start two eureka-server
rem cmd /k start cmd /k XXXXXXXX
echo start two eureka-server
rem start /b java -jar eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer1 >nul 2>nul
rem cmd /k start cmd /k java -jar eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer1 & /k start cmd /k java -jar eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer2
rem start /b java -jar eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer2 >nul 2>nul
rem cmd /k start cmd /k java -jar eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer2
start cmd /k "java -jar eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer1"
start cmd /k "java -jar eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer2"
start cmd /k "java -jar hello-service/target/hello-service-0.0.1-SNAPSHOT.jar --server.port=8081"
start cmd /k "java -jar hello-service/target/hello-service-0.0.1-SNAPSHOT.jar --server.port=8082"

rem hystrix 启动
rem start cmd /k "java -jar hystrix-consumer/target/hystrix-consumer-0.0.1-SNAPSHOT.jar "
rem start cmd /k "java -jar ribbon-consumer/target/ribbon-consumer-0.0.1-SNAPSHOT.jar "
rem start cmd /k "java -jar ribbon-consumer/target/ribbon-consumer-0.0.1-SNAPSHOT.jar "
rem start cmd /k "java -jar ribbon-consumer/target/ribbon-consumer-0.0.1-SNAPSHOT.jar "

rem feign 启动
start cmd /k "java -jar feign-consumer/target/feign-consumer-0.0.1-SNAPSHOT.jar "