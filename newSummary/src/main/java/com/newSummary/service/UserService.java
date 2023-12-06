package com.newSummary.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newSummary.domain.UserRole;
import com.newSummary.domain.dto.user.JoinRequest;
import com.newSummary.domain.dto.user.LoginRequest;
import com.newSummary.domain.dto.user.UserDTO;
import com.newSummary.domain.entity.User;
import com.newSummary.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder encoder;

	 
	// userEmail 중복 체크 중복되면 true return
	public boolean checkUserEmailDuplicate(String userEmail) {
		return userRepository.existsByUserEmail(userEmail);
	}
	// userPhone 중복 체크 중복되면 true return
	public boolean checkUserPhoneDuplicate(String userPhone) {
		return userRepository.existsByUserPhone(userPhone);
	}

	
	// 암호화 안된 회원가입(확인용)
	public void join(JoinRequest req) {
		userRepository.save(req.toEntity());
	}

	// 암호화된 회원가입
	public void join2(JoinRequest req) {
		userRepository.save(req.toEntity(encoder.encode(req.getUserPw())));
	}

	// 이메일로 회원조회
	public UserDTO findByUserEmail(String userEmail) {
		UserDTO userDTO = UserDTO.toUserDTO(userRepository.findByUserEmail(userEmail).get());
		return userDTO;
	}


	// 회원 정보 수정
//	public UserDTO updateUserInfo(UserDTO userDTO) {
//		User originalUser = userRepository.findByUserEmail(userDTO.getUserEmail()).get();
//		if (checkUserPhoneDuplicate(userDTO.getUserPhone())==false){
//		User user = User.builder()
//		    .userEmail(userDTO.getUserEmail())
//            .userPw(userDTO.getUserPw())
//            .userName(userDTO.getUserName())
//            .userPhone(userDTO.getUserPhone())
//            .build();
//		userRepository.save(user);
//		return updateDTO;
//		} else {
//			return null;
//		}
//	}
//	

	/**
	 * userEmail(String)를 입력받아 User을 return 해주는 기능 인증, 
	 * 인가 시 사용 userEmail가 null이거나(로그인 X) 
	 * userEmail로 찾아온 User가 없으면 null return 
	 * userEmail로 찾아온 User가 존재하면 User return
	 */
	public User getLoginUserByEmail(String userEmail) {
		if (userEmail == null)
			return null;

		Optional<User> optionalUser = userRepository.findByUserEmail(userEmail);
		if (optionalUser.isEmpty())
			return null;

		return optionalUser.get();
	}

}