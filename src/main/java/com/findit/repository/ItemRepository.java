package com.findit.repository;

import com.findit.model.Item;
import com.findit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByStatus(String status);
    List<Item> findByCategory(String category);
    List<Item> findByLocationContaining(String location);
    List<Item> findByPostedBy(User user);
}