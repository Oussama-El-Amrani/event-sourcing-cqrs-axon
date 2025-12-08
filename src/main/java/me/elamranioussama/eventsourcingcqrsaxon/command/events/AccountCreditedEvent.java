package me.elamranioussama.eventsourcingcqrsaxon.command.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.elamranioussama.eventsourcingcqrsaxon.command.AccountStatus;

@Getter @AllArgsConstructor
public class AccountCreditedEvent {
    private String accountId;
    private double amount;
    private String currency;
}
