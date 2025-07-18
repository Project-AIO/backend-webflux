package com.idt.aiowebflux.repository.custom;//package com.idt.aiowebflux.repository.custom;
//
//import com.idt.aiowebflux.dto.AccountData;
//import com.idt.aiowebflux.dto.AdminData;
//import com.idt.aiowebflux.dto.KnowledgeAccountPageDto;
//import com.idt.aiowebflux.dto.QAccountData;
//import com.idt.aiowebflux.dto.QAdminData;
//import com.idt.aiowebflux.util.EncryptUtil;
//import com.querydsl.jpa.JPQLQuery;
//import com.querydsl.jpa.impl.JPAQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
//import org.springframework.data.support.PageableExecutionUtils;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//import static com.idt.aiowebflux.entity.QAdmin.admin;
//import static com.idt.aiowebflux.entity.QProjectAccount.projectAccount;
//
//@Repository
//public class CustomKnowledgeAccountRepositoryImpl extends QuerydslRepositorySupport implements CustomKnowledgeAccountRepository {
//    private final JPAQueryFactory queryFactory;
//    private final EncryptUtil encryptUtil;
//
//    public CustomKnowledgeAccountRepositoryImpl(final JPAQueryFactory queryFactory, final EncryptUtil encryptUtil) {
//        super(ProjectAccount.class);
//        this.queryFactory = queryFactory;
//        this.encryptUtil = encryptUtil;
//    }
//
//    @Override
//    public Page<KnowledgeAccountPageDto> findProjectAccountDataPageByProjectId(final Integer projectId, final Pageable pageable) {
//        final JPAQuery<AccountData> base = queryFactory
//                .select(
//                        new QAccountData(
//                                projectAccount.account.accountId,
//                                projectAccount.account.admin.accountId,
//                                projectAccount.account.role,
//                                projectAccount.account.roleName
//                        )
//                )
//                .from(projectAccount)
//                .where(projectAccount.project.projectId.eq(projectId));
//
//        final JPQLQuery<AccountData> paged = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, base);
//
//        final List<AccountData> results = paged.fetch();
//
//        final List<String> adminIds = results.stream()
//                .map(AccountData::accountId)
//                .distinct()
//                .toList();
//
//        queryFactory
//                .select(projectAccount.count())
//                .from(projectAccount)
//                .where(projectAccount.project.projectId.eq(projectId))
//                .fetchOne();
//
//        final Map<String, AdminData> adminMap = queryFactory
//                .select(
//                        new QAdminData(
//                                admin.accountId,
//                                admin.licenseKey
//                        )
//                )
//                .from(admin)
//                .where(admin.accountId.in(adminIds))
//                .fetch()
//                .stream()
//                .collect(Collectors.toMap(AdminData::accountId, data -> data));
//
//        final List<KnowledgeAccountPageDto> pageResults = results.stream()
//                .map(ad -> new KnowledgeAccountPageDto(
//                        projectId,
//                        ad.accountId(),
//                        ad.accountId(),
//                        ad.role(),
//                        ad.roleName(),
//                        adminMap.get(ad.accountId()).licenseKey(),
//                        encryptUtil.selLicenseKeyToDate(adminMap.get(ad.accountId()).licenseKey())
//                ))
//                .toList();
//
//
//        return PageableExecutionUtils.getPage(
//                pageResults,
//                pageable,
//                paged::fetchCount
//        );
//    }
//
//    @Override
//    public void deleteAllByProject_ProjectIdAndAccount_AccountIdIn(final Integer projectId, final List<String> accountIds) {
//        queryFactory
//                .delete(projectAccount)
//                .where(projectAccount.project.projectId.eq(projectId)
//                        .and(projectAccount.account.accountId.in(accountIds)))
//                .execute();
//    }
//
////    @Override
////    public List<ProjectAccount> findByProject_ProjectId(Integer projectId) {
////        return queryFactory
////                .selectFrom(projectAccount)
////                .where(projectAccount.project.projectId.eq(projectId))
////                .fetch();
////    }
//
//
//}
