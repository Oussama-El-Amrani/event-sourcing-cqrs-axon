package me.elamranioussama.eventsourcingcqrsaxon.command.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.elamranioussama.eventsourcingcqrsaxon.command.AccountStatus;

@Getter @AllArgsConstructor
public class AccountCreatedEvent {
    private String accountId;
    private double initialBalance;
    private AccountStatus accountStatus;
    private String currency;

}
