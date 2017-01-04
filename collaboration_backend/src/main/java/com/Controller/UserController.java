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

import com.Dao.FriendDao;
import com.Dao.UserDao;
import com.Model.UserDetails;

@RestController
public class UserController {
	
	private static final Logger log=LoggerFactory.getLogger(UserController.class);
	
	@Autowired(required=true)
	private UserDao userDao;

	@Autowired
	private UserDetails userdetails;
	
	@Autowired
	private FriendDao friendDao;
	

	// this method will return list of userdetails from the database
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<List<UserDetails>> list() {
		List<UserDetails> userlist = userDao.list();
		if (userlist.isEmpty()) {
			log.debug("USERS NOT AVAILABLE");
			userdetails.setErrorCode("404");
			userdetails.setErrorMessage("users not available");
			userlist.add(userdetails);
		}
		return new ResponseEntity<List<UserDetails>>(userlist, HttpStatus.OK);

	}
	
	// this method will check whether the given userid exists or not
		@RequestMapping(value = "/user/{userid}", method = RequestMethod.GET)
		public ResponseEntity<UserDetails> get(@PathVariable(value = "userid") String userid) {
			userdetails = userDao.getuser(userid);

			if (userdetails == null) {
				log.debug("USER IS NOT EXISTS WITH THIS ID"+userdetails.getUserid());
				userdetails = new UserDetails();
				userdetails.setErrorCode("404");
				userdetails.setErrorMessage("user is not exists with this id"+userdetails.getUserid());
			}
			return new ResponseEntity<UserDetails>(userdetails, HttpStatus.OK);

		}

	
	
	//this method is used to update a user by user id
	@RequestMapping(value="/user/{userid}",method=RequestMethod.PUT)
	public ResponseEntity<UserDetails> updateuser(@PathVariable("userid")String userid)
	{
	log.debug("-->CALLING METHOD USER UPDATE");
	if(userDao.getuser(userid)==null)
	{
		log.debug("-->USER DOES NOT EXISTS");
		userdetails = new UserDetails();
		userdetails.setErrorCode("404");
		userdetails.setErrorMessage("User not found");
		return new ResponseEntity<UserDetails>(userdetails,HttpStatus.NOT_FOUND);
	}
	userDao.update(userdetails);
	log.debug("-->USER UPDATED SUCCESSFULLY");
	return new ResponseEntity<UserDetails>(userdetails,HttpStatus.OK);
	
}
	//this method is used to delete a user from the database  
	@RequestMapping(value="/user/{userid}",method=RequestMethod.DELETE)
	public ResponseEntity<UserDetails> deleteuser(@PathVariable("userid")String userid)
	{
		log.debug("-->CALLING METHOD DELETE USER");
		UserDetails userdetails=userDao.getuser(userid);
		if(userdetails==null)
		{
			log.debug("-->User does not exist");
			userdetails = new UserDetails();
			userdetails.setErrorCode("404");
			userdetails.setErrorMessage("user not found");
			return new ResponseEntity<UserDetails>(userdetails,HttpStatus.NOT_FOUND);
		}
		userDao.delete(userid);
		log.debug("-->USER DELETED SUCCESSFULLY");
		return new ResponseEntity<UserDetails>(userdetails,HttpStatus.OK);
	}
	
