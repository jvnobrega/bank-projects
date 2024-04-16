package com.techbank.account.cmd.api.dto;

import com.techbank.account.common.dto.BaseResponse;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class OpenAccountResponse extends BaseResponse {
    private String id;
    public OpenAccountResponse(String message, String id) {
        super(message);
        this.id = id;
    }
}
