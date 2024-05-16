package com.techbank.account.query.api.queries;

import com.techbank.account.query.api.dto.EqualityType;
import com.techbank.account.query.domain.AccountRepository;
import com.techbank.account.query.domain.BankAccount;
import com.techbank.cqrs.core.domain.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountQueryHandler implements QueryHandler {

    private final AccountRepository accountRepository;

    @Override
    public List<BaseEntity> handle(FindAllAccountsQuery query) {
        Iterable<BankAccount> bankAccounts = accountRepository.findAll();
        List<BaseEntity> bankAccountList = new ArrayList<>();
        bankAccounts.forEach(bankAccountList::add);
        return bankAccountList;
    }

    @Override
    public List<BaseEntity> handle(FindAllAccountByIdQuery query) {
        Optional<BankAccount> bankAccount = accountRepository.findById(query.getId());
        if (bankAccount.isEmpty()) {
            return null;
        }
        List<BaseEntity> bankAccountList = new ArrayList<>();
        bankAccount.ifPresent(bankAccountList::add);
        return bankAccountList;
    }

    @Override
    public List<BaseEntity> handle(FindAllAccountByHolderQuery query) {
        Optional<BankAccount> bankAccount = accountRepository.findByAccountHolder(query.getAccountHolder());
        if (bankAccount.isEmpty()) {
            return null;
        }
        List<BaseEntity> bankAccountList = new ArrayList<>();
        bankAccount.ifPresent(bankAccountList::add);
        return bankAccountList;
    }

    @Override
    public List<BaseEntity> handle(FindAllAccountWithBalanceQuery query) {
        List<BaseEntity> bankAccountList = new ArrayList<>();
        if (EqualityType.GREATER_THAN == query.getEqualityType()) {
            bankAccountList.addAll(accountRepository.findByBalanceGreaterThan(query.getBalance())
            );
        } else if (EqualityType.LESS_THAN == query.getEqualityType()) {
            bankAccountList.addAll(accountRepository.findByBalanceLessThan(query.getBalance()));
        }
        return bankAccountList;
    }
}
