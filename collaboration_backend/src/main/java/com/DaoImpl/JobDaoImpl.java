package com.DaoImpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.Dao.JobDao;
import com.Model.Job;


@EnableTransactionManagement
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
	@Transactional
	public void saveOrUpdate(Job job) {
		sessionFactory.getCurrentSession().saveOrUpdate(job);
		
	}
@Transactional
	public List<Job> list() {
	return null;
	}

@Transactional
	public Job getjob(int jobid) {
		// TODO Auto-generated method stub
		return null;
	}
@Transactional
	public void delete(int jobid) {
		
		
	}

}
