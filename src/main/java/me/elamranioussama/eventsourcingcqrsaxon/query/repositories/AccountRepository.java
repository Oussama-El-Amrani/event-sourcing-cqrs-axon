package me.elamranioussama.eventsourcingcqrsaxon.query.repositories;

import me.elamranioussama.eventsourcingcqrsaxon.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String > {
}
