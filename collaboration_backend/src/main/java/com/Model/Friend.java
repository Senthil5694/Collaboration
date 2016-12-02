package com.Model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Friend extends BaseDomain{
	
	@Id
	@GeneratedValue
	private int id;
	
	private String userid;
	
	private String friendid;
	
	private String status;
	
	private char is_online;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getFriendid() {
		return friendid;
	}

	public void setFriendid(String friendid) {
		this.friendid = friendid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public char getIs_online() {
		return is_online;
	}

	public void setIs_online(char is_online) {
		this.is_online = is_online;
	}
	
	
	

}
