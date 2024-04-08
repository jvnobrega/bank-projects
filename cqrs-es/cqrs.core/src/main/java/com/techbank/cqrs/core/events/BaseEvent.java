package com.techbank.cqrs.core.events;

import com.techbank.cqrs.core.messages.Message;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BaseEvent extends Message {
    private int version;

}
