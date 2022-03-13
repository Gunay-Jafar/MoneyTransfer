package az.unibank.UniTech.service.impl;

import az.unibank.UniTech.constant.ErrorCode;
import az.unibank.UniTech.constant.Status;
import az.unibank.UniTech.dto.AccountResponseDto;
import az.unibank.UniTech.dto.SuccessResponse;
import az.unibank.UniTech.dto.TransferRequest;
import az.unibank.UniTech.error.AccountNotExistException;
import az.unibank.UniTech.error.CustomIllegalArgumentException;
import az.unibank.UniTech.error.OverDraftException;
import az.unibank.UniTech.mapper.AccountMapper;
import az.unibank.UniTech.model.Account;
import az.unibank.UniTech.repository.AccountRepo;
import az.unibank.UniTech.service.AccountService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepo accountRepo, AccountMapper accountMapper) {
        this.accountRepo = accountRepo;
        this.accountMapper = accountMapper;
    }

    @Override
    @Transactional
    public SuccessResponse transferBalances(TransferRequest transferRequest) {
        Account accountFrom = accountRepo.findById(transferRequest.getAccountFromId())
                .orElseThrow(() -> new AccountNotExistException("Account with id:" + transferRequest.getAccountFromId() + " does not exist.", ErrorCode.ACCOUNT_ERROR));

        Account accountTo = accountRepo.findById(transferRequest.getAccountToId())
                .orElseThrow(() -> new AccountNotExistException("Account with id:" + transferRequest.getAccountFromId() + " does not exist.", ErrorCode.ACCOUNT_ERROR));
        if (accountFrom.getBalance().compareTo(transferRequest.getAmount()) < 0) {
            throw new OverDraftException("Account with id:" + accountFrom.getId() + " does not have enough balance to transfer.", ErrorCode.ACCOUNT_ERROR);
        }
        if (accountFrom.equals(accountTo)) {
            throw new CustomIllegalArgumentException("Didn't try to make transfer to same account");
        }

        if (accountTo.getStatus().equals(Status.DEACTIVE)) {
            throw new CustomIllegalArgumentException("Didn't try to make transfer to deactive account");
        }

        accountFrom.setBalance(accountFrom.getBalance().subtract(transferRequest.getAmount()));
        accountTo.setBalance(accountTo.getBalance().add(transferRequest.getAmount()));
        accountRepo.save(accountFrom);
        accountRepo.save(accountTo);
        return new SuccessResponse(200, true);
    }

    @Override
    public List<AccountResponseDto> findAllByStatus() {
        List<Account> accounts = accountRepo.findByStatus(Status.ACTIVE);
        return accountMapper.toResponseDtoList(accounts);
    }
}
