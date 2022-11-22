# 오직(오너와 직원) 서비스 Spring Server

### domain
- 회원관리 : auth
- 근무시간관리 : manage
- 정산관리 : salary

---

#### 2022 / 11 / 21

 - UserDetailsService() - UserDetail() 인터페이스 구현을 확장할 것인지 그냥 사용할 것인지?
   
   - ~~클래스 엔티티 DB 레이어에서 사용할 경우~~
   
   - Manager가 붙어서 Detail()이 필요 없는 경우
    
     - DaoAuthenticationProvider 사용하면 알아서 긁어옴 개쉬움 너무 날먹아닌가?
     
   - ~~타 서비스에서 DTO 객체를 이용할 경우~~
    
   - ~~ROLE을 안 만들 경우~~
 
 - 구현체는 어떠한 관리자 객체를 사용할 것인지?
   - 그걸 왜 사용?
