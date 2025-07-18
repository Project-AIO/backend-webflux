package com.idt.aiowebflux.repository.custom;//package com.idt.aiowebflux.repository.custom;
//
//import com.google.common.base.CaseFormat;
//import com.idt.aiowebflux.dto.AccountData;
//import com.idt.aiowebflux.entity.constant.Role;
//import com.idt.aiowebflux.response.DocAccountDataResponse;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.Query;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
////import static com.idt.aiowebflux.entity.QDocumentAccount.documentAccount;
//
//@Repository
//@RequiredArgsConstructor
//public class CustomDocumentAccountRepositoryImpl implements CustomDocumentAccountRepository {
//    private final EntityManager em;
//    private final JPAQueryFactory queryFactory;
//
//    @Override
//    public Page<DocAccountDataResponse> findDocAccountDataPageByDocId(final String docId, final Pageable pageable, final Integer projectId) {
//        final int page = pageable.getPageNumber();
//        final int size = pageable.getPageSize();
//        final Sort.Order order = pageable.getSort().iterator().next();
//
//        final String sortProperty = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, order.getProperty());
//        final String direction = order.getDirection().roleName();
//
//        final String query = String.format("""
//                SELECT\s
//                                         t.account_id,
//                                         t.admin_id,
//                                         t.role,
//                                         t.roleName,
//                                         CASE MIN(t.sort_order)
//                                             WHEN 1 THEN 'AUTHORIZED'
//                                             WHEN 2 THEN 'UNAUTHORIZED'
//                                             ELSE 'NO_PERMISSION'
//                                         END AS permission_status
//                                     FROM (
//                                         -- 권한이 있는 경우 (sort_order 1)
//                                         SELECT
//                                             a.account_id,
//                                             a.admin_id,
//                                             a.role,
//                                             a.roleName,
//                                             1 AS sort_order
//                                         FROM tb_project p
//                                         JOIN tb_project_folder pf ON p.project_id = pf.project_id
//                                         JOIN tb_doc d ON pf.project_folder_id = d.project_folder_id
//                                         JOIN tb_doc_account da ON d.doc_id = da.doc_id
//                                         JOIN tb_account a ON da.account_id = a.account_id
//                                         WHERE d.doc_id = '%s'
//                                         AND p.project_id = %d
//                                        \s
//                                         UNION ALL
//                                        \s
//                                         -- 같은 프로젝트 내에서 특정 문서에 권한이 없는 경우 (sort_order 2)
//                                         SELECT
//                                             a.account_id,
//                                             a.admin_id,
//                                             a.role,
//                                             a.roleName,
//                                             2 AS sort_order
//                                         FROM tb_project p
//                                         JOIN tb_project_folder pf ON p.project_id = pf.project_id
//                                         JOIN tb_doc d ON pf.project_folder_id = d.project_folder_id
//                                         JOIN tb_doc_account da ON d.doc_id = da.doc_id
//                                         JOIN tb_account a ON da.account_id = a.account_id
//                                         WHERE d.doc_id <> '%s'
//                                         AND p.project_id = %d
//                                        \s
//                                         UNION ALL
//                                        \s
//                                         -- 아예 아무 권한도 없는 계정 (sort_order 3)
//                                         SELECT
//                                             a.account_id,
//                                             a.admin_id,
//                                             a.role,
//                                             a.roleName,
//                                             3 AS sort_order
//                                         FROM tb_account a
//                                         LEFT JOIN tb_doc_account da ON a.account_id = da.account_id
//                                         WHERE da.account_id IS NULL
//                                     ) AS t
//                                     GROUP BY t.account_id, t.admin_id, t.role, t.roleName
//                                     ORDER BY MIN(t.sort_order), t.%s %s
//                                     LIMIT %d OFFSET %d;
//                """, docId, projectId, docId, projectId, sortProperty, direction, size, page * size);
//        //projectId도 추가
//
//        return getDocAccountDataResponses(page, size, query, projectId, docId);
//    }
//
////    @Override
////    public void deleteAllByDocument_DocIdAndAccount_AccountIds(String docId, List<String> accountIds) {
////        queryFactory
////                .delete(documentAccount)
////                .where(documentAccount.document.docId.eq(docId)
////                        .and(documentAccount.account.accountId.in(accountIds)))
////                .execute();
////    }
//
//
//    private Page<DocAccountDataResponse> getDocAccountDataResponses(final int page, final int size, final String query, final Integer projectId, final String docId) {
//        final List<Object[]> resultList = generateNativeQueryResultList(query);
//
//        final List<DocAccountDataResponse> results = convert(resultList);
//
//        final String totalQuery = String.format("""
//                    SELECT COUNT(*) AS total_count
//                    FROM (
//                         SELECT t.account_id
//                         FROM (
//                             -- 권한이 있는 경우 (sort_order 1)
//                             SELECT
//                                 a.account_id,
//                                 1 AS sort_order
//                             FROM tb_project p
//                             JOIN tb_project_folder pf ON p.project_id = pf.project_id
//                             JOIN tb_doc d ON pf.project_folder_id = d.project_folder_id
//                             JOIN tb_doc_account da ON d.doc_id = da.doc_id
//                             JOIN tb_account a ON da.account_id = a.account_id
//                             WHERE d.doc_id = '%s'
//                             AND p.project_id = %d
//
//                             UNION ALL
//
//                             -- 같은 프로젝트 내에서 특정 문서에 권한이 없는 경우 (sort_order 2)
//                             SELECT
//                                 a.account_id,
//                                 2 AS sort_order
//                             FROM tb_project p
//                             JOIN tb_project_folder pf ON p.project_id = pf.project_id
//                             JOIN tb_doc d ON pf.project_folder_id = d.project_folder_id
//                             JOIN tb_doc_account da ON d.doc_id = da.doc_id
//                             JOIN tb_account a ON da.account_id = a.account_id
//                             WHERE d.doc_id <> '%s'
//                             AND p.project_id = %d
//
//                             UNION ALL
//
//                             -- 아예 아무 권한도 없는 계정 (sort_order 3)
//                             SELECT
//                                 a.account_id,
//                                 3 AS sort_order
//                             FROM tb_account a
//                             LEFT JOIN tb_doc_account da ON a.account_id = da.account_id
//                             WHERE da.account_id IS NULL
//                         ) AS t
//                         GROUP BY t.account_id
//                    ) AS unioned;
//                """, docId, projectId, docId, projectId);
//
//        final Query nativeQuery = em.createNativeQuery(totalQuery);
//        final Number totalCount = (Number) nativeQuery.getSingleResult();
//
//        final long total = Optional.ofNullable(totalCount).map(Number::longValue).orElse(0L);
//
//
//        return new PageImpl<>(results, PageRequest.of(page, size), total);
//    }
//
//    @SuppressWarnings("unchecked")
//    private List<Object[]> generateNativeQueryResultList(String unionSql) {
//        final Query nativeQuery = em.createNativeQuery(unionSql);
//        return nativeQuery.getResultList();
//    }
//
//    private List<DocAccountDataResponse> convert(final List<Object[]> resultList) {
//        return resultList.stream()
//                .map(result -> {
//                    final String accountId = (String) result[0];
//                    final String accountId = (String) result[1];
//                    final Role role = Role.valueOf((String) result[2]);
//                    final String roleName = (String) result[3];
//                    final String authorization = (String) result[4];
//                    return new DocAccountDataResponse(
//                            new AccountData(
//                                    accountId,
//                                    accountId,
//                                    role,
//                                    roleName
//                            ),
//                            authorization
//                    );
//
//                }).toList();
//    }
//}
