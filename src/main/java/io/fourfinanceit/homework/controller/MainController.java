package io.fourfinanceit.homework.controller;

import io.fourfinanceit.homework.exception.LoanNotFoundException;
import io.fourfinanceit.homework.model.Loan;
import io.fourfinanceit.homework.payload.ApplyLoanRequest;
import io.fourfinanceit.homework.payload.ExtendLoanRequest;
import io.fourfinanceit.homework.payload.LoanResponse;
import io.fourfinanceit.homework.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class MainController {

    @Autowired
    private LoanService loanService;


    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @PostMapping("/apply")
    @ResponseStatus(HttpStatus.CREATED)
    public LoanResponse applyForLoan(@RequestBody ApplyLoanRequest applyLoanRequest, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        return loanService.applyForLoan(applyLoanRequest,ip);
    }

    @PostMapping("/extend")
    public LoanResponse extendLoan(@RequestBody ExtendLoanRequest extendLoanRequest) throws LoanNotFoundException {
        return loanService.extendLoan(extendLoanRequest.getId(),extendLoanRequest.getExtendTill());
    }

    @GetMapping("/calculate/{id}")
    public LoanResponse calculateRisk(@PathVariable Long id) throws LoanNotFoundException {
        return loanService.calculateRisk(id);
    }

    @GetMapping("/loans/{userId}")
    public List<LoanResponse> viewLoans(@PathVariable String userId) {
        return loanService.viewLoans(userId);
    }

}
