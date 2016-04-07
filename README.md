# 3rd Party Push Server For Gcm (JPA, Spring Boot, Redis)

JPA, Redis를 이용한 3rd Party 푸시 서버. 

1. SPRING BOOT 으로 JPA 설정.
2. JPA DB 생성 및 GCM 발송 가능 하도록 개발.
3. QueryDSL 적용.
4. Message Queue 적재 부분을 Redis MessageQueue 로 구현.


Use Guide
 1. Redis 설치  후  application.properties 설정 변경.
    - spring.redis.host : redis ip 또는  host
    - spring.redis.password : redis 설정에 따라
    - spring.redis.port : redis 기본 포트는 6379 변경을 하면 여기 수정.
 2. maria DB 설정      
  
 
Trouble Shooting Guide
 1. getter, setter 사용을 편하기 하기 위해 lombok을 사용함. (eclipse 사용자)
    - lombok 설정은 lombok.xxx.jar를 받아  java -jar lombok.xxx.jar를 실행 후 eclipse 지정 해줘야함.
    - 또는 eclipse.ini 에 -javaagent:lombok.jar 설정 추가
 2. QueryDSL 적용으로 인해 maven build path 설정 (eclipse 사용자)
    - eclipse.ini 에 -vm  C:/Program Files/Java/jdk1.8.0_65/bin  vm 경로 직접 설정
    - 다시 maven update 하면 target/generated-sources/java 밑에 O*****.class 들이 잡힘.
    - 실제로 해당 클레스는 QueryDSL 라이브러리에 의해 domain class를 참조하여 자동 생성 되는 클레스.
  



*** 다음 작업 예정.
Queue를 완전히 걷어 내고 Redis 로 완전히 대체.
Push 보내는 Restful api 추가.
gcm xmpp 관련 기능 작업.
  
 
 
 
 

