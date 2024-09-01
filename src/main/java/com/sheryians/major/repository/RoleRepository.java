package com.sheryians.major.repository;

import com.sheryians.major.Model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {


}
