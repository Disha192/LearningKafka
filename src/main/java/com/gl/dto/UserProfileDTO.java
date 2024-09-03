package com.gl.dto;

import com.gl.entity.Account;
import lombok.Data;

import java.util.List;
@Data
public class UserProfileDTO {
    private int userId;
    private String userName;
    private String userEmail;
    List<Account> accounts;

    public UserProfileDTO() {
        super();
    }
    public UserProfileDTO(int userId, String userName, String userEmail, List<Account> accounts) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.accounts = accounts;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UserProfileDTO{")
                .append("userId=").append(userId)
                .append(", userName='").append(userName).append('\'')
                .append(", userEmail='").append(userEmail).append('\'')
                .append(", accounts=[");
        for (Account account : accounts) {
            sb.append("\n  ").append(account);
        }
        sb.append("\n]}");
        return sb.toString();
    }

}
