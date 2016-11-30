package com.Dao;

import java.util.List;

import com.Model.UserDetails;

public interface UserDao {
public List<UserDetails> list();
	
	public UserDetails getuser(String userid);
	
	public void saveOrUpdate(UserDetails userdetails);
	
	public void save(UserDetails userdetails);
	
	public boolean update(UserDetails userdetails);
	
	public void delete (String userid);
	
	public UserDetails authenticate(String username,String password);
}
