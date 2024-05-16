package com.techbank.account.query.api.controllers;

import com.techbank.account.query.api.dto.AccountLookupResponse;
import com.techbank.account.query.api.dto.EqualityType;
import com.techbank.account.query.api.queries.FindAllAccountByHolderQuery;
import com.techbank.account.query.api.queries.FindAllAccountByIdQuery;
import com.techbank.account.query.api.queries.FindAllAccountWithBalanceQuery;
import com.techbank.account.query.api.queries.FindAllAccountsQuery;
import com.techbank.account.query.domain.BankAccount;
import com.techbank.cqrs.core.infrastructure.QueryDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping(path = "api/v1/bankAccountLookup")
public class AccountLookupController {

    public static final String SAFE_ERROR_MESSAGE = "Failed to complete get all accounts request";
    private final QueryDispatcher queryDispatcher;

    @GetMapping
    public ResponseEntity<AccountLookupResponse> getAllAccounts() {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountsQuery());
            if (CollectionUtils.isEmpty(accounts)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            AccountLookupResponse response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Successfully returned {0} banks account(s)", accounts.size()))
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception exception) {
            log.error("m=getAllAccounts stage=error message: {}",
                    exception.getMessage(),
                    exception);
            return ResponseEntity
                    .internalServerError()
                    .body(new AccountLookupResponse(SAFE_ERROR_MESSAGE));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountLookupResponse> getAccountById(
            @PathVariable("id") String id) {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountByIdQuery(id));
            if (CollectionUtils.isEmpty(accounts)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            AccountLookupResponse response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message("Successfully returned account")
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception exception) {
            log.error("m=getAccountById stage=error message: {}",
                    exception.getMessage(),
                    exception);
            return ResponseEntity
                    .internalServerError()
                    .body(new AccountLookupResponse(SAFE_ERROR_MESSAGE));
        }
    }

    @GetMapping("/holder/{accountHolder}")
    public ResponseEntity<AccountLookupResponse> getByAccountHolderName(
            @PathVariable("accountHolder") String accountHolder) {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountByHolderQuery(accountHolder));
            if (CollectionUtils.isEmpty(accounts)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            AccountLookupResponse response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message("Successfully returned account")
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception exception) {
            log.error("m=getByAccountHolderName stage=error message: {}",
                    exception.getMessage(),
                    exception);
            return ResponseEntity
                    .internalServerError()
                    .body(new AccountLookupResponse(SAFE_ERROR_MESSAGE));
        }
    }

    @GetMapping("/withBalance/{equalityType}/{balance}")
    public ResponseEntity<AccountLookupResponse> getAccountWithBalance(
            @PathVariable("equalityType") EqualityType equalityType,
            @PathVariable("balance") double balance) {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountWithBalanceQuery(equalityType, balance));
            if (CollectionUtils.isEmpty(accounts)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            AccountLookupResponse response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Successfully returned {0} banks account(s)", accounts.size()))
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception exception) {
            log.error("m=getByAccountHolderName stage=error message: {}",
                    exception.getMessage(),
                    exception);
            return ResponseEntity
                    .internalServerError()
                    .body(new AccountLookupResponse(SAFE_ERROR_MESSAGE));
        }
    }

}
