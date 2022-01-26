package com.jobhouse.finder.Tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class CompanyProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotBlank(message = "location is required")
    private String location;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(nullable = true, length = 64)
    private String logo;

    @OneToOne(mappedBy = "companyProfile")
    private User user;

    public static enum Type {
        IT, CONSTRUCTION, INDUSTRY, BUSINESS, OTHER
    }

    @Transient
    public String getPhotosImagePath() {
        if (logo == null || id == null)
            return null;

        return "/user-photos/" + id + "/" + logo;
    }
}
