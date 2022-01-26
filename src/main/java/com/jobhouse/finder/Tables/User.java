package com.jobhouse.finder.Tables;

import java.util.*; 

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "users")
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id")
        private Long id;

        @NotNull
        private String fullName;
        private String username;

        @NotNull
        private String email;

        @NotNull
        private String password;
        private boolean enabled;

        @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
        private Role role;

        @OneToOne(cascade = CascadeType.ALL)
        @JoinTable(name = "emp_profile", joinColumns = {
                        @JoinColumn(name = "employee_id", referencedColumnName = "user_id") }, inverseJoinColumns = {
                                        @JoinColumn(name = "profile_id", referencedColumnName = "id") })
        private EmployeeProfile employeeProfile;

        @OneToOne(cascade = CascadeType.ALL)
        @JoinTable(name = "comp_profile", joinColumns = {
                        @JoinColumn(name = "company_id", referencedColumnName = "user_id") }, inverseJoinColumns = {
                                        @JoinColumn(name = "profile_id", referencedColumnName = "id") })
        private CompanyProfile companyProfile;

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
        private List<Post> posts;

        public boolean isAdmin() {
                if (this.getRole().getName().equals("Admin")) {
                        return true;
                } else {
                        return false;
                }
        }

}