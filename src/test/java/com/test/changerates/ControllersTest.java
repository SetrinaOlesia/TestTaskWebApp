package com.test.changerates;

import com.test.changerates.service.impl.Parser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ControllersTest {

	private MockMvc mockMvc;
	private Parser parser;

	@Autowired
	public ControllersTest(MockMvc mockMvc, Parser parser) {
		this.mockMvc=mockMvc;
		this.parser = parser;
	}

	@Test
	@WithMockUser(username = "username")
    public void getCurrenciesTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/currencies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("usdeur"))
                .andExpect(MockMvcResultMatchers.model().size(4))
                .andExpect(MockMvcResultMatchers.model().attribute("usdeur", parser.getUsdeur()));
    }

    @Test
	public void checkGetCurrenciesPageWithoutAuthorization() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/currencies"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

    @Test
	public void loginIncorrectUserTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/login")
				.param("username", "incorrectUser")
				.param("password", "user"))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	public void loginCorrectUserTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/login")
				.param("username", "username")
				.param("password", "$2a$10$WQjG9j/8kCf8IVdPIQvanuPAR6sPjrkq.MhkRptRXk8VQYCg0qNyK"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
