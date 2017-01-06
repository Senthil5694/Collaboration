package com.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Dao.FriendDao;
import com.Model.Friend;
import com.Model.UserDetails;

@RestController
public class FriendController {
	
	private static final Logger log=LoggerFactory.getLogger(FriendController.class);
	
	@Autowired
	private FriendDao friendDao;
	
	@Autowired
	private Friend friend;
	
	@Autowired
	private HttpSession session;
	
	
	// this method is used to list all the users in the database
	@RequestMapping(value="/myFriends", method=RequestMethod.GET)
	public ResponseEntity<List<Friend>> listAllFriend(HttpSession session){
		log.debug("-->CALLING METHOD LIST ALL MYFRIENDS");
		
		UserDetails loggedInUser = (UserDetails) session.getAttribute("loggedInUser");
		System.out.println("CALLING METHOD LIST ALL THE USERS"+loggedInUser.getUsername());
		List<Friend> myfriends = friendDao.getmyfriends(loggedInUser.getUsername());
		return new ResponseEntity<List<Friend>> (myfriends,HttpStatus.OK);
	}
	
	//this method is used to send friend request to another user
	@RequestMapping(value="/addFriend/{friendid}", method=RequestMethod.GET)
	public ResponseEntity<Friend> sendFriendRequest(@PathVariable("friendid")String friendid,HttpSession session)
	{
		log.debug("-->CALLING METHOD SEND FRIEND REQUEST");
		String loggedInUserid = (String) session.getAttribute("loggedInUserId");
		friend.setUserid(loggedInUserid);
		friend.setFriendid(friendid);
		friend.setStatus("N");
		friend.setIsOnline('N');
		friendDao.save(friend);
		return new ResponseEntity<Friend>(friend,HttpStatus.OK);
	}
	
	//this method is used to unfriend a friend using friendid
	@RequestMapping(value="/unfriend/{friendid}", method=RequestMethod.GET)
	public ResponseEntity<Friend> unfriend(@PathVariable("friendid")String friendid,HttpSession session)
	{
		log.debug("-->CALLING METHOD UNFRIEND");
		UserDetails loggedInUser = (UserDetails) session.getAttribute("loggedInUser");	
		friend.setUserid(loggedInUser.getUserid());
		friend.setFriendid(friendid);
		friend.setStatus("R");
		friendDao.saveOrUpdate(friend);
		return new ResponseEntity<Friend>(friend,HttpStatus.OK);
	}
	
	//this method is used to accept a friend request
	@RequestMapping(value="/acceptFriend/{id}", method=RequestMethod.GET)
	public ResponseEntity<Friend> acceptFriend(@PathVariable("id")String id,HttpSession session)
	{
		log.debug("-->CALLING METHOD ACCEPT FRIEND");
		friendDao.setStatusAccept(id);
		return new ResponseEntity<Friend> (friend,HttpStatus.OK);
	}
	
	//this method is used to reject a friend request
	@RequestMapping(value="/rejectFriend/{id}", method=RequestMethod.GET)
	public ResponseEntity<Friend> rejectFriend(@PathVariable("id")String id,HttpSession session)
	{
		log.debug("-->CALLING METHOD REJECT FRIEND");
		friendDao.setStatusReject(id);
		return new ResponseEntity<Friend>(friend,HttpStatus.OK);
	}
	
	//this method is used to view the friend request of the user
	@RequestMapping(value="/getmyfriendRequest", method=RequestMethod.GET)
	public ResponseEntity<List<Friend>> getFriendRequest(HttpSession session){
		log.debug("-->CALLING METHOD LIST MY FRIEND REQUEST");
		UserDetails loggedInUser = (UserDetails) session.getAttribute("loggedInUser");		
		System.out.println("Calling method listAllFriends"+loggedInUser.getUsername());
		List<Friend> myfriends = friendDao.getNewFriendrequest(loggedInUser.getUsername());
		return new ResponseEntity<List<Friend>> (myfriends,HttpStatus.OK);
		
	}
	
	//this method is used to view the friends list of the user
	@RequestMapping(value="/myfriends/{id}", method=RequestMethod.GET)
	public ResponseEntity<List<Friend>> getmyFriendsTemp(@PathVariable("id")String id){
		List<Friend> myfriends = friendDao.getmyfriends(id);
		return new ResponseEntity<List<Friend>> (myfriends,HttpStatus.OK);
	}
	
	/*private void updateRequest(String friendid,String status)
	{
		String LoggedInUserId=(String) session.getAttribute("LoggedInUserId");
		friend.setUserid(LoggedInUserId);
		friend.setFriendid(friendid);
		friend.setStatus(status);
		friendDao.saveOrUpdate(friend);
	}*/
		
	}


