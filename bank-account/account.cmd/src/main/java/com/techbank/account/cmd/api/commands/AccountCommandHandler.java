package com.techbank.account.cmd.api.commands;

import com.techbank.account.cmd.domain.AccountAggregate;
import com.techbank.cqrs.core.handlers.EventSourcingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountCommandHandler implements CommandHandler {

    private final EventSourcingHandler<AccountAggregate> eventSourcingHandler;
    @Override
    public void handle(OpenAccountCommand command) {
        eventSourcingHandler.save(new AccountAggregate(command));
    }

    @Override
    public void handle(DepositCommand command) {
        AccountAggregate aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.depositFunds(command.getAmount());
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(WithdrawFundsCommand command) {
        AccountAggregate aggregate = eventSourcingHandler.getById(command.getId());
        if (command.getAmount() > aggregate.getBalance()) {
            throw new IllegalStateException("Withdrawal declined, insufficient funds!");
        }
        aggregate.withdrawFunds(command.getAmount());
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(CloseAccountCommand command) {
        AccountAggregate aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.closeAccount();
        eventSourcingHandler.save(aggregate);
    }
}
