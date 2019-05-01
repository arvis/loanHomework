package io.fourfinanceit.homework.payload;

import java.math.BigDecimal;
import java.util.Date;

public class ApplyLoanRequest {
    private String userId;
    private BigDecimal amount;
    private Date loanTill;

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

    public Date getLoanTill() {
        return loanTill;
    }

    public void setLoanTill(Date loanTill) {
        this.loanTill = loanTill;
    }
}
