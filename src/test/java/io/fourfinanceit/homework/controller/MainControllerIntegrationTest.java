package io.fourfinanceit.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.fourfinanceit.homework.payload.ApplyLoanRequest;
import io.fourfinanceit.homework.payload.LoanResponse;
import io.fourfinanceit.homework.service.LoanService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
public class MainControllerIntegrationTest {

    @Autowired
    private MainController controller;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;


    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void testIndex() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Greetings from Spring Boot!")));
    }

    @Test
    public void testApplyForLoan()throws Exception {
        LoanResponse response = new LoanResponse();
        response.setId(1L);
        response.setUserId("1234");
        ApplyLoanRequest request = new ApplyLoanRequest();

        when(loanService.applyForLoan(any(ApplyLoanRequest.class),anyString())).thenReturn(response);

        this.mockMvc.perform( MockMvcRequestBuilders
                .post("/apply")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("\"userId\":\"1234\"")));
    }

    @Test
    public void testViewLoans()throws Exception {
        LoanResponse response = new LoanResponse();
        List<LoanResponse> loans  = new ArrayList<LoanResponse>();
        when(loanService.viewLoans(anyString())).thenReturn(loans);
        this.mockMvc.perform(get("/loans")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("view Loans")));
    }



    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
