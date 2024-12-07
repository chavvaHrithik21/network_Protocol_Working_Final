package com.ring_protocol.network.repository;

import com.ring_protocol.network.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.Optional;



public interface LoginRepository extends JpaRepository<Login, String> {

}
