package com.poc5.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.poc5.model.User;


public interface UserService {
	
	List<User> getAllUsers();
	void saveUser(User user);
	User getUserById(int id);
	void deleteUserById(int id);
	Page<User> getPaginatedUsers(int pageNumber, int pageSize);
	Page<User> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

}
