package com.poc5.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;


import com.poc5.model.User;
import com.poc5.repository.UserRepository1;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository1 userRepository;

	@Override
	public List<User> getAllUsers() {
		log.info("Gettin all users from database");
		// final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		return userRepository.findAll();
	}

	@Override
	public void saveUser(final User user) {
		userRepository.save(user);

	}

	@Override
	public User getUserById(int id) {
		Optional<User> optional = userRepository.findById(id);
		User user = null;
		if (optional.isPresent()) {
			user = optional.get();
		} else {
			throw new RuntimeException("User not found for id: " + id);
		}
		return user;
	}

	@Override
	public void deleteUserById(int id) {
		this.userRepository.deleteById(id);

	}

	@Override
	public Page<User> getPaginatedUsers(int pageNumber, int pageSize) {
		log.info("Fetching the paginated residents from the dB.");
		final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		return userRepository.findAll(pageable);
	}

	@Override
	public Page<User> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.userRepository.findAll(pageable);
	}

}
