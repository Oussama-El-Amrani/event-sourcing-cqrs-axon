package me.elamranioussama.eventsourcingcqrsaxon.query.dtos;

import me.elamranioussama.eventsourcingcqrsaxon.query.entities.Account;
import me.elamranioussama.eventsourcingcqrsaxon.query.entities.AccountOperation;

import java.util.List;

public record AccountStatementResponseDTO(
        Account account,
        List<AccountOperation> operations
) {
}
