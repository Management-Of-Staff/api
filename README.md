# 근무시간 관리 어플리케이션 Spring Server

### domain
- 회원관리 : auth
- 근무시간관리 : manage
- 정산관리 : salary

---

#### 2022 / 11 / 21

 - 인증 흐름에서 UserDetailsService - UserDetailsManger 인터페이스 결합과 구현체를 확장용이성을 지키는 것이 맞는지?
  
   - 고정 인증에 사용자 관리를 시큐리티에서 하지 않고 DB 레이어에서 할 예정이면 신경 x
 
 - 구현체는 어떠한 관리자 객체를 사용할 것인지?
   
   - 그걸 왜 사용?
