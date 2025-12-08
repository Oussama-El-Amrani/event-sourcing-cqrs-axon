package me.elamranioussama.eventsourcingcqrsaxon.command.dtos;

public record CreditAccountRequestDto(String accountId, double amount, String currency) {
}
