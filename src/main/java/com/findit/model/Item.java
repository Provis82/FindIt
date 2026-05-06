package com.findit.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(length = 1000)
    private String description;
    
    private String category;
    
    private String location;
    
    @Column(name = "date_found")
    private LocalDateTime dateFound;
    
    @Column(name = "photo_url")
    private String photoUrl;
    
    private String status = "PENDING"; // PENDING, CLAIMED, RESOLVED
    
    @ManyToOne
    @JoinColumn(name = "posted_by")
    private User postedBy;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}