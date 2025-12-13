package me.elamranioussama.eventsourcingcqrsaxon.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.elamranioussama.eventsourcingcqrsaxon.enums.AccountStatus;

@Getter @AllArgsConstructor
public class AccountActivatedEvent {
    private String accountId;
    private AccountStatus status;
}
