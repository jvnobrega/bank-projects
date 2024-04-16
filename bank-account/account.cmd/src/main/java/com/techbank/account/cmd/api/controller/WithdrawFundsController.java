package com.techbank.account.cmd.api.controller;

import com.techbank.account.cmd.api.commands.WithdrawFundsCommand;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.exceptions.AggregateNotFoundException;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/withdrawFunds")
public class WithdrawFundsController {

    private final CommandDispatcher commandDispatcher;

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> withdrawFunds(
            @PathVariable(value = "id") String id,
            @RequestBody WithdrawFundsCommand command) {
        try {
            command.setId(id);
            commandDispatcher.send(command);

            return new ResponseEntity<>(
                    new BaseResponse("Withdraw Funds request completed successfully!"),
                    HttpStatus.CREATED);

        } catch (IllegalStateException | AggregateNotFoundException e) {
            log.warn("Client made a bad request - {}", e.toString());
            return new ResponseEntity<>(
                    new BaseResponse(e.toString()),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrorMessage = MessageFormat.format(
                    "Error while processing request to withdraw funds to a bank account with id - {0}", id);
            log.error(safeErrorMessage, e);
            return new ResponseEntity<>(
                    new BaseResponse(e.toString()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
