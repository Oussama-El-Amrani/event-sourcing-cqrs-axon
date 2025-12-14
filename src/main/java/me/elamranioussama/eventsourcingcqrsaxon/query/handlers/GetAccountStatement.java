package me.elamranioussama.eventsourcingcqrsaxon.query.handlers;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class GetAccountStatement {
    private String accountId;
}
