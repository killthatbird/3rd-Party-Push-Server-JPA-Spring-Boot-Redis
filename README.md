# 3rd Party Push Server For Gcm (JPA, Spring Boot, Redis)

JPA, Redis를 이용한 3rd Party 푸시 서버. 

1. SPRING BOOT 으로 JPA 설정.
2. JPA DB 생성 및 GCM 발송 가능 하도록 개발.
3. QueryDSL 적용.
4. Message Queue 적재 부분을 Redis MessageQueue 로 구현.


사용법
 1. Redis 설치  후  application.properties 설정 변경.
    - spring.redis.host : redis ip 또는  host
    - spring.redis.password : redis 설정에 따라
    - spring.redis.port : redis 기본 포트는 6379 변경을 하면 여기 수정.
 2. maria DB 설정      
 
 
 
 

