package com.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Friend {
	
	@Id
	@Column
	private int friendid;
	
	/*@ManyToOne
	@JoinColumn(name="userid",insertable=false,nullable=false,updatable=false)
	private UserDetails userdetails;
	
	private String isonline;*/

}
