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
@Builder 
@Entity 
public class User {
	
	@Id // Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	
	@Column(nullable = false, length = 100, unique = true)
	private String username; 
	
	@Column(nullable = false, length = 100)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	@Enumerated(EnumType.STRING)
	private RoleType role; 
	
	private String oauth; // kakao, google
	
	@CreationTimestamp 
	private Timestamp createDate;
}
