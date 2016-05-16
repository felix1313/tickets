package com.felix.tickets.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="users_events")
public class UserEvent implements Serializable{
	private static final long serialVersionUID = 2343610173032407177L;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "user_id", updatable = false, insertable = false, referencedColumnName = "id")
	// @PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "event_id", updatable = false, insertable = false, referencedColumnName = "id")
	private Event event;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
}

	