package me.elamranioussama.eventsourcingcqrsaxon.query.handlers;

import lombok.extern.slf4j.Slf4j;
import me.elamranioussama.eventsourcingcqrsaxon.enums.OperationType;
import me.elamranioussama.eventsourcingcqrsaxon.events.AccountActivatedEvent;
import me.elamranioussama.eventsourcingcqrsaxon.events.AccountCreatedEvent;
import me.elamranioussama.eventsourcingcqrsaxon.events.AccountCreditedEvent;
import me.elamranioussama.eventsourcingcqrsaxon.events.AccountDebitEvent;
import me.elamranioussama.eventsourcingcqrsaxon.query.entities.Account;
import me.elamranioussama.eventsourcingcqrsaxon.query.entities.AccountOperation;
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

    @EventHandler
    public void on(AccountActivatedEvent event) {
        log.info("--------------------- Query Side AccountActivatedEvent Received ----------------------");
        Account account = accountRepository.findById(event.getAccountId()).orElse(null);
        if (account != null) {
            account.setStatus(event.getStatus());
            accountRepository.save(account);
        }
    }

    @EventHandler
    public void on(AccountDebitEvent event, EventMessage eventMessage) {
        log.info("--------------------- Query Side AccountDebitEvent Received ----------------------");
        Account account = accountRepository.findById(event.getAccountId()).orElse(null);
        if (account != null) {
            AccountOperation accountOperation = AccountOperation.builder()
                    .amount(event.getAmount())
                    .date(eventMessage.getTimestamp())
                    .type(OperationType.DEBIT)
                    .currency(event.getCurrency())
                    .account(account)
                    .build();

            operationRepository.save(accountOperation);

            account.setBalance(account.getBalance() - accountOperation.getAmount());
        }
    }

    @EventHandler
    public void on(AccountCreditedEvent event, EventMessage eventMessage) {
        log.info("--------------------- Query Side AccountCreditedEvent Received ----------------------");
        Account account = accountRepository.findById(event.getAccountId()).orElse(null);
        if (account != null) {
            AccountOperation accountOperation = AccountOperation.builder()
                    .amount(event.getAmount())
                    .date(eventMessage.getTimestamp())
                    .type(OperationType.CREDIT)
                    .currency(event.getCurrency())
                    .account(account)
                    .build();

            operationRepository.save(accountOperation);

            account.setBalance(account.getBalance() + accountOperation.getAmount());
        }
    }
}
