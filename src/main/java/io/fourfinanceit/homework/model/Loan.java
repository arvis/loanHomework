package io.fourfinanceit.homework.model;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 32)
    private String userId;

    @Column(precision = 10, scale = 2)
    @DecimalMin("5.00")
    @DecimalMax("50000000.00")
    private BigDecimal amount;

    @NotBlank
    @Size(max = 40)
    @Column
    private String ipAddress;

    @Column
    private boolean needsRiskAnalysis;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime loanAssigned;

    @Column
    private Date loanTill;

    @Column
    private Date loanExtendedTill;

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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public boolean isNeedsRiskAnalysis() {
        return needsRiskAnalysis;
    }

    public void setNeedsRiskAnalysis(boolean needsRiskAnalysis) {
        this.needsRiskAnalysis = needsRiskAnalysis;
    }

    public LocalDateTime getLoanAssigned() {
        return loanAssigned;
    }

    public void setLoanAssigned(LocalDateTime loanAssigned) {
        this.loanAssigned = loanAssigned;
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

//    public Instant getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Instant createdAt) {
//        this.createdAt = createdAt;
//    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
