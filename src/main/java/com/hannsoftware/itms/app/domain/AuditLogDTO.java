package com.hannsoftware.itms.app.domain;

import java.io.Serializable;
import java.util.Date;

public class AuditLogDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1369016566687223445L;
	
	private Long id;
	
	private String action;
	
	private Date creationDate;
	
        private TicketDTO ticket;
	
	private UserDTO user;

	public AuditLogDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AuditLogDTO(String action, TicketDTO ticket, UserDTO user) {
		super();
		this.action = action;
		this.ticket = ticket;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
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
