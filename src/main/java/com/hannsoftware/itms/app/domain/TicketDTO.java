package com.hannsoftware.itms.app.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TicketDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 439819622854035112L;
	
	private Long id;
	
	private String title;
	
	private String description;
	
	private Date creationDate;
	
	private String priority;
	
	private String category;
	
	private String status;
		
	private List<CommentsDTO> comments;

	public TicketDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TicketDTO(String title, String description, String priority, String category) {
		super();
		this.title = title;
		this.description = description;
		this.priority = priority;
		this.category = category;
	}

    public TicketDTO(Long id, String title, String description, Date creationDate, String priority, String category, String status, List<CommentsDTO> comments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.priority = priority;
        this.category = category;
        this.status = status;
        this.comments = comments;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<CommentsDTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentsDTO> comments) {
		this.comments = comments;
	}

}