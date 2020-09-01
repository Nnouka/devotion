package com.nouks.devotion.domain.dtos.responses;

import com.nouks.devotion.domain.dtos.data.CongregationMemberDTO;
import com.nouks.devotion.domain.dtos.data.FullAddressDTO;
import com.nouks.devotion.domain.models.Congregation;
import com.nouks.devotion.domain.models.pivots.CongregationUser;

import java.util.List;
import java.util.stream.Collectors;

public class CongregationResponseDTO {
    private Long id;
    private String name;
    private String displayPic;
    private FullAddressDTO fullAddress;
    private List<CongregationMemberDTO> members;

    public CongregationResponseDTO(Congregation congregation) {
        if (congregation != null) {
            this.id = congregation.getId();
            this.name = congregation.getName();
            this.displayPic = congregation.getDisplayPic();
            this.fullAddress = new FullAddressDTO(congregation.getLocationAddress());
            List<CongregationUser> congregationUsers = congregation.getCongregationUsers();
            if (congregationUsers != null) {
                this.members = congregationUsers.stream()
                        .map(CongregationMemberDTO::new)
                        .collect(Collectors.toList());
            }
        }
    }

    public CongregationResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayPic() {
        return displayPic;
    }

    public void setDisplayPic(String displayPic) {
        this.displayPic = displayPic;
    }

    public FullAddressDTO getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(FullAddressDTO fullAddress) {
        this.fullAddress = fullAddress;
    }

    public List<CongregationMemberDTO> getMembers() {
        return members;
    }

    public void setMembers(List<CongregationMemberDTO> members) {
        this.members = members;
    }
}
