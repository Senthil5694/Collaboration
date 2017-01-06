package com.Dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.Model.Forum;

@Repository
public interface ForumDao {

   public boolean save(Forum forum);
	
	public boolean update(Forum forum);
		
		public boolean delete(String id);
		
		
		public List<Forum> list();

		
				
		
		
		public Forum get(String userid);
}
