package ca.gatin.todoapi.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author RGatin
 * @since 10-Oct-2015
 * 
 * This class represents the Account record in the Database
 *
 */
@XmlRootElement
public class Account {
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private boolean isActive;
	private Date dateCreated;
	private Date dateLastModified;

	// required for JAXB
	public Account() {}

	public Account(int id, String firstName, String lastName, String email, String password, boolean isActive, Date dateCreated, Date dateLastModified) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.setActive(isActive);
		this.dateCreated = dateCreated;
		this.dateLastModified = dateLastModified;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateLastModified() {
		return dateLastModified;
	}

	public void setDateLastModified(Date dateLastModified) {
		this.dateLastModified = dateLastModified;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password=" + password + ", isActive=" + isActive + ", dateCreated=" + dateCreated + ", dateLastModified=" + dateLastModified + "]";
	}
	
}
