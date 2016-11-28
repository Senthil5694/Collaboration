package com.Dao;

import java.util.List;

import com.Model.Friend;

public interface FriendDao {

public boolean save(Friend friend);
	
	public boolean update(Friend friend);
	
	public void delete(String userid,String friendid );	
	
	public List<Friend> getmyfriends(String userid);
}
