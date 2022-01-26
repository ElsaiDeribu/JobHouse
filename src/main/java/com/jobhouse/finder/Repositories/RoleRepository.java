package com.jobhouse.finder.Repositories;

import com.jobhouse.finder.Tables.Role;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    public Role findById(int id);
}