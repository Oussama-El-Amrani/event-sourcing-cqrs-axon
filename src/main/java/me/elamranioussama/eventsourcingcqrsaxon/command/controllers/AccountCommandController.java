package me.elamranioussama.eventsourcingcqrsaxon.command.controllers;

import me.elamranioussama.eventsourcingcqrsaxon.command.commands.AddAccountCommand;
import me.elamranioussama.eventsourcingcqrsaxon.command.dtos.AddNewRequestDTO;
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

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception exception) {
        return exception.getMessage();
    }

    @GetMapping("/events/{accountid}")
    public Stream eventStore(@PathVariable String accountid) {
        return eventStore.readEvents(accountid).asStream();
    }
}
