package com.example.boardadmin.domain;

import com.example.boardadmin.domain.constant.RoleType;
import com.example.boardadmin.domain.convertor.RoleTypesConvertor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "email", unique = true),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
})
@Entity
public class UserAccount extends AuditingFields {
    @Id @Column(length = 50) private String userId;

    @Setter @Column(nullable = false) private String userPassword;

    @Column(nullable = false)
    @Convert(converter = RoleTypesConvertor.class)
    private Set<RoleType> roleTypes = new LinkedHashSet<>();

    @Setter @Column(length = 100) private String email;
    @Setter @Column(length = 100) private String nickname;
    @Setter private String memo;

    protected UserAccount() {
    }

    private UserAccount(String userId, String userPassword, Set<RoleType> roleTypes, String email, String nickname, String memo, String createdBy) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.roleTypes = roleTypes;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
        this.createdBy = createdBy;
        this.modifiedBy = createdBy;
    }

    //이미 인증이 되어 있는 상태에서 유저의 정보가 필요 없을 경우
    public static UserAccount of(String userId, String userPassword, Set<RoleType> roleTypes, String email, String nickname, String memo){
        return new UserAccount(userId, userPassword, roleTypes,email, nickname,memo, null);
    }

    // 인증이 안되어 있는 상태이기 때문에 생성자 정보가 없는 경우
    public static UserAccount of(String userId, String userPassword, Set<RoleType> roleTypes, String email, String nickname, String memo, String createdBy){
        return new UserAccount(userId, userPassword, roleTypes, email, nickname,memo, createdBy);
    }

    public void addRoleType(RoleType roleType){
        this.getRoleTypes().add(roleType);
    }

    public void addRoleTypes(Collection<RoleType> roleTypes){
        this.getRoleTypes().addAll(roleTypes);
    }

    public void removeRoleType(RoleType roleType){
        this.getRoleTypes().remove(roleType);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount userAccount)) return false;
        return this.getUserId() != null && this.getUserId().equals(userAccount.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUserId());
    }
}
