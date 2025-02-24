package com.hannsoftware.itms.app.domain;

import java.io.Serializable;
import java.util.Date;

public class CommentsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -418519690823534223L;
	
	private Long id;
	
	private String comment;
	
	private Date creationDate;
	
	private TicketDTO ticket;
	
	private UserDTO user;
	
	public CommentsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CommentsDTO(String comment, TicketDTO ticket, UserDTO user) {
		super();
		this.comment = comment;
		this.ticket = ticket;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public TicketDTO getTicket() {
		return ticket;
	}

	public void setTicket(TicketDTO ticket) {
		this.ticket = ticket;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
	
}
