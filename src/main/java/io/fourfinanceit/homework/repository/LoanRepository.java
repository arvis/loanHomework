package io.fourfinanceit.homework.repository;

import io.fourfinanceit.homework.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    public List<Loan> findByUserId(String userId);
    public List<Loan> findByIpAddress(String ipAddress);

    @Query("SELECT v FROM Loan v where v.ipAddress = :ipAddress and v.loanAssigned between :dayStart and :dayEnd" )
    List<Loan> findByIpAddressAndDay(@Param("ipAddress") String ipAddress, @Param("dayStart") LocalDateTime dayStart,
                                     @Param("dayEnd") LocalDateTime dayEnd);


}
