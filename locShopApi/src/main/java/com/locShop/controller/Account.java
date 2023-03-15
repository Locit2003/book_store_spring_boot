package com.locShop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.locShop.model.UserEntity;
import com.locShop.securityConfig.PasswordGenerator;
import com.locShop.service.AccountService;

@RestController
public class Account {

	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value = "/api/show-acc")
	public List<UserEntity> findAllAcc(){
		
		return accountService.getAllUsers();
	}
	
	@RequestMapping(value = "/api/add-account", method = RequestMethod.POST, consumes="application/json")
	public String addAccount(@RequestBody UserEntity acc) {
		acc.setPassword(new PasswordGenerator().bcryptPassword(acc.getPassword()));
		if(accountService.addUser(acc)) {
			return "thêm mới thành công";
		}
		return "thêm mới thất bại";
	}
	
	@RequestMapping(value = "/login-admin", method = RequestMethod.POST)
	public String loginAdmin() {
		return "login";
	}
}
