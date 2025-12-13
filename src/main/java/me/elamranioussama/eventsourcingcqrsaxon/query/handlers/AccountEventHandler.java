package me.elamranioussama.eventsourcingcqrsaxon.query.handlers;

import lombok.extern.slf4j.Slf4j;
import me.elamranioussama.eventsourcingcqrsaxon.events.AccountCreatedEvent;
import me.elamranioussama.eventsourcingcqrsaxon.query.entities.Account;
import me.elamranioussama.eventsourcingcqrsaxon.query.repositories.AccountRepository;
import me.elamranioussama.eventsourcingcqrsaxon.query.repositories.OperationRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccountEventHandler {
    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;

    public AccountEventHandler(AccountRepository accountRepository, OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    @EventHandler
    public void on(AccountCreatedEvent event, EventMessage eventMessage) {
        log.info("--------------------- Query Side AccountCreatedEvent Received ----------------------");
        Account account = Account.builder()
                .id(event.getAccountId())
                .balance(event.getInitialBalance())
                .status(event.getAccountStatus())
                .currency(event.getCurrency())
                .createdAt(eventMessage.getTimestamp())
                .build();

        accountRepository.save(account);
    }
}
