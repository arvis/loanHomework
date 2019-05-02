package io.fourfinanceit.homework.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.fourfinanceit.homework.exception.LoanNotFoundException;
import io.fourfinanceit.homework.model.Loan;
import io.fourfinanceit.homework.payload.ApplyLoanRequest;
import io.fourfinanceit.homework.payload.LoanResponse;
import io.fourfinanceit.homework.repository.LoanRepository;
import io.fourfinanceit.homework.utils.Constants;
import io.fourfinanceit.homework.utils.ConvertToPayload;

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
	}

	public List<LoanResponse> viewLoans(String userId) {
		List<Loan> loanList = loanRepository.findByUserId(userId);
        return loanList.stream().map(n -> ConvertToPayload.loanToLoanResponse(n))
				.collect(Collectors.toList());
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

	private boolean needRiskAnalisys(Loan loan) {
		boolean needRiskAnalisys = false;

		LocalDateTime dayStart = loan.getLoanAssigned().toLocalDate().atStartOfDay();
		LocalDateTime dayEnd = loan.getLoanAssigned().toLocalDate().atTime(LocalTime.MAX);
		List<Loan> loansPerDay = loanRepository.findByIpAddressAndDay(loan.getIpAddress(), dayStart, dayEnd);

		if ((loansPerDay.size() > Constants.MAX_LOANS_PER_DAY) || (dayStart.isBefore(loan.getLoanAssigned())
				&& (Constants.MAX_LOAN_AMOUNT.compareTo(loan.getAmount()) < 0))) {
			needRiskAnalisys = true;
		}
		return needRiskAnalisys;
	}

}
