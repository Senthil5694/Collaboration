package com.DaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.Dao.FriendDao;
import com.Model.Friend;


@EnableTransactionManagement
@Repository("frienddao")
public class FriendDaoImpl implements FriendDao{

	public boolean save(Friend friend) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean update(Friend friend) {
		// TODO Auto-generated method stub
		return false;
	}

	public void delete(String userid, String friendid) {
		// TODO Auto-generated method stub
		
	}

	public List<Friend> getmyfriends(String userid) {
		// TODO Auto-generated method stub
		return null;
	}

}
