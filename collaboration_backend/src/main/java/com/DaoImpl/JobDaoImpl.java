package com.DaoImpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Dao.JobDao;
import com.Model.Job;


@Transactional
@Repository("jobdao")
public class JobDaoImpl implements JobDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public JobDaoImpl(SessionFactory sessionFactory)
	{
		try {
			this.sessionFactory=sessionFactory;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void saveOrUpdate(Job job) {
		sessionFactory.getCurrentSession().saveOrUpdate(job);
		
	}

	public List<Job> list() {
	return null;
	}

	public Job getjob(int jobid) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(int jobid) {
		
		
	}

}
