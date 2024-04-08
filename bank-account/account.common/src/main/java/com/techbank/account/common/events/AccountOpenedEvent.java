package com.techbank.account.common.events;

import com.techbank.account.common.dto.AccountType;
import com.techbank.cqrs.core.events.BaseEvent;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AccountOpenedEvent extends BaseEvent {
    private String accountHolder;
    private AccountType accountType;
    private LocalDateTime createdDate;
    private double openingBalance;
}
