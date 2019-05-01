package io.fourfinanceit.homework.payload;

import java.time.Instant;
import java.util.Date;

public class ExtendLoanRequest {
    private Long id;
    private Date extendTill;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getExtendTill() {
        return extendTill;
    }

    public void setExtendTill(Date extendTill) {
        this.extendTill = extendTill;
    }
}
