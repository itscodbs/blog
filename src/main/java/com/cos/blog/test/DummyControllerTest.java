package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

import jakarta.transaction.Transactional;

@RestController
public class DummyControllerTest {

	@Autowired // 의존성 주입 (DI)
	private UserRepository userRepository;

	// save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고,
	// id에 대한 데이터가 없으면 insert를 해준다.
	// save 함수는 id를 전달하지 않으면, insert를 해준다.
	// email, password

	// Delete
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}

		return "삭제되었습니다. id : " + id;
	}

	// Update
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		// json 데이터를 요청 -> Java Object (MessageConverterdml Jackson 라이브러리가 반환해서 받아준다)
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());

		// '수정에 실패하였습니다.'
		User user = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());

		// 방법 1)
		// requestUser.setId(id);
		// requestUser.setUsername("itscodbs");
		// userRepository.save(requestUser);

		// 더티 체킹
		return null;
	}

	// Select
	// http://localhost:8080/blog/dummy/user
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();
	}

	// 한 페이지당 2건의 데이터를 return 받아볼 예
	@GetMapping("/dummy/user")
	public List<User> pageList(
			@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<User> pagingUser = userRepository.findAll(pageable);

//		if(pagingUser.isFirst()) { // ifLast()
//			
//		}

		List<User> users = pagingUser.getContent();
		return users;
	}

	// {id} 주소로 파라미터를 전달 받을 수 있음
	// http://localhost:8080/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// user/4 을 찾으면 내가 DB에서 못찾아오게 될 떄 user가 null이 되나?
		// 그럼 return null 이 리턴됨. 그럼 프로그램에 문제있지 않나?
		// Optional로 너의 User 객체를 감싸서 가져올테니, null인지 아닌지 판단해서 return해

		// 람다식
		// User user = userRepository.findById(id).orElseThrow(()->{
		// return new IllegalArgumentException("해당 사용자는 없습니다.");
		// });

		// User user = userRepository.findById(id).orElseGet(new Supplier<User>(){
		// @Override
		// public User get() {
		// return null; // 혹은 return new User();
		// }
		// });

		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 사용자 없습니다. id : " + id);
			}
		});

		// 요청 : 웹브라우저
		// user 객체 = 자바 오브젝트
		// 변환 (웹브라우저가 이해할 수 있는 데이터) -> json (Gson 라이브러리)
		// 스프링부트 = MessageConverter라는 객체가 응답 시 에 자동 작동
		// 만약, 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		// user 오브젝트를 json으로 변환해서 브라우저에게 전송해줌.
		return user;
	}

	// Insert
	// http://localhost:8080/blog/dummy/join (요청)
	// http body에 username, password, email 데이터를 가지고 요청
	@PostMapping("/dummy/join")
	// public String join(String username, String password, String email){
	public String join(User user) {
		System.out.println("id : " + user.getId());
		System.out.println("username : " + user.getUsername());
		System.out.println("password : " + user.getPassword());
		System.out.println("email : " + user.getEmail());
		System.out.println("role : " + user.getRole());
		System.out.println("createDate : " + user.getCreateDate());

		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
}
