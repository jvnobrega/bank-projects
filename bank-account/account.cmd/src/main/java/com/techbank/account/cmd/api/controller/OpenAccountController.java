package com.techbank.account.cmd.api.controller;

import com.techbank.account.cmd.api.commands.OpenAccountCommand;
import com.techbank.account.cmd.api.dto.OpenAccountResponse;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.UUID;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/openBankAccount")
public class OpenAccountController {
    private final CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        try {
            commandDispatcher.send(command);
            return new ResponseEntity<>(new OpenAccountResponse(
                    "Bank Account Creation request completed successfully!", id),
                    HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            log.warn("Client made a bad request - {}", e.toString());
            return new ResponseEntity<>(
                    new BaseResponse(e.toString()),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrorMessage = MessageFormat.format("Error while processing request to open a new bank account for id:{0}", id);
            log.error(safeErrorMessage, e);
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}