package az.unibank.UniTech.mapper;

import az.unibank.UniTech.dto.AccountResponseDto;
import az.unibank.UniTech.model.Account;
import az.unibank.UniTech.model.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface AccountMapper {

    @IterableMapping(qualifiedByName = "toResponseDto")
    List<AccountResponseDto> toResponseDtoList(List<Account> account);

    @Named("toResponseDto")
    @Mapping(target = "userPincode", source = "user", qualifiedByName = "userPincode")
    AccountResponseDto toResponseDto(Account account);

    @Named("userPincode")
    default String userPincode(User user) {
        return user.getPincode();
    }
}
