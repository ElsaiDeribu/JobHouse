package com.jobhouse.finder.Tables;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Description cannot be empty")
    private String description;

    @NotEmpty(message = "Category cannot be empty")
    private String category;

    @NotNull(message = "Number of needed candidates cannot be empty")
    @Min(value = 1, message = "Number of needed candidates cannot be less than one")
    private int number; // number of needed candidates

    @NotEmpty(message = "Gender cannot be empty")
    private String gender;

    private Date createdAt = new Date();

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Deadline cannot be empty")
    private Date deadline;

    @Lob
    private String image;

    @ManyToOne
    private User user;

    public static final String[] categories = {
            "IT", "MECHANICAL", "ECONOMICS", "ACCOUNTING",
            "BUSINESS", "CYBER SECURITY", "OTHERS"
    }; // Sample categories

}
