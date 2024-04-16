package com.techbank.account.cmd.infrastructure;

import com.techbank.cqrs.core.commands.BaseCommand;
import com.techbank.cqrs.core.commands.CommandHandlerMethod;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {
    private final HashMap<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public void send(BaseCommand command) {
        var handlers = routes.get(command.getClass());
        if (isEmpty(handlers)) {
            throw new RuntimeException("No command handler was registered");
        }
        if (handlers.size() > 1) {
            throw new RuntimeException("Cannot send command to more than one handler");
        }
        handlers.get(0).handle(command);
    }
}
