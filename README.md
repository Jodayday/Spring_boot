# Q&A 사이트

## 설명
스프링 부트로 만든 묻고답하는사이트입니다.

## 환경
윈도우   
java SE 11  
스프링 부트 2.7.1  
lombok 1.18.24 

thymeleaf(Template Engine)  
Bootstrap v5.1.3  
H2(DBMS)


## 작성한기능
로그인   
로그아웃  
회원가입  
질문,답변 수정  
페이징&검색  

## 파일
test 디렉토리 : H2 DB에 연관된 test  
testQuestion 메소드는 페이징을 위한 더미 데이터 생성  

answer package: 답변에 대한 코드    
question package: 질문에 대한 코드    
user package:  유저에 대한 코드  
controller package: 설정 및 보조 코드 
layout.html : 홈페이지의 frame 