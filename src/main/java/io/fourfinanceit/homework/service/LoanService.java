package io.fourfinanceit.homework.service;

import io.fourfinanceit.homework.exception.LoanNotFoundException;
import io.fourfinanceit.homework.model.Loan;
import io.fourfinanceit.homework.payload.ApplyLoanRequest;
import io.fourfinanceit.homework.payload.LoanResponse;
import io.fourfinanceit.homework.repository.LoanRepository;
import io.fourfinanceit.homework.utils.Constants;
import io.fourfinanceit.homework.utils.ConvertToPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class LoanService {


    @Autowired
    private LoanRepository loanRepository;

    public LoanResponse applyForLoan(ApplyLoanRequest applyLoanRequest, String ipAddress) {
        Loan loan = new Loan();
        loan.setAmount(applyLoanRequest.getAmount());
        loan.setUserId(applyLoanRequest.getUserId());
        loan.setLoanTill(applyLoanRequest.getLoanTill());
        loan.setIpAddress(ipAddress);
        loan.setLoanAssigned(LocalDateTime.now());
        return ConvertToPayload.loanToLoanResponse(loanRepository.save(loan));
//        Loan savedLoan = loanRepository.save(loan);
//        boolean needsAnalysis = this.needRiskAnalisys(savedLoan);
//        savedLoan.setNeedsRiskAnalysis(needsAnalysis);
//        return ConvertToPayload.loanToLoanResponse(loanRepository.save(savedLoan));
    }

    public List<LoanResponse> viewLoans(String userId) {
        List<Loan> loanList = loanRepository.findByUserId(userId);
        List<LoanResponse> loanResponses = new ArrayList<LoanResponse>();
        //TODO: maybe use map?
        for (Loan loan : loanList) {
            loanResponses.add(ConvertToPayload.loanToLoanResponse(loan));
        }
        return loanResponses;
    }

    public LoanResponse calculateRisk(Long id) throws LoanNotFoundException {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new LoanNotFoundException(id));
        boolean needsAnalysis = this.needRiskAnalisys(loan);
        loan.setNeedsRiskAnalysis(needsAnalysis);
        return ConvertToPayload.loanToLoanResponse(loanRepository.save(loan));
    }

    public LoanResponse extendLoan(Long id, Date extendTill) throws LoanNotFoundException {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new LoanNotFoundException(id));
        loan.setLoanExtendedTill(extendTill);
        return ConvertToPayload.loanToLoanResponse(loanRepository.save(loan));
    }

    protected boolean needRiskAnalisys(Loan loan) {
        boolean needRiskAnalisys = false;

        LocalDateTime dayStart = loan.getLoanAssigned().toLocalDate().atStartOfDay();
        LocalDateTime dayEnd = loan.getLoanAssigned().toLocalDate().atTime(LocalTime.MAX);
        List<Loan> loansPerDay = loanRepository.findByIpAddressAndDay(loan.getIpAddress(), dayStart, dayEnd);

        System.out.println("--- loans count " + loansPerDay.size());

        //TODO: move into one if
        if (loansPerDay.size() > Constants.MAX_LOANS_PER_DAY) {
            needRiskAnalisys = true;
        }
        System.out.println("--- after 00 " + dayStart.isBefore(loan.getLoanAssigned()));
        System.out.println("--- bigger than " + Constants.MAX_LOAN_AMOUNT.compareTo(loan.getAmount()));

        BigDecimal bg1, bg2;
        bg1 = new BigDecimal("10");
        bg2 = new BigDecimal("20");
        int res = bg1.compareTo(bg2);
        System.out.println("--- compareTo " + res);


        if (dayStart.isBefore(loan.getLoanAssigned()) && (Constants.MAX_LOAN_AMOUNT.compareTo(loan.getAmount()) < 0)) {
            needRiskAnalisys = true;
        }

        System.out.println("--- need analysis " + needRiskAnalisys);

        return needRiskAnalisys;
    }


}
