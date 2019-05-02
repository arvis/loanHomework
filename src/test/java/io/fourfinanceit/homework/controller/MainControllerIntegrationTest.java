package io.fourfinanceit.homework.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.fourfinanceit.homework.payload.ApplyLoanRequest;
import io.fourfinanceit.homework.payload.ExtendLoanRequest;
import io.fourfinanceit.homework.payload.LoanResponse;
import io.fourfinanceit.homework.service.LoanService;

@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
public class MainControllerIntegrationTest {

//	@Autowired
//	private MainController controller;
//
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LoanService loanService;

	private LoanResponse loanResponse;

	@Before
	public void setUp() {
		loanResponse = new LoanResponse();
		loanResponse.setId(1L);
		loanResponse.setUserId("1234");
	}
	
	
	@Test
	public void testApplyForLoan() throws Exception {
		ApplyLoanRequest request = new ApplyLoanRequest();

		when(loanService.applyForLoan(any(ApplyLoanRequest.class), anyString())).thenReturn(loanResponse);

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/apply").content(asJsonString(request))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(content().string(containsString("\"userId\":\"1234\"")));
	}
	
	@Test
	public void testExtendForLoan() throws Exception {
		ExtendLoanRequest request = new ExtendLoanRequest();
		when(loanService.extendLoan(any() , any())).thenReturn(loanResponse);

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/extend").content(asJsonString(request))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(containsString("\"userId\":\"1234\"")));
	}

	@Test
	public void testViewLoans() throws Exception {
		List<LoanResponse> list = new ArrayList<LoanResponse>();
		list.add(loanResponse);
		when(loanService.viewLoans(anyString())).thenReturn(list);
		
		this.mockMvc.perform(get("/loans/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("\"userId\":\"1234\"")));
	}
	
	@Test
	public void testCalculateRisk() throws Exception {
		loanResponse.setNeedsRiskAnalysis(true);
		when(loanService.calculateRisk(1L)).thenReturn(loanResponse);
		this.mockMvc.perform(get("/calculate/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("\"userId\":\"1234\"")));
	}
	
	
	
	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
