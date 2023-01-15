package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 사용자가 요청 -> 응답 (HTML 파일)
// @Controller

// 사용자가 요청 -> 응답 (Data)
@RestController
public class HttpControllerTest {
	
	private static final String TAG = "HttpController Test : ";
	Member m = new Member(1, "cos", "cos1234", "itscodbs@naver.com");
	
	// http://localhost:8080/blog/http/lombok
	@GetMapping("/http/lombok")
	public String lombokTest() {
		
		System.out.println(TAG + "getter :" + m.getId());
		m.setId(5000);
		System.out.println(TAG + "setter :" + m.getId());
		return "lombok test 완료";
	}
	
	// 인터넷 브라우저 요청은 get방식으로 할 수 밖에 없음 
	// http://localhost:8080/http/get (select)
	@GetMapping("/http/get")
	public String getTest(@RequestBody String text){
		return "get 요청 : " + text;
	}
	
	// http://localhost:8080/http/post -> 그대로 접속하면 405 오류
	@PostMapping("/http/post") // (insert) text/plain, application/json data
	public String postTest(@RequestBody Member m){ // MessageConverter (스프링부트)
		return "post 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
	}
	
	// http://localhost:8080/http/put
	@PutMapping("/http/put") // (update)
	public String putTest(@RequestBody Member m){ 
		return "put 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
	}
	
	// http://localhost:8080/http/delete
	@DeleteMapping("/http/delete") // (delete)
	public String deleteTest(){
		return "delete 요청";
	}	
}
