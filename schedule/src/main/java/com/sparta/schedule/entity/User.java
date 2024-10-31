package com.sparta.schedule.entity;

import com.sparta.schedule.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends Timestamed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String Name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();

    public User(UserRequestDto requestDto, String encryptedPassword) {
        this.email = requestDto.getEmail();
        this.password = encryptedPassword;
        this.Name = requestDto.getName();
    }

    public void update(UserRequestDto requestDto) {
        this.Name = requestDto.getName();
        this.email = requestDto.getEmail();
    }
}
