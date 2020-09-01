package com.test.changerates;

import com.test.changerates.entity.User;
import com.test.changerates.service.UserService;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@SpringBootTest
class TestApplicationTests {

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Qualifier("userServiceImpl")
	@Autowired
	private UserDetailsService userDetailsService;
	@Test
	void checkRegistration() {

		User user = User.builder().username("Olesia").email("123@gmail.com").password("1234").build();
		String expected = userService.register(user).toString();
		String actual = userService.getUserById(user.getId()).toString();
		Assert.assertEquals(expected, actual);

	}

}
