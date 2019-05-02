package io.fourfinanceit.homework.utils;

import io.fourfinanceit.homework.model.Loan;
import io.fourfinanceit.homework.payload.LoanResponse;

public class ConvertToPayload {
    public static LoanResponse loanToLoanResponse(Loan loan){
        LoanResponse loanResponse = new LoanResponse();
        loanResponse.setAmount(loan.getAmount());
        loanResponse.setUserId(loan.getUserId());
        loanResponse.setId(loan.getId());
        loanResponse.setIpAddress(loan.getIpAddress());
        loanResponse.setLoanTill(loan.getLoanTill());
        loanResponse.setLoanExtendedTill(loan.getLoanExtendedTill());
        loanResponse.setNeedsRiskAnalysis(loan.isNeedsRiskAnalysis());
        return loanResponse;
    }
}
