package me.elamranioussama.eventsourcingcqrsaxon.command.controllers;

import me.elamranioussama.eventsourcingcqrsaxon.command.commands.AddAccountCommand;
import me.elamranioussama.eventsourcingcqrsaxon.command.commands.CreditAccountCommand;
import me.elamranioussama.eventsourcingcqrsaxon.command.commands.DebitAccountCommand;
import me.elamranioussama.eventsourcingcqrsaxon.command.dtos.AddNewRequestDTO;
import me.elamranioussama.eventsourcingcqrsaxon.command.dtos.CreditAccountRequestDto;
import me.elamranioussama.eventsourcingcqrsaxon.command.dtos.DebitAccountRequestDto;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands/accounts")
public class AccountCommandController {
    private final CommandGateway commandGateway;
    private final EventStore eventStore;

    public AccountCommandController(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    @PostMapping("/add")
    public CompletableFuture<String> addNewAccount(@RequestBody AddNewRequestDTO request) {
        CompletableFuture<String> response = commandGateway.send(
                new AddAccountCommand(
                        UUID.randomUUID().toString(),
                        request.initialBalance(),
                        request.currency()
                )
        );

        return response;
    }

    @PostMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDto request) {
        CompletableFuture<String> response = commandGateway.send(
                new CreditAccountCommand(
                        request.accountId(),
                        request.amount(),
                        request.currency()
                )
        );

        return response;
    }

    @PostMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDto request) {
        CompletableFuture<String> response = commandGateway.send(
                new DebitAccountCommand(
                        request.accountId(),
                        request.amount(),
                        request.currency()
                )
        );

        return response;
    }

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception exception) {
        return exception.getMessage();
    }

    @GetMapping("/events/{accountid}")
    public Stream eventStore(@PathVariable String accountid) {
        return eventStore.readEvents(accountid).asStream();
    }
}
