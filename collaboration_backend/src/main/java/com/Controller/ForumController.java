package com.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Dao.ForumDao;
import com.Model.Forum;

@RestController
public class ForumController {
	
	private static final Logger log=LoggerFactory.getLogger(ForumController.class);
	
	@Autowired(required=true)
	private Forum forum;
	
	@Autowired(required=true)
	private ForumDao forumDao;
	
	
	@RequestMapping(value="/forums", method=RequestMethod.GET)
	public ResponseEntity<List<Forum>> listAllForum(){
		log.debug("-->Calling method listAllUsers");
		List<Forum> forum=forumDao.list();
		if(forum.isEmpty()){
			return new ResponseEntity<List<Forum>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Forum>>(forum,HttpStatus.OK);
		
	}
	//getting id
	@RequestMapping(value="/forum/{id}",method=RequestMethod.GET)
	public ResponseEntity<Forum> getuser(@PathVariable("id")String id)
	{
	log.debug("-->calling get method");
   Forum forum=forumDao.get(id);
	if(forum==null)
	{
		forum = new Forum();
		forum.setErrorCode("404");
		forum.setErrorMessage("Forum not found");
		return new ResponseEntity<Forum>(forum,HttpStatus.NOT_FOUND);
	}
	
	return new ResponseEntity<Forum>(forum,HttpStatus.OK);
	}
	
	//to create forum
	@RequestMapping(value="/createforums/", method=RequestMethod.POST)
	public ResponseEntity<Forum> createforums(@RequestBody Forum forum,HttpSession session){
		log.debug("-->Calling method createUsers");
		String loggedInUserid = (String) session.getAttribute("loggedInUserId");
		forum.setUserid(loggedInUserid);
	
			forumDao.save(forum);
			return new ResponseEntity<Forum>(forum,HttpStatus.OK);
		}
	//delete user by using userid
	@RequestMapping(value="/forum/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Forum> deleteuser(@PathVariable("id")String id)
	{
		log.debug("-->calling delete method");
		Forum forum=forumDao.get(id);
		if(forum==null)
		{
			log.debug("-->User does not exist");
			forum = new Forum();
			forum.setErrorCode("404");
			forum.setErrorMessage("Forum not found");
			return new ResponseEntity<Forum>(forum,HttpStatus.NOT_FOUND);
		}
		forumDao.delete(id);
		log.debug("-->User deleted successfully");
		return new ResponseEntity<Forum>(forum,HttpStatus.OK);
		}
	

}
