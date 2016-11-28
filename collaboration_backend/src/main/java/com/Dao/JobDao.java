package com.Dao;

import java.util.List;

import com.Model.Job;

public interface JobDao {

public void saveOrUpdate(Job job);
	
	public List<Job> list();
	
	public Job getjob(int jobid);
	
	public void delete(int jobid);
}
