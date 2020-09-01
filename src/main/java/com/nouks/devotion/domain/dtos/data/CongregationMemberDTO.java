package com.nouks.devotion.domain.dtos.data;

import com.nouks.devotion.domain.models.User;
import com.nouks.devotion.domain.models.pivots.CongregationUser;

public class CongregationMemberDTO {
    private Long id;
    private String fullName;
    private String email;
    private boolean isMinister;

    public CongregationMemberDTO(CongregationUser congregationUser) {
        if (congregationUser != null) {
            User user = congregationUser.getUser();
            if (user != null) {
                this.id = user.getId();
                this.fullName = user.getFullName();
                this.email = user.getEmail();
                this.isMinister = congregationUser.isMinister();
            }
        }
    }

    public CongregationMemberDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isMinister() {
        return isMinister;
    }

    public void setMinister(boolean minister) {
        isMinister = minister;
    }
}
