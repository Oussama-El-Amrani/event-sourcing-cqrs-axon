package me.elamranioussama.eventsourcingcqrsaxon.query.handlers;

import me.elamranioussama.eventsourcingcqrsaxon.query.dtos.AccountStatementResponseDTO;
import me.elamranioussama.eventsourcingcqrsaxon.query.entities.Account;
import me.elamranioussama.eventsourcingcqrsaxon.query.entities.AccountOperation;
import me.elamranioussama.eventsourcingcqrsaxon.query.queries.GetAllAccountsQuery;
import me.elamranioussama.eventsourcingcqrsaxon.query.repositories.AccountRepository;
import me.elamranioussama.eventsourcingcqrsaxon.query.repositories.OperationRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountQueryHandler {
    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;

    public AccountQueryHandler(AccountRepository accountRepository, OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query) {
       return accountRepository.findAll();
    }

    @QueryHandler
    public AccountStatementResponseDTO on(GetAccountStatement accountStatement) {
        Account account = accountRepository.findById(accountStatement.getAccountId()).orElse(null);
        if (account == null) throw new RuntimeException("Account not found");

        List<AccountOperation> operations = operationRepository.findByAccountId(account.getId());
        return new AccountStatementResponseDTO(account, operations);
    }
}
