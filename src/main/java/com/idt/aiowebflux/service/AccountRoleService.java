package com.idt.aiowebflux.service;

import com.idt.aiowebflux.dto.AccountRoleDto;
import com.idt.aiowebflux.dto.AccountSecret;
import com.idt.aiowebflux.dto.SearchParam;
import com.idt.aiowebflux.entity.Account;
import com.idt.aiowebflux.entity.AccountRole;
import com.idt.aiowebflux.entity.Role;
import com.idt.aiowebflux.entity.composite.AccountRoleId;
import com.idt.aiowebflux.entity.constant.Status;
import com.idt.aiowebflux.exception.DomainExceptionCode;
import com.idt.aiowebflux.repository.AccountRepository;
import com.idt.aiowebflux.repository.AccountRoleRepository;
import com.idt.aiowebflux.repository.RoleRepository;
import com.idt.aiowebflux.request.AccountRequest;
import com.idt.aiowebflux.request.AccountRoleRequest;
import com.idt.aiowebflux.request.AccountRolesRequest;
import com.idt.aiowebflux.request.AccountUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class AccountRoleService {
    private final AccountRoleRepository accountRoleRepository;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @Transactional(readOnly = true)
    public AccountSecret findAccountRoleById(final String accountId) {
        return accountRoleRepository.findAccountSecretById(accountId);
    }

    @Transactional(readOnly = true)
    public Page<AccountRoleDto> findAllAccountRoleByPage(final Pageable pageable,
                                                         final String searchField,
                                                         final SearchParam searchParam,
                                                         final Status status
                                                         ) {
        return accountRoleRepository.findAllByPage(pageable, searchField, searchParam, status);
    }

    @Transactional
    public void updateAccountRoles(final String accountId, final AccountUpdateRequest params) {


        final List<AccountRole> existAccountRoles = accountRoleRepository.findByAccount_AccountId(accountId);

        if(!existAccountRoles.isEmpty()) {
            //기존에 사용자가 role를 가지고 있으면 삭제
            accountRoleRepository.deleteByAccount_AccountId(accountId);
        }

        final List<Role> roles = roleRepository.findAllById(params.roleRequest().roleIds());

        if (roles.isEmpty()) {
            throw DomainExceptionCode.ROLE_NOT_FOUND.newInstance();
        }

        final List<AccountRole> results = roles.stream()
                .map(role -> {
                    final Account account = accountRepository.findById(accountId)
                            .orElseThrow(DomainExceptionCode.ACCOUNT_NOT_FOUND::newInstance);

                   account.updateName(params.name());

                    final AccountRoleId accountRoleId = new AccountRoleId(account.getAccountId(), role.getRoleId());
                    return new AccountRole(accountRoleId, account, role);
                }).toList();

        accountRoleRepository.saveAll(results);
    }

    @Transactional
    public void updateAccountRolesBatch(final AccountRolesRequest request) {
        final List<AccountRole> accountRoles = accountRoleRepository.findByAccount_AccountIdIn(request.accountId());

        if (accountRoles.isEmpty()) {
            throw DomainExceptionCode.ACCOUNT_NOT_FOUND.newInstance();
        }

        final List<Role> roles = roleRepository.findAllById(request.addRoleIds());

        if (roles.isEmpty()) {
            throw DomainExceptionCode.ROLE_NOT_FOUND.newInstance();
        }

        //기존에 사용자가 role를 가지고 있으면 무시하고 없으면 추가
        final List<AccountRole> newAccountRoles = roles.stream()
                .flatMap(role -> accountRoles.stream()
                        .filter(accountRole -> !accountRole.getRole().getRoleId().equals(role.getRoleId()))
                        .map(accountRole -> new AccountRole(
                                new AccountRoleId(accountRole.getAccount().getAccountId(), role.getRoleId()),
                                accountRole.getAccount(),
                                role)))
                .toList();

        accountRoleRepository.saveAll(newAccountRoles);
    }

    @Transactional(readOnly = true)
    public Set<String> findAccountRoleNamesByAccountId(final String accountId) {
        return accountRoleRepository.findRoleNameByAccount_AccountId(accountId);
    }

    @Transactional
    public void createAccountAndRoles(final AccountRoleRequest params) {
        accountService.createAccount(new AccountRequest(params.accountId(), params.name()));

        final List<Role> roles = roleRepository.findAllById(params.addRoleIds());

        if (roles.isEmpty()) {
            return ;
        }

        final List<AccountRole> newAccountRoles = roles.stream()
                .map(role -> {
                    final Account account = accountRepository.findById(params.accountId())
                            .orElseThrow(DomainExceptionCode.ACCOUNT_NOT_FOUND::newInstance);
                    final AccountRoleId accountRoleId = new AccountRoleId(account.getAccountId(), role.getRoleId());
                    return new AccountRole(accountRoleId, account, role);
                })
                .toList();
        accountRoleRepository.saveAll(newAccountRoles);
    }

    @Transactional
    public void deleteRoleFromAccount(final String accountId, final Long roleId) {
        final AccountRoleId accountRoleId = new AccountRoleId(accountId, roleId);
        final AccountRole accountRole = accountRoleRepository.findById(accountRoleId)
                .orElseThrow(DomainExceptionCode.ACCOUNT_ROLE_NOT_FOUND::newInstance);

        accountRoleRepository.delete(accountRole);
    }
}
