package com.Model;

import javax.persistence.Transient;

import org.springframework.stereotype.Component;

@Component("basedomain")
public class BaseDomain {
	@Transient
	public String errorcode;
	
	@Transient
	public String errormessage;

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public String getErrormessage() {
		return errormessage;
	}

	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}
	
}
