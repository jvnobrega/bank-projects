package com.techbank.account.common.events;

import com.techbank.cqrs.core.events.BaseEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@SuperBuilder
public class AccountClosedEvent extends BaseEvent {

}
