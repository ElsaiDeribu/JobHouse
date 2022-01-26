package com.jobhouse.finder.Tables;

import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @Column(name = "role_id")

    private Integer id;

    private String name;
    @OneToMany(mappedBy = "role")
    private List<User> users;

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

}
