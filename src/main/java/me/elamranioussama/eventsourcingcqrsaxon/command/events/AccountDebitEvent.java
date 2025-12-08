package me.elamranioussama.eventsourcingcqrsaxon.command.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class AccountDebitEvent {
    private String accountId;
    private double amount;
    private String currency;
}
