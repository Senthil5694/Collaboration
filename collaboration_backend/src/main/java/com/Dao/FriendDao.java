package com.Dao;

import java.util.List;

import com.Model.Friend;

public interface FriendDao {

   public boolean save(Friend friend);
	public boolean saveOrUpdate(Friend friend);
	public void delete(String userid,String friendid);
	public void setStatusAccept(String id);
	public void setStatusReject(String id);
	public void setOnLine(String userid);
	public void setOffLine(String userid);
	public Friend get(String userid,String friendid);
	public List<Friend> getmyfriends(String userid);
	public List<Friend> getNewFriendrequest(String userid);
	
	
	
	
	
}
