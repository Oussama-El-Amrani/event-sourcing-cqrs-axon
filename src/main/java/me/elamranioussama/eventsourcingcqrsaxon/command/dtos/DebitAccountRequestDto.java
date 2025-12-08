package me.elamranioussama.eventsourcingcqrsaxon.command.dtos;

public record DebitAccountRequestDto(String accountId, double amount, String currency) {
}
