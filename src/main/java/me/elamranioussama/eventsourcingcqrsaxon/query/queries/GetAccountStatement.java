package me.elamranioussama.eventsourcingcqrsaxon.query.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class GetAccountStatement {
    private String accountId;
}
