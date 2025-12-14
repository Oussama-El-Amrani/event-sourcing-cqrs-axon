package me.elamranioussama.eventsourcingcqrsaxon.query.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WatchEventQuery {
    private String accountId;
}
