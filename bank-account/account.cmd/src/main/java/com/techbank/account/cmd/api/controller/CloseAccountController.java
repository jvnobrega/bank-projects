package com.techbank.account.cmd.api.controller;

import com.techbank.account.cmd.api.commands.CloseAccountCommand;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.exceptions.AggregateNotFoundException;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/closeBankAccount")
public class CloseAccountController {
    private final CommandDispatcher commandDispatcher;

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> closeAccount(
            @PathVariable(value = "id") String id) {

        try {
            commandDispatcher.send(new CloseAccountCommand(id));
            return new ResponseEntity<>(
                    new BaseResponse(
                    "Bank Account Closure request completed successfully!"),
                    HttpStatus.CREATED);
        } catch (IllegalStateException | AggregateNotFoundException e) {
            log.warn("Client made a bad request - {}", e.toString());
            return new ResponseEntity<>(
                    new BaseResponse(e.toString()),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrorMessage = MessageFormat.format(
                    "Error while processing request to close bank account with id - {0}", id);
            log.error(safeErrorMessage, e);
            return new ResponseEntity<>(
                    new BaseResponse(e.toString()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}