package com.jobhouse.finder.Tables;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Digits;

import com.jobhouse.finder.security.CustomUserDetails;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class EmployeeProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotBlank(message = "Name is required")
    private String fullName;

    @NotNull
    @NotBlank(message = "Field Of Study is required")
    private String fieldOfStudy;

    @DecimalMax("4.0")
    @DecimalMin("0.0")
    private double GPA;

    @NotNull
    @NotBlank(message = "Education is required")
    private String educationLevel;

    

    @DecimalMin("0")

    private Integer yearsOfExperience;

    @NotBlank(message = "Location is required")
    private String location;
    private String bio;

    @Embedded
    private List<Skill> skills;

    @OneToOne(mappedBy = "employeeProfile")
    private User user;

    @Column(nullable = true, length = 64)
    private String profilePicture;

    public void addSkill(Skill skill) {
        this.skills.add(skill);
    }

    // @Lob
    // private byte[] cv;

    @Transient
    public String getPhotosImagePath() {
        if (profilePicture == null || id == null)
            return null;
        return "/user-photos/" + user.getUsername() + "/" + profilePicture;
    }

}
