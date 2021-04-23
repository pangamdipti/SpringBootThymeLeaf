package com.poc5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.poc5.model.User;


@Repository
public interface UserRepository1 extends JpaRepository<User, Integer>{

}
