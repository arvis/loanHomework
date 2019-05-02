package io.fourfinanceit.homework.payload;

import java.math.BigDecimal;
import java.util.Date;

public class LoanResponse {

    private Long id;
    private String userId;
    private BigDecimal amount;
    private String ipAddress;
    private Date loanTill;
    private Date loanExtendedTill;
    private boolean needsRiskAnalysis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getLoanTill() {
        return loanTill;
    }

    public void setLoanTill(Date loanTill) {
        this.loanTill = loanTill;
    }

    public Date getLoanExtendedTill() {
        return loanExtendedTill;
    }

    public void setLoanExtendedTill(Date loanExtendedTill) {
        this.loanExtendedTill = loanExtendedTill;
    }

    public boolean isNeedsRiskAnalysis() {
        return needsRiskAnalysis;
    }

    public void setNeedsRiskAnalysis(boolean needsRiskAnalysis) {
        this.needsRiskAnalysis = needsRiskAnalysis;
    }
}
