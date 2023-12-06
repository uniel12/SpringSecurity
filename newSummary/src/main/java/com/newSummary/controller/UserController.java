package com.newSummary.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.newSummary.domain.dto.user.JoinRequest;
import com.newSummary.domain.dto.user.UserDTO;
import com.newSummary.domain.entity.User;
import com.newSummary.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
	private final UserService userService;

	@PostMapping("/users/join")
	public ResponseEntity<?> join(@Valid @RequestBody JoinRequest joinRequest, BindingResult bindingResult) {
		// 이메일 중복 체크
		if (userService.checkUserEmailDuplicate(joinRequest.getUserEmail())) {
			bindingResult.addError(new FieldError("joinRequest", " userEmail", "이메일이 중복됩니다."));
		}
		// 전화번호 중복 체크
		if (userService.checkUserEmailDuplicate(joinRequest.getUserPhone())) {
			bindingResult.addError(new FieldError("joinRequest", " userPhone", "전화번호가 중복됩니다."));
		}

		// password와 passwordCheck가 같은지 체크
		if (!joinRequest.getUserPw().equals(joinRequest.getPasswordCheck())) {
			bindingResult.addError(new FieldError("joinRequest", "passwordCheck", "비밀번호가 일치하지 않습니다."));
		}

		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
		}

		userService.join2(joinRequest);
		return ResponseEntity.ok("회원가입 성공");
	}

	// 이메일 중복체크
	@GetMapping("/users/duplication-email/{userEmail}")
	public ResponseEntity<?> checkUserEmail(@PathVariable("userEmail") String userEmail) {
		if (userService.checkUserEmailDuplicate(userEmail)) {
			log.info("userEmail={},message={}",userEmail,"이메일이 중복됩니다.다시 확인해주세요.");
			return  ResponseEntity.ok("이메일 중복");
		} else {
			log.info("userEmail={},message={}",userEmail,"이메일 사용 가능합니다.");
			return  ResponseEntity.ok("이메일 사용가능");
		}

	}

	// 전화번호 중복확인
	@GetMapping("/users/duplication-phone/{userPhone}")
	public ResponseEntity<?> checkNickname(@PathVariable("userPhone") String userPhone) {
		if (userService.checkUserPhoneDuplicate(userPhone)) {
			log.info("userPhone={},message={}",userPhone,"전화번호가 중복됩니다.다시 확인해주세요.");
			return  ResponseEntity.ok("전화번호정보 중복");
		} else {
			log.info("userPhone={},message={}",userPhone,"전화번호 사용가능합니다.");
			return ResponseEntity.ok("전화번호 사용가능");
		}
	}


	// 로그인한 데이터
	@GetMapping("/users/me")
	public ResponseEntity<UserDTO> getCurrentUser(Authentication auth) {
		if (auth == null) {
			return ResponseEntity.badRequest().body(null);
		}
		User user = userService.getLoginUserByEmail(auth.getName());
		UserDTO userDTO = UserDTO.toUserDTO(user);
		return ResponseEntity.ok(userDTO);
	}

	// 회원정보 수정
//	 @PatchMapping("/users/{userEmail}")
//	 public ResponseEntity<UserDTO> editMypageUser(@PathVariable("userEmail") String userEmail,
//			 									   @RequestBody UserDTO userDTO){
//	 UserDTO originUserDTO = userService.findByUserEmail(userDTO.getUserEmail());
//	 if(userService.checkUserPhoneDuplicate(userDTO.getUserPhone())==false) {
//	 userService.updateUserInfo(originUserDTO);
//	 }
//	
//	 User user = userService.getLoginUserByEmail(userDTO.getUserEmail());
//	 UserDTO UpdateuserDTO = UserDTO.toUserDTO(user);
//	 return ResponseEntity.ok(UpdateuserDTO);
//	
//	 }
//	 // 회원 정보 삭제
//	 @DeleteMapping("/users/{userEmail}")
//	 public String deleteUser (@PathVariable("userEmail") String userEmail) {
//		 
//	 }
}
