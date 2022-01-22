package org.xhliu.kafkaexample.entity;

import com.google.common.base.Objects;

import javax.persistence.*;

/**
 * @author xhliu
 * @create 2022-01-20-14:16
 **/
@Entity
@Table(name = "tb_user")
public class UserEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    @Column(name = "user_account")
    private String userAccount;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_role")
    private Integer userRole;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return userId == that.userId
                && Objects.equal(userAccount, that.userAccount)
                && Objects.equal(userName, that.userName)
                && Objects.equal(userPassword, that.userPassword)
                && Objects.equal(userRole, that.userRole);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId, userAccount, userName, userPassword, userRole);
    }
}
