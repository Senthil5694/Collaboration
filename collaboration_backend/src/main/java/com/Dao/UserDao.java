package com.Dao;

import java.util.List;

import com.Model.UserDetails;

public interface UserDao {
public List<UserDetails> list();
	
	public UserDetails getuser(String userid);
    public boolean save(UserDetails userdetails);
	
	public boolean update(UserDetails userdetails);
		
		public boolean delete(String userid);
		
		public void setOnLine(String userid);
		public void setOffLine(String userid);
	
	
	public UserDetails authenticate(String username,String password);
}
