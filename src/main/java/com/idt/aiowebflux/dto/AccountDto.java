package com.idt.aiowebflux.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idt.aiowebflux.entity.Account;
import com.idt.aiowebflux.entity.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto {

    private String accountId;
    private String adminId;
    private Role role;
    private String name;

    private String licenseKey;
    private LocalDate expiredDt;

    public AccountDto(String accountId, String adminId, Role role, String name, String licenseKey) {
        this.accountId = accountId;
        this.adminId = adminId;
        this.role = role;
        this.name = name;
        this.licenseKey = licenseKey;
    }

    public static AccountDto from(Account account) {
//        return new AccountDto(
//                account.getAccountId(),
//                account.getAdmin().getAdminId(),
//                account.getRole(),
//                account.getName(),
//                account.getAdmin().getLicenseKey()
//        );
        return null;
    }

    public static List<AccountDto> from(List<Account> account) {
        return account.stream().map(AccountDto::from).toList();
    }

    public void setLicenseKey(String licenseKey) {
        this.licenseKey = licenseKey;
    }

    public void setExpiredDt(LocalDate expiredDt) {
        this.expiredDt = expiredDt;
    }

}
