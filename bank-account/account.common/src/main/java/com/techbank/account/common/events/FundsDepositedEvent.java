package com.techbank.account.common.events;

import com.techbank.cqrs.core.events.BaseEvent;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FundsDepositedEvent extends BaseEvent {
    private double amount;

}
