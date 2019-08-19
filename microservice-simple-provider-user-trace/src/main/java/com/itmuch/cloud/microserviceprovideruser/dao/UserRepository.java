package com.itmuch.cloud.microserviceprovideruser.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.itmuch.cloud.microserviceprovideruser.pojo.User;

public interface UserRepository extends JpaRepository<User, Long> {}