	//this method is used to create a user table in database when the user registers
		@RequestMapping(value="/createusers/", method=RequestMethod.POST)
		public ResponseEntity<UserDetails> createusers(@RequestBody UserDetails userdetails){
			log.debug("-->CALLING METHOD CREATE USER");
			if(userDao.getuser(userdetails.getUserid())==null){
				userDao.save(userdetails);
				return new ResponseEntity<UserDetails>(userdetails,HttpStatus.OK);
			}
			log.debug("-->USER ALREADY EXISTS"+userdetails.getUserid());
			return new ResponseEntity<UserDetails>(userdetails,HttpStatus.OK);
			}
	//authentication
		@RequestMapping(value="/user/authenticate",method=RequestMethod.POST)
		public ResponseEntity<UserDetails> authenticateuser(@RequestBody UserDetails userdetails,HttpSession session)
		{
			log.debug("-->CALLING METHOD USER AUTHENTICATE");
		
			userdetails=userDao.authenticate(userdetails.getUsername(), userdetails.getPassword());
			if(userdetails==null)
			{
				log.debug("-->USER DOES NOT EXISTS");
				userdetails = new UserDetails();
				userdetails.setErrorCode("404");
				userdetails.setErrorMessage("Invalid Credentials, Please enter vaild credentials");
		}
			else
			{
				userdetails.setErrorCode("200");
				log.debug("-->USER EXISTS WITH THE ABOVE CREDENTIALS");
				session.setAttribute("loggedInUser",userdetails);
				session.setAttribute("loggedInUserId", userdetails.getUsername());
				friendDao.setOnLine(userdetails.getUsername());
				userDao.setOnLine(userdetails.getUsername());
			}
			return new ResponseEntity<UserDetails>(userdetails,HttpStatus.OK);
		}
		//this method is used to logout a user from the application
		@RequestMapping(value="/user/logout",method=RequestMethod.GET)
		public ResponseEntity<UserDetails> logout(HttpSession session)
		{
			log.debug("CALLING METHOD USER LOGOUT");
			UserDetails loggedInUser = (UserDetails) session.getAttribute("loggedInUser");
			userdetails= userDao.authenticate(loggedInUser.getUsername(), loggedInUser.getPassword());
			friendDao.setOffLine(userdetails.getUsername());
			userDao.setOffLine(userdetails.getUsername());
			session.invalidate();
			return new ResponseEntity<UserDetails>(userdetails,HttpStatus.OK);
		}
	
		
		
}
	
	
/*//this method is used to save the userdetails to the database when the user registers
		@RequestMapping(value="/createusers/",method=RequestMethod.POST)
		public ResponseEntity<UserDetails> registeruser(@RequestBody UserDetails userdetails,HttpSession httpSession) {
			if(userDao.getuser(userdetails.getUserid())==null)
			{
				userdetails.setStatus("R");
				userdetails.setIsOnline("N");
				userDao.save(userdetails);
				log.debug("USER REGISTERED SUCCESSFULLY"+userdetails.getUsername());
				userdetails.setErrorCode("You have Registered successfully,you have registered as"+userdetails.getRole());
				return new ResponseEntity<UserDetails>(userdetails,HttpStatus.OK);
			}else
			{
				log.debug("USERID ALREADY EXISTS:"+userdetails.getUserid());
				userdetails.setErrorCode("404");
				userdetails.setErrorMessage("User id already exists please try with new userid :"+ userdetails.getUserid());
				return new ResponseEntity<UserDetails>(userdetails,HttpStatus.OK);
			}

		}
	
		// this method will validate whether the entered userid and password are
		// correct or not
		@RequestMapping(value = "/user/authenticate", method = RequestMethod.POST)
		public ResponseEntity<UserDetails> login(@RequestBody UserDetails userdetails, HttpSession httpSession) {
			//userdao.authenticate(userdetails.getUserid(),userdetails.getPassword());
			userdetails=userDao.authenticate(userdetails.getUsername(),userdetails.getPassword());

			if (userdetails == null) {
				log.debug("INVALID CREDENTIALS");
				userdetails = new UserDetails();
				userdetails.setErrorCode("404");
				userdetails.setErrorMessage("Invalid credentials please try again");

			} else {
				if(userdetails.getStatus()=="R")
				{
					log.debug("YOU ARE NOT APPROVED TO LOGIN");
					userdetails.setErrorCode("404");
					userdetails.setErrorMessage("You are not approved to login please contact admin");
				}
				else{
					log.debug("USER LOGGED IN SUCCESSFULLY" + userdetails.getUsername());
					httpSession.setAttribute("loggedinuserid", userdetails.getUserid());
					httpSession.setAttribute("loggedinuser", userdetails);
					userdetails.setIsOnline("Y");
					//frienddao.setonline(user.getid());
				}
				
			}
			return new ResponseEntity<UserDetails>(userdetails, HttpStatus.OK);
		}
		//this method is used to logout from the session
		@RequestMapping(value = "/user/logout/", method = RequestMethod.POST)
		public ResponseEntity<UserDetails> logout(HttpSession httpSession) {
			// remove the attribute from http session
			// update the status as offline
			String loggedinuserid = (String) httpSession.getAttribute("loggedinuserid");
			userDao.getuser(loggedinuserid);
			userdetails.setIsOnline("N");

			if (userDao.update(userdetails)) {
				log.debug("LOGOUT SUCCESSFULL");
				userdetails.setErrorCode("200");
				userdetails.setErrorMessage("Logout successfull");
			} else {
				log.debug("PROBLEM OCCURED WHILE LOGGING OUT");
				userdetails.setErrorCode("404");
				userdetails.setErrorMessage("problem occured while logging out");
			}
			httpSession.invalidate();
			return new ResponseEntity<UserDetails>(userdetails, HttpStatus.OK);

		}
	
	
	*/
	
	
	
	
	/*// this method will return list of userdetails from the database
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<List<UserDetails>> list() {
		List<UserDetails> userlist = userDao.list();
		if (userlist.isEmpty()) {
			userdetails.setErrorcode("404");
			userdetails.setErrormessage("users not available");
			userlist.add(userdetails);
		}
		return new ResponseEntity<List<UserDetails>>(userlist, HttpStatus.OK);

	}

	//this method is used to accept a user by using his userid
	@RequestMapping(value="/accept/{userid}",method=RequestMethod.POST)
	public ResponseEntity<UserDetails> acceptuser(@PathVariable(value="userid")String userid) {
     userdetails=userDao.getuser(userid);
      userdetails.setStatus("A");
      userDao.update(userdetails);
      return new ResponseEntity<UserDetails>(userdetails,HttpStatus.OK);

	}
	//this method is used to save the userdetails to the database when the user registers
	@RequestMapping(value="/createusers/",method=RequestMethod.POST)
	public ResponseEntity<UserDetails> registeruser(@RequestBody UserDetails userdetails,HttpSession httpSession) {
		if(userDao.getuser(userdetails.getUserid())==null)
		{
			userdetails.setStatus("R");
			userdetails.setIsonline("N");
			userDao.save(userdetails);
			userdetails.setErrormessage("You have Registered successfully,you have registered as"+userdetails.getRole());
			return new ResponseEntity<UserDetails>(userdetails,HttpStatus.OK);
		}else
		{
			userdetails.setErrorcode("404");
			userdetails.setErrormessage("User id already exists please try with new userid :"+ userdetails.getUserid());
			return new ResponseEntity<UserDetails>(userdetails,HttpStatus.OK);
		}

	}
	//this method is used to make an employee as admin using his userid
	@RequestMapping(value="makeadmin/{userid}",method=RequestMethod.POST)
	public ResponseEntity<UserDetails> makeadmin(@PathVariable("userid") String userid){
		userdetails=userDao.getuser(userid);
		if(userdetails==null)
		{
			userdetails=new UserDetails();//this line avoids null pointer exception
			userdetails.setErrorcode("400");
			userdetails.setErrormessage("user with this id does not exists");
			return new ResponseEntity<UserDetails>(userdetails,HttpStatus.OK);
		}
		userdetails.setRole("admin");
		userDao.update(userdetails);
		userdetails.setErrorcode("200");
		userdetails.setErrormessage("successfully employee is maded as admin"+userdetails.getUsername());
		return new ResponseEntity<UserDetails>(userdetails,HttpStatus.OK);
	}
	//private UserDetails updatestatus(String userid, char status,String reason)
	//{
		//userdetails=userdao.getuser(userid);
		
	*/

		/*//authentication
		@RequestMapping(value="/user/authenticate",method=RequestMethod.POST)
		public ResponseEntity<UserDetails> authenticateuser(@RequestBody UserDetails userdetails,HttpSession session)
		{
			log.debug("-->calling authenticate method");
			userdetails=userDao.authenticate(userdetails.getUsername(),userdetails.getPassword());
			if(userdetails==null)
			{
				log.debug("-->User does not exist");
				userdetails = new UserDetails();
		}
			else
			{
				userdetails.setErrorcode("200");
				log.debug("-->User exist with above credentials");
				session.setAttribute("loggegInUser",userdetails);
				session.setAttribute("loggedinuserid", userdetails.getUserid());
				//friendDAO.setOnLine(userdetails.getUsername());
				userdetails.setIsonline("y");
			}
			return new ResponseEntity<UserDetails>(userdetails,HttpStatus.OK);
		}*/
