package com.projectLearn.TaskList.entites;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Builder
@Table(name="token")
public class Token {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String token;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
}
