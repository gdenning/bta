package com.zerodes.bta.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllUsers", query = "select myUser from User myUser"),
		@NamedQuery(name = "findUserByNameContaining", query = "select myUser from User myUser where myUser.name like ?1"),
		@NamedQuery(name = "findUserByName", query = "select myUser from User myUser where myUser.name = ?1"),
		@NamedQuery(name = "findUserByEmail", query = "select myUser from User myUser where myUser.email = ?1"),
})
@Table(name = "TUser")
public class User {
	@Column(name = "UserId", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;

	@Column(name = "Email", nullable = false)
	private String email;

	@Column(name = "Password", nullable = false)
	private String password;
	
	@Transient
	private String unencryptedPassword;

	@Column(name = "Name", length = 50, nullable = false)
	private String name;

	@Column(name = "Description", length = 1000, nullable = false)
	private String description;

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getUserId() {
		return this.userId;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setUnencryptedPassword(String unencryptedPassword) {
		this.unencryptedPassword = unencryptedPassword;
	}

	public String getUnencryptedPassword() {
		return unencryptedPassword;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	/**
	 * Returns a textual representation of a bean.
	 */
	public String toString() {
		return new ToStringBuilder(this)
			.append("userId", userId)
			.append("email", email)
			.append("name", name)
			.append("description", description)
			.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(email)
			.append(name)
			.append(description)
			.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return new EqualsBuilder()
			.append(email, other.email)
			.append(name, other.name)
			.append(description, other.description)
			.isEquals();
	}

}
