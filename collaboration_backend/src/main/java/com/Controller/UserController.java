package com.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Dao.UserDao;
import com.Model.UserDetails;

@RestController
public class UserController {
	
	@Autowired(required=true)
	private UserDao userDao;

	@Autowired
	private UserDetails userdetails;

	// this method will return list of userdetails from the database
	@RequestMapping(value = "/listusers", method = RequestMethod.GET)
	public ResponseEntity<List<UserDetails>> list() {
		List<UserDetails> userlist = userDao.list();
		if (userlist.isEmpty()) {
			userdetails.setErrorcode("404");
			userdetails.setErrormessage("users not available");

			userlist.add(userdetails);
		}
		return new ResponseEntity<List<UserDetails>>(userlist, HttpStatus.OK);

	}
	// this method will check whether the given userid exists or not
	@RequestMapping(value = "/user/{userid}", method = RequestMethod.GET)
	public ResponseEntity<UserDetails> get(@PathVariable(value = "userid") String userid) {
		userdetails = userDao.getuser(userid);

		if (userdetails == null) {
			userdetails = new UserDetails();
			userdetails.setErrorcode("404");
			userdetails.setErrormessage("user is not exists with this id"+userdetails.getUserid());
		}
		return new ResponseEntity<UserDetails>(userdetails, HttpStatus.OK);

	}

	// this method will validate whether the entered userid and password are
	// correct or not
	@RequestMapping(value = "/validateuser", method = RequestMethod.GET)
	public ResponseEntity<UserDetails> login(@RequestBody UserDetails userdetails, HttpSession httpSession) {
		//userdao.authenticate(userdetails.getUserid(),userdetails.getPassword());
		userdetails=userDao.authenticate(userdetails.getUserid(),userdetails.getPassword());

		if (userdetails == null) {
			userdetails = new UserDetails();
			userdetails.setErrorcode("404");
			userdetails.setErrormessage("Invalid credentials please try again");

		} else {
			if(userdetails.getStatus()=="R")
			{
				userdetails.setErrorcode("404");
				userdetails.setErrormessage("You are not approved to login please contact admin");
			}
			else{
				httpSession.setAttribute("loggedinuserid", userdetails.getUserid());
				httpSession.setAttribute("loggedinuser", userdetails);
				userdetails.setIsonline("Y");
				//frienddao.setonline(user.getid());
			}
			
		}
		return new ResponseEntity<UserDetails>(userdetails, HttpStatus.OK);
	}
//this method is used to logout from the session
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<UserDetails> logout(HttpSession httpSession) {
		// remove the attribute from http session
		// update the status as offline
		String loggedinuserid = (String) httpSession.getAttribute("loggedinuserid");
		userDao.getuser(loggedinuserid);
		userdetails.setIsonline("N");

		if (userDao.update(userdetails)) {
			userdetails.setErrorcode("200");
			userdetails.setErrormessage("Logout successfull");
		} else {
			userdetails.setErrorcode("404");
			userdetails.setErrormessage("problem occured while logging out");
		}
		httpSession.invalidate();
		return new ResponseEntity<UserDetails>(userdetails, HttpStatus.OK);

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
	@RequestMapping(value="/register",method=RequestMethod.POST)
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
		
	

}
