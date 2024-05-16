package com.techbank.account.query.api.queries;

import com.techbank.cqrs.core.domain.BaseEntity;

import java.util.List;

public interface QueryHandler {

    List<BaseEntity> handle(FindAllAccountsQuery query);
    List<BaseEntity> handle(FindAllAccountByIdQuery query);
    List<BaseEntity> handle(FindAllAccountByHolderQuery query);
    List<BaseEntity> handle(FindAllAccountWithBalanceQuery query);
}
