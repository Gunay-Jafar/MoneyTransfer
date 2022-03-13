package az.unibank.UniTech.service;

import az.unibank.UniTech.dto.AccountResponseDto;
import az.unibank.UniTech.dto.SuccessResponse;
import az.unibank.UniTech.dto.TransferRequest;
import az.unibank.UniTech.model.Account;
import az.unibank.UniTech.model.User;

import java.util.List;

public interface AccountService {

    SuccessResponse transferBalances(TransferRequest transferRequest);

    List<AccountResponseDto> findAllByStatus();

}
