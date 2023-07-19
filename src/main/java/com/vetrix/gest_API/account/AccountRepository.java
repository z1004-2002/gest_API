package com.vetrix.gest_API.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
    Account findByLogin(String login);
    Account findByRole(Role role);
}
