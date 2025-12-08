package me.elamranioussama.eventsourcingcqrsaxon.command.aggregates;

import lombok.extern.slf4j.Slf4j;
import me.elamranioussama.eventsourcingcqrsaxon.command.AccountStatus;
import me.elamranioussama.eventsourcingcqrsaxon.command.commands.AddAccountCommand;
import me.elamranioussama.eventsourcingcqrsaxon.command.events.AccountCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private AccountStatus accountStatus;

    public AccountAggregate(){}

    @CommandHandler
    public AccountAggregate(AddAccountCommand command) {
        log.info("------------------------ AddAccountCommand Received ---------------------");
        if (command.getInitialBalance() <= 0) throw new IllegalArgumentException("Balance must be positive");

        // Creation
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getInitialBalance(),
                AccountStatus.CREATED,
                command.getCurrency()
        ));

        // Activation
        AggregateLifecycle.apply(new AccountActivatedEvent(
                command.getId(),
                AccountStatus.ACTIVATED
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        log.info("------------------------ AccountCreatedEvent ---------------------");
        this.accountId = event.getAccountId();
        this.balance = event.getInitialBalance();
        this.accountStatus = event.getAccountStatus();
    }


    @EventSourcingHandler
    public void onCredit(AccountCreditedEvent event) {
        log.info("------------------------ AccountCreditedEvent ---------------------");
        this.accountId = event.getAccountId();
        this.balance += event.getAmount();
    }

    @EventSourcingHandler
    public void onActivate(AccountActivatedEvent event) {
        log.info("------------------------ AccountActivatedEvent ---------------------");
        this.accountId = event.getAccountId();
        this.accountStatus = event.getStatus();
    }
}
