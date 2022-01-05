package com.app.registration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.registration.model.Employee;
import com.app.registration.model.User;
import com.app.registration.service.EmployeeService;
import com.app.registration.service.RegistrationService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class RegistrationController {
	@Autowired
	private RegistrationService service;
	@Autowired
	private  EmployeeService employeeService;
	
	@PostMapping("/registeruser")
	
	public User registerUser(@RequestBody User user) throws Exception {
		String tempEmailId= user.getEmailId();
		if (tempEmailId !=null && "".equals(tempEmailId)){
		User userobj=service.fetchUserByEmailId(tempEmailId);
		if (userobj !=null) {
			throw new Exception(" user with "+tempEmailId+"is already exist");
			
		}
		}
		User userObj= null;
		userObj= service.saveUser(user);
		return userObj;
		
	}
	@PostMapping("/login")
	
	public User loginUser(@RequestBody User user) throws Exception {
		String tempEmailId=user.getEmailId();
		String tempPass=user.getPassword();
		User userObj=null;
		if (tempEmailId!= null && tempPass != null) {
			userObj=service.fetchUserByEmailIdAndPassword(tempEmailId, tempPass);
			
		}
		if (userObj == null) {
			throw new Exception("bad credentials");
		}
		return userObj;
	}
	

	 @GetMapping("/all")
	    public ResponseEntity<List<Employee>> getAllEmployees () {
	        List<Employee> employees = employeeService.findAllEmployees();
	        return new ResponseEntity<>(employees, HttpStatus.OK);
	    }

	    @GetMapping("/find/{id}")
	    public ResponseEntity<Employee> getEmployeeById (@PathVariable("id") Long id) {
	        Employee employee = employeeService.findEmployeeById(id);
	        return new ResponseEntity<>(employee, HttpStatus.OK);
	    }

	    @PostMapping("/add")
	    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
	        Employee newEmployee = employeeService.addEmployee(employee);
	        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
	    }

	    @PutMapping("/update")
	    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
	        Employee updateEmployee = employeeService.updateEmployee(employee);
	        return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
	    }

	    @DeleteMapping("/delete/{id}")
	    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id) {
	        employeeService.deleteEmployee(id);
	        return new ResponseEntity<>(HttpStatus.OK);
	    }
	}