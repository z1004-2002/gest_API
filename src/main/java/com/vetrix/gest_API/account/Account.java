package com.vetrix.gest_API.account;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String login;
    private String password;
    private Role role;

    public Account(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
}
