package com.DaoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.Dao.ForumDao;
import com.Model.Forum;

@EnableTransactionManagement
@Repository
public class ForumDaoImpl implements ForumDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	public ForumDaoImpl(SessionFactory sessionFactory)
	{
	this.sessionFactory = sessionFactory;
	}
	@Transactional
	public boolean save(Forum forum)
	{
	try {
		// Session session = sessionFactory.getCurrentSession();
	
		sessionFactory.getCurrentSession().save(forum);
		return true;
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return false;
	}
	}


@Transactional
public boolean update(Forum forum)
{
try {

	sessionFactory.getCurrentSession().update(forum);
	return true;
}
catch(Exception e)
{
	e.printStackTrace();
	return false;
}
}



@Transactional
	public boolean delete(String id)
	{
	try {
		
		
		Forum ForumToDelete = new Forum();
			ForumToDelete.setUserid(id);
			sessionFactory.getCurrentSession().delete(ForumToDelete);
		
		return true;
	}
	catch(Exception e)
	{
		
		e.printStackTrace();
		return false;
	}
	}

@Transactional
public Forum get(String userid)
{
	String hql = "from Forum where userid= "+" '" +userid+ "'";
	Query query =sessionFactory.getCurrentSession().createQuery(hql);
	@SuppressWarnings("unchecked")
	List<Forum> list = query.list();
	if(list == null || list.isEmpty())
	{
		return null;
	}
	else
	{
		return list.get(0);
	}
}




@SuppressWarnings("unchecked")
@Transactional
public List<Forum> list()
{
	String hql = "from Forum";
	Query query =sessionFactory.getCurrentSession().createQuery(hql);
	return query.list();
}


	
	
  
}
