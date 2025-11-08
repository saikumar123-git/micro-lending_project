package com.example.micro_lending.Project.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.micro_lending.Project.Entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    private String title;
    private String body;


    @Enumerated(EnumType.STRING)
    private NotificationChannel channel; // EMAIL, SMS, PUSH


    private boolean readFlag = false;
    private LocalDateTime sentAt = LocalDateTime.now();
}