package com.Model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Forum extends BaseDomain {

	@Id
	private String id;
	public  Forum()
	{
		this.id="FORUM" + UUID.randomUUID().toString().substring(24).toUpperCase();
	}
	
	private String title;
	private String description;
	private String 	reason ;

	private String userid;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
		public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
}
