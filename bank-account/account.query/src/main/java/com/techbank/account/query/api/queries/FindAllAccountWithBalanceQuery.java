package com.techbank.account.query.api.queries;

import com.techbank.account.query.api.dto.EqualityType;
import com.techbank.cqrs.core.queries.BaseQuery;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class FindAllAccountWithBalanceQuery extends BaseQuery {
    private EqualityType equalityType;
    private double balance;
}
