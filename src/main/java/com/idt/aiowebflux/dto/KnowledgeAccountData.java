package com.idt.aiowebflux.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idt.aiowebflux.entity.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KnowledgeAccountData {

    private Integer projectId;
    private String accountId;

    private String adminId;
    private Role role;
    private String name;
    private String licenseKey;

//    public static KnowledgeAccountData from(ProjectAccount projectAccount) {
//        return new KnowledgeAccountData(
//                projectAccount.getKnowledge().getProjectId(),
//                projectAccount.getAccount().getAccountId(),
//                projectAccount.getAccount().getAdmin().getAdminId(),
//                projectAccount.getAccount().getRole(),
//                projectAccount.getAccount().getName(),
//                projectAccount.getAccount().getAdmin().getLicenseKey()
//        );
//    }
//
//    public static List<KnowledgeAccountData> from(List<ProjectAccount> projectAccount) {
//        return projectAccount.stream().map(KnowledgeAccountData::from).toList();
//    }
}