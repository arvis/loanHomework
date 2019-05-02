package io.fourfinanceit.homework.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import io.fourfinanceit.homework.model.Loan;
import io.fourfinanceit.homework.payload.ApplyLoanRequest;
import io.fourfinanceit.homework.payload.LoanResponse;
import io.fourfinanceit.homework.repository.LoanRepository;

@RunWith(SpringRunner.class)
public class LoanServiceTest {

	private static final String IP_ADDRESS = "127.0.0.1.";

	@TestConfiguration
	static class EmployeeServiceImplTestContextConfiguration {

		@Bean
		public LoanService employeeService() {
			return new LoanService();
		}
	}

	@Autowired
	private LoanService loanService;

	@MockBean
	private LoanRepository employeeRepository;

	private final Long id = 1L;
	private final String userId = "1234";
	private Loan loan;
	private List<Loan> loanList;

	@Before
	public void setUp() {

		loan = new Loan();
		loan.setId(id);
		loan.setAmount(new BigDecimal(10000));
		loan.setUserId(userId);
		loan.setLoanAssigned(LocalDateTime.now());
		
		Loan loan2 = new Loan();
		loan2.setId(2L);
		loan2.setUserId(userId);
		loan2.setLoanAssigned(LocalDateTime.now());
		loan2.setAmount(new BigDecimal(30));
		
		Loan loan3 = new Loan();
		loan3.setId(3L);
		loan3.setUserId(userId);
		loan3.setLoanAssigned(LocalDateTime.now());
		loan3.setAmount(new BigDecimal(50));
		
		loanList = new ArrayList<Loan>();
		loanList.add(loan);
		loanList.add(loan2);
		loanList.add(loan3);

		Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(loan));
		Mockito.when(employeeRepository.save(Mockito.any())).thenReturn(loan);
		Mockito.when(employeeRepository.findByUserId(userId) ).thenReturn(loanList);

	}

	@Test
	public void testApplyLoan() {
		ApplyLoanRequest applyLoanRequest = new ApplyLoanRequest();
		LoanResponse response = loanService.applyForLoan(applyLoanRequest, IP_ADDRESS);
		assertEquals(loan.getUserId(), response.getUserId());
	}

	@Test
	public void testExtendLoan() {
		Date extendDate = new Date();
		LoanResponse response = loanService.extendLoan(id, extendDate);
		assertEquals(extendDate, response.getLoanExtendedTill());
	}
	
	@Test
	public void testCalculateRiskBigLoan() {
		Loan loan4 = new Loan();
		loan4.setId(3L);
		loan4.setUserId(userId);
		loan4.setLoanAssigned(LocalDateTime.now());
		loan4.setAmount(new BigDecimal(50));
		loanList.add(loan4);
		
		Mockito.when(employeeRepository.findByIpAddressAndDay(Mockito.anyString(), Mockito.any(), Mockito.any()) ).thenReturn(loanList);
		LoanResponse response = loanService.calculateRisk(id);
		assertEquals(true, response.isNeedsRiskAnalysis());
	}

	@Test
	public void testCalculateRiskTooMany() {
		LoanResponse response = loanService.calculateRisk(id);
		assertEquals(true, response.isNeedsRiskAnalysis());
	}
	
	
	@Test
	public void testViewLoans() {
		List<LoanResponse> response = loanService.viewLoans(userId);
		assertEquals(loanList.size(), response.size());
		assertEquals(loanList.get(0).getId(), response.get(0).getId());
		assertEquals(loanList.get(1).getId(), response.get(1).getId());
		assertEquals(loanList.get(2).getId(), response.get(2).getId());
		
	}
	
	
}
