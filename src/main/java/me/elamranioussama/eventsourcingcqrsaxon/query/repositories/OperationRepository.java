package me.elamranioussama.eventsourcingcqrsaxon.query.repositories;

import me.elamranioussama.eventsourcingcqrsaxon.query.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<AccountOperation, Long> {
    List<AccountOperation> findByAccountId(String id);
}
