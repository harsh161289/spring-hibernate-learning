package com.anksys.bgms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.anksys.bgms.controller.HomeController;
import com.anksys.bgms.dao.BGMSDao;
import com.anksys.bgms.model.BGMSUser;


public class BGMSUserService implements UserDetailsService{

	BGMSDao bgmsDao;
	private static final Logger logger = Logger.getLogger(BGMSUserService.class);
	
	public void setBgmsDao(BGMSDao bgmsDao) {
		this.bgmsDao = bgmsDao;
	}
	
	public BGMSDao getBgmsDao() {
		return bgmsDao;
	}
	
	/**
	 * Converts BGMSUser to Spring Security Users
	 */
	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		UserDetails springUser = null;
		try{
			BGMSUser user =  bgmsDao.getUserByUsername(userName);
			List<GrantedAuthority> listOfAuthorities = buildUserAuthorities(user.getRole().toString());
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;
				
			springUser = new User(user.getUserName(), user.getPassword(), user.getEnabled(), accountNonExpired, credentialsNonExpired, accountNonLocked, listOfAuthorities);
			
		}catch(Exception e){
		logger.error("Exception:"+ e);
	}
		return springUser;
}
	private List<GrantedAuthority> buildUserAuthorities(String userRole){
		List<GrantedAuthority> authoritiesList = new ArrayList<GrantedAuthority>();
		authoritiesList.add(new SimpleGrantedAuthority(userRole));
		
		return authoritiesList;
	}
	
}
