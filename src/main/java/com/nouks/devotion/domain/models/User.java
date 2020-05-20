package com.nouks.devotion.domain.models;


import com.nouks.devotion.domain.models.pivots.CongregationUser;
import com.nouks.devotion.domain.models.pivots.PulseRelevantToUser;
import com.nouks.devotion.domain.models.pivots.UserCheckedPulse;
import com.nouks.devotion.domain.models.pivots.UserCommentedPulse;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "full_name")
  private String fullName;
  private String email;
  private String password;
  private String phone;
  private boolean enabled;
  @Column(name = "token_expired")
  private boolean tokenExpired;
  @Column(name = "created_at")
  private LocalDateTime createdAt;
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
  @Column(name = "email_verified_at")
  private LocalDateTime emailVerifiedAt;
  @Column(name = "password_reset_code")
  private String passwordResetCode;
  @ManyToMany
  @JoinTable(
    name = "user_role",
    joinColumns = @JoinColumn(
      name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(
      name = "role_id", referencedColumnName = "id"
    )
  )
  private List<Role> roles;
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<CongregationUser> congregationUsers;
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<UserCheckedPulse> userCheckedPulses;
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<UserCommentedPulse> userCommentedPulses;
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<PulseRelevantToUser> pulseRelevantToUsers;
  @ManyToOne
  @JoinColumn(name = "country_id")
  private Country country;
}
