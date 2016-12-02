package com.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.context.annotation.ComponentScan;

@Entity
@ComponentScan
public class Blog extends BaseDomain{
	
@Id
private String id;

private String title;

private String description;

private String userid;

private char status;

private String reason;

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
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

public String getUserid() {
	return userid;
}

public void setUserid(String userid) {
	this.userid = userid;
}

public char getStatus() {
	return status;
}

public void setStatus(char status) {
	this.status = status;
}

public String getReason() {
	return reason;
}

public void setReason(String reason) {
	this.reason = reason;
}




}
