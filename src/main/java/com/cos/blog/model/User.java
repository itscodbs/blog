package com.cos.blog.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴
@Entity // User 클래스가 MySQL에 테이블이 생성이 된다.
//@DynamicInsert // insert시에 null인 필드를 제외시켜준다.
//ORM -> Java(다른언어) Object -> 테이블로 매핑해주는 기술
public class User {
	
	@Id // Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링을 따라간다.
	private int id; // 시퀀스, auto_increment
	
	@Column(nullable = false, length = 100, unique = true)
	private String username; // 아이디
	
	@Column(nullable = false, length = 100) // 123456 => 해쉬 (비밀번호 암호화, 넉넉하게 크기 잡아두기)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	// DB는 RoleType 이라는 게 없다.
	// @ColumnDefault("'user'") // 양 옆으로 작은 따옴표를 줘서 문자라는 것을 알려줘야함
	@Enumerated(EnumType.STRING)
	private RoleType role; // Enum을 쓰는게 좋다. // admin, user, manager -> 도메인(도메인은 범위를 의미)
	
	private String oauth; // kakao, google
	
	// 내가 직접 시간을 넣으려면 Timestamp.valueOf(LocalDateTime.now())
	@CreationTimestamp // 값을 비워두고 insert해도 시간이 자동 입력
	private Timestamp createDate;
	
}