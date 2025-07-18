package com.idt.aiowebflux.service;

import com.idt.aiowebflux.entity.Account;
import com.idt.aiowebflux.entity.constant.Status;
import com.idt.aiowebflux.repository.AccountRepository;
import com.idt.aiowebflux.request.AccountRequest;
import com.idt.aiowebflux.util.EncryptUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final EncryptUtil encryptUtil;

    @Transactional
    public void createAccount(@Valid AccountRequest request) {
        final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final Account account = new Account(request.accountId(), request.name(), encoder.encode("aio1234!"));

        accountRepository.save(account);
    }

    @Transactional
    public void changeAccountStatus(final List<String> accountIds, final Status status) {
        accountRepository.findAllById(accountIds).forEach(ac -> ac.updateStatus(status));
    }

/*

    public AccountService(AccountRepository accountRepository, EncryptUtil encryptUtil) {
        this.accountRepository = accountRepository;
        this.encryptUtil = encryptUtil;
    }

    @Transactional(readOnly = true)
    public Page<AccountDto> getAccountPageList(final int page, final int size,
                                               final Sort.Direction direction,
                                               final String sortProperty) throws Exception {

        final Pageable pageable = PageRequest.of(page - 1, size, direction, sortProperty);
        final Page<Account> accountPage = accountRepository.findAll(pageable);

        Page<AccountDto> accountDto = accountPage.map(AccountDto::from);

        for (int i = 0; i < accountDto.getContent().size(); i++) {
            accountDto.getContent().get(i)
                    .setExpiredDt(encryptUtil.selLicenseKeyToDate(accountDto.getContent().get(i).getLicenseKey()));
            accountDto.getContent().get(i).setLicenseKey(null);
        }

        return accountDto;
    }

    @Transactional(readOnly = true)
    public List<AccountDto> getAccounstList() throws Exception {

        List<AccountDto> accountDto = AccountDto.from(accountRepository.findAll());

        for (AccountDto dto : accountDto) {
            dto.setExpiredDt(encryptUtil.selLicenseKeyToDate(dto.getLicenseKey()));
            dto.setLicenseKey(null);
        }

        return accountDto;
    }

    @Transactional(readOnly = true)
    public List<Account> getAccountsByIds(final List<String> accountIds) {
        final List<Account> accounts = accountRepository.findAllById(accountIds);
        if (accounts.size() != accountIds.size()) {
            throw DomainExceptionCode.ACCOUNT_NOT_FOUND.newInstance();
        }
        return accounts;
    }

    @Transactional
    public ResponseEntity<?> createUser(AccountRequest request) {
        String accountId = UUID.randomUUID().toString();
        String accountId = request.accountId();
        String roleName = request.roleName();
        String role = request.role();

        accountRepository.saveAccount(accountId, accountId, roleName, role);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Transactional
    public void delUser(String accountId) {
        accountRepository.deleteByAccountId(accountId);
    }
*/

}
