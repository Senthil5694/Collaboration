package com.DaoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.Dao.BlogDao;
import com.Model.Blog;

@EnableTransactionManagement
@Repository("blogDao")
public class BlogDaoImpl implements BlogDao {
	
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public BlogDaoImpl(SessionFactory sessionFactory)
	{
		
		this.sessionFactory = sessionFactory;
	}
	
	private Integer getMaxId()
	{
		String hql= "select max(id) from Blog";
		Query query =sessionFactory.getCurrentSession().createQuery(hql);
		Integer maxId = (Integer) query.uniqueResult();
		return maxId;	
	}

	@Transactional
	public boolean save(Blog blog) {
		try {

			sessionFactory.getCurrentSession().save(blog);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
  @Transactional
	public boolean update(Blog blog) {
		try {

			sessionFactory.getCurrentSession().update(blog);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean delete(String id) {
		try {
			
			
			Blog BlogToDelete = new Blog();
				BlogToDelete.setId(id);
				sessionFactory.getCurrentSession().delete(BlogToDelete);
			
			return true;
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public List<Blog> list() {
		String hql = "from Blog";
		Query query =sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}

	@Transactional
	public Blog get(String id) {
		String hql = "from Blog where id= "+" '" +id+ "'";
		Query query =sessionFactory.getCurrentSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		List<Blog> list = query.list();
		if(list == null || list.isEmpty())
		{
			return null;
		}
		else
		{
			return list.get(0);
		}
	}
	

}
