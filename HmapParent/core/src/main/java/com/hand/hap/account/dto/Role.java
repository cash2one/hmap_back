package com.hand.hap.account.dto;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.Condition;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Copyright (c) 2017. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:com.hand.hap.account.dto
 * Date:2017/1/23
 * Create By:yahang.liu@hand-china.com
 */
@MultiLanguage
@Table(name = "sys_role_b")
public class Role extends BaseDTO {

    public static final String FIELD_ROLE_ID = "roleId";

    @Id
    @Column
    @GeneratedValue(generator = GENERATOR_TYPE)
    private Long roleId;

    @Column
    @Condition(operator = LIKE)
    private String roleCode;

    @Column
    @MultiLanguageField
    @Condition(operator = LIKE)
    private String roleName;

    @Column
    @MultiLanguageField
    private String roleDescription;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }
}
