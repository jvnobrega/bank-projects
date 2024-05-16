package com.techbank.account.query.api.queries;

import com.techbank.cqrs.core.queries.BaseQuery;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class FindAllAccountByIdQuery extends BaseQuery {
    private String id;
}
