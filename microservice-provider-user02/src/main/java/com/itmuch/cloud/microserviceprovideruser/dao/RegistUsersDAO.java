package com.itmuch.cloud.microserviceprovideruser.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itmuch.cloud.microserviceprovideruser.pojo.RegistUsers;

public interface RegistUsersDAO extends JpaRepository<RegistUsers, Long> {}
