package com.findit.repository;

import com.findit.model.Claim;
import com.findit.model.Item;
import com.findit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
    List<Claim> findByStatus(String status);
    List<Claim> findByClaimant(User claimant);
    List<Claim> findByItem(Item item);
    boolean existsByItemAndClaimant(Item item, User claimant);
}