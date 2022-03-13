package az.unibank.UniTech.controller;

import az.unibank.UniTech.dto.SuccessResponse;
import az.unibank.UniTech.dto.TransferRequest;
import az.unibank.UniTech.service.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final AccountService accountService;

    public TransactionController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public SuccessResponse transferMoney(@RequestBody @Valid TransferRequest transferRequest) {
        return accountService.transferBalances(transferRequest);
    }
}