/*@RequestMapping(value="/users", method=RequestMethod.GET)
public ResponseEntity<List<UserDetails>> listAllUsers(){
	log.debug("-->Calling method listAllUsers");
	List<UserDetails> userdetails=userDao.list();
	if(userdetails.isEmpty()){
		return new ResponseEntity<List<UserDetails>>(HttpStatus.NO_CONTENT);
	}
	return new ResponseEntity<List<UserDetails>>(userdetails,HttpStatus.OK);
	
}*/


/*//to get user by user id
	@RequestMapping(value="/getuser/{userid}",method=RequestMethod.GET)
	public ResponseEntity<UserDetails> getuser(@PathVariable("userid")String userid)
	{
	log.debug("-->calling get method");
	UserDetails userdetails=userDao.getuser(userid);
	if(userdetails==null)
	{
		log.debug("-->User does not exist");
		userdetails = new UserDetails();
		userdetails.setErrorcode("404");
		userdetails.setErrormessage("User not found");
		return new ResponseEntity<UserDetails>(userdetails,HttpStatus.NOT_FOUND);
	}
	log.debug("-->User exist");
	return new ResponseEntity<UserDetails>(userdetails,HttpStatus.OK);
	}*/

/*//to create users
		@RequestMapping(value="/createusers/", method=RequestMethod.POST)
		public ResponseEntity<UserDetails> createusers(@RequestBody UserDetails userdetails){
			log.debug("-->Calling method createUsers");
			if(userDao.getuser(userdetails.getUserid())==null){
				log.debug("-->User created");
				userDao.save(userdetails);
				return new ResponseEntity<UserDetails>(userdetails,HttpStatus.OK);
			}
			log.debug("-->User already exist"+userdetails.getUserid());
			return new ResponseEntity<UserDetails>(userdetails,HttpStatus.OK);
			}*/