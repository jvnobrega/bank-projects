package com.techbank.account.query;

import com.techbank.account.query.api.queries.*;
import com.techbank.cqrs.core.infrastructure.QueryDispatcher;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@RequiredArgsConstructor
@SpringBootApplication
public class QueryApplication {

    private final QueryDispatcher queryDispatcher;
    private final QueryHandler queryHandler;

    public static void main(String[] args) {
        SpringApplication.run(QueryApplication.class, args);
    }

    @PostConstruct
    public void registerHandlers() {
        queryDispatcher.registeredHandler(FindAllAccountsQuery.class, queryHandler::handle);
        queryDispatcher.registeredHandler(FindAllAccountByIdQuery.class, queryHandler::handle);
        queryDispatcher.registeredHandler(FindAllAccountByHolderQuery.class, queryHandler::handle);
        queryDispatcher.registeredHandler(FindAllAccountWithBalanceQuery.class, queryHandler::handle);
    }

}
