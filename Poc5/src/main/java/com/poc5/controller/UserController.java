package com.poc5.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poc5.dto.ResponseDto;
import com.poc5.model.User;
import com.poc5.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {

	private static final int DEFAULT_PAGE_NUMBER = 1;
	private static final int DEFAULT_PAGE_SIZE = 5;

	@Autowired
	private UserService userService;

	// display list of users
	@GetMapping("/")
	public String viewHomePage(Model model) {
		log.info("Redirecting the index page to the controller method for fetching the users in a paginated fashion.");
		return findPaginated(1,"fname","asc",model);

		/*
		 * model.addAttribute("listUsers", userService.getAllUsers()); return "index";
		 */
	}
	
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<User> page = userService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<User> listUsers = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listUsers", listUsers);
		return "index";
	}
	
	
	

	/*
	 * @GetMapping("/users/paginated/{page}/{page-size}") public String
	 * getPaginatedUsers(@PathVariable(name = "page") final int pageNumber,
	 * 
	 * @PathVariable(name = "page-size") final int pageSize, final Model model) {
	 * log.
	 * info("Getting the users in a paginated way for page-number = {} and page-size = {}."
	 * , pageNumber, pageSize); final Page<User> paginatedUsers =
	 * userService.getPaginatedUsers(pageNumber, pageSize);
	 * model.addAttribute("responseEntity",createResponseDto(paginatedUsers,
	 * pageNumber)); return "index"; }
	 * 
	 * private ResponseDto createResponseDto(Page<User> userPage, int pageNumber) {
	 * final Map<String, Integer> page = new HashMap<>(); page.put("currentPage",
	 * pageNumber);
	 * 
	 * // Here we are fetching the total number of records from the Page interface
	 * of // the Spring itself. We can also customize this logic based on the total
	 * number // of elements retrieved from the query.
	 * 
	 * page.put("totalPages", userPage.getTotalPages()); page.put("totalElements",
	 * (int) userPage.getTotalElements()); return
	 * ResponseDto.create(userPage.getContent(), page); }
	 */

	@GetMapping("/showNewUserForm")
	public String showNewUserForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "new_user";
	}

	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute("user") User user) {
		userService.saveUser(user);
		return "redirect:/";
	}

	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") int id, Model model) {
		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		return "update_user";

	}

	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable(value = "id") int id) {
		this.userService.deleteUserById(id);
		return "redirect:/";
	}
}
