package com.DaoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Dao.UserDao;
import com.Model.UserDetails;

@Transactional
@Repository
public class UserDaoImpl implements UserDao{
	
	@Autowired(required=true)
	private SessionFactory sessionFactory;
	
	public UserDaoImpl(SessionFactory sessionFactory){
		try{
			this.sessionFactory =sessionFactory;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
//this method is used to return list of users from the database
	public List<UserDetails> list() {	
		@SuppressWarnings("unchecked")
		List<UserDetails> list = (List<UserDetails>) 
		          sessionFactory.getCurrentSession()
				.createCriteria(UserDetails.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

		return list;	
	}
//this method is used to bring a userdetail by sending userid to the database
	public UserDetails getuser(String userid) {
		String hql ="from UserDetails where userid=" + "'"+ userid + "'";
		//return get(hql);
		Query query = sessionFactory.openSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		List<UserDetails> list = (List<UserDetails>) query.list();
		return getuser(hql);
	}
//this method is used to saveorupdate the userdetails to the database
	public void saveOrUpdate(UserDetails userdetails) {
		sessionFactory.getCurrentSession().saveOrUpdate(userdetails);
	}
//this method will save userdetails
	public void save(UserDetails userdetails) {
	
			sessionFactory.getCurrentSession().save(userdetails);
	}
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
	public UserDetails authenticate(String userid, String password) {
    String hql = "from UserDetails  where userid= '" + userid +"' and " + " password = '" + password+"'";
		
		Query query=  sessionFactory.getCurrentSession().createQuery(hql);

		return  (UserDetails)query.uniqueResult();
			}


}
