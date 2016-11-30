package com.DaoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.Controller.UserController;
import com.Dao.UserDao;
import com.Model.UserDetails;

@EnableTransactionManagement
@Repository
public class UserDaoImpl implements UserDao{
	
	
	private static final Logger log=LoggerFactory.getLogger(UserDaoImpl.class);
	@Autowired(required=true)
	private SessionFactory sessionFactory;
	
	public UserDaoImpl(SessionFactory sessionFactory){
		try{
			this.sessionFactory =sessionFactory;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Transactional
//this method is used to return list of users from the database
	public List<UserDetails> list() {	
		@SuppressWarnings("unchecked")
		List<UserDetails> list = (List<UserDetails>) 
		          sessionFactory.getCurrentSession()
				.createCriteria(UserDetails.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

		return list;	
	}
	@Transactional
//this method is used to bring a userdetail by sending userid to the database
	public UserDetails getuser(String userid) {
		log.debug("CALLING METHOD GET USER BY USERID");
		String hql = "from UserDetails where userid= "+" '" +userid+ "'";
		Query query =sessionFactory.getCurrentSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		List<UserDetails> list = query.list();
		if(list == null || list.isEmpty())
		{
			return null;
		}
		else
		{
			log.debug("RETURNING USER LIST BY USER ID");
			return list.get(0);
		}
		/*String hql = "from UserDetails where userid = " + "'" + userid + "'";
		log.debug("-->Calling query");
		Query query = sessionFactory.openSession().createQuery(hql);
		log.debug("-->Calling createquery");
		@SuppressWarnings("unchecked")
		List<UserDetails> list = (List<UserDetails>) query.list();
		log.debug("-->returning hql");
		return getuser(hql);*/
	}
	
	@Transactional
//this method is used to saveorupdate the userdetails to the database
	public void saveOrUpdate(UserDetails userdetails) {
		sessionFactory.getCurrentSession().saveOrUpdate(userdetails);
	}
	@Transactional
//this method will save userdetails
	public void save(UserDetails userdetails) {
	
			sessionFactory.getCurrentSession().save(userdetails);
	}
	@Transactional
//this method is used to update userdetails
	public boolean update(UserDetails userdetails) {
			try
			{
			sessionFactory.getCurrentSession().update(userdetails);
			}catch (Exception e)
			{
				e.printStackTrace();
				return false;
			}
			return true;	
			}
	@Transactional
//this method is used to delete a single user by using userid
	public void delete(String userid) {
		UserDetails userdetails = new UserDetails();
		userdetails.setUserid(userid);
		
			sessionFactory.getCurrentSession().delete(userdetails);	
		
	}
/*//this method will return a userdetail based on the userid and password
	public boolean authenticate(String userid, String password) {
		String hql ="from RegisterModel where userid= '" + userid + "' and " + " password ='" + password + "'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		List<UserDetails> list = (List<UserDetails>) query.list();
		if(list != null && !list.isEmpty())
		{
		
			return true;
		}
		
		return false;	
	}*/
	@Transactional
	public UserDetails authenticate(String username, String password) {
		log.debug("CALLING METHOD USER AUTHENTICATE");
    String hql = "from UserDetails  where username= '" + username +"' and " + " password = '" + password+"'";
		
		Query query=  sessionFactory.getCurrentSession().createQuery(hql);
       log.debug("RETURNING METHOD USER AUTHENTICATE");
		return  (UserDetails)query.uniqueResult();
			}


}
