package com.felix.tickets.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="events")
public class Event implements Serializable{

	private static final long serialVersionUID = 4516265777031175647L;
	private Integer id;
	private String name;
	private String url;
	private Boolean tickets;
	private Date time;
	private int price_low;
	private int price_high;
	private String location;

	@Column(name="location")
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name="date", nullable = false)
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable = false,unique = true)
	public Integer getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "Event [id=" + id + ", name=" + name + ", url=" + url + ", tickets=" + tickets + ", time=" + time + "]";
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="url")
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name="tickets")
	public Boolean getTickets() {
		return tickets;
	}
	
	public void setTickets(Boolean tickets) {
		this.tickets = tickets;
	}

	@Column(name="price_low")
	public int getPrice_low() {
		return price_low;
	}

	@Column(name="price_high")
	public void setPrice_low(int price_low) {
		this.price_low = price_low;
	}
	
	public int getPrice_high() {
		return price_high;
	}

	public void setPrice_high(int price_high) {
		this.price_high = price_high;
	}
}
