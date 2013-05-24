package com.zerodes.bta.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "TCategory")
public class Category {
	@Column(name = "CategoryId", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long categoryId;

	@ManyToOne
	@JoinColumns( { @JoinColumn(name = "UserId", referencedColumnName = "UserId", nullable = false) })
	@ForeignKey(name = "FK_Transaction_User")
	private User user;

	@Column(name = "Name", nullable = false)
	private String name;
	
	@Column(name = "CreditCardPayment", nullable = false)
	private boolean creditCardPayment;

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCreditCardPayment() {
		return creditCardPayment;
	}

	public void setCreditCardPayment(boolean creditCardPayment) {
		this.creditCardPayment = creditCardPayment;
	}

	public String toString() {
		return new ToStringBuilder(this)
			.append("categoryId", categoryId)
			.append("user", user)
			.append("name", name)
			.append("creditCardPayment", creditCardPayment)
			.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(user)
			.append(name)
			.append(creditCardPayment)
			.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Category)) {
			return false;
		}
		Category other = (Category) obj;
		return new EqualsBuilder()
			.append(user, other.user)
			.append(name, other.name)
			.append(creditCardPayment, other.creditCardPayment)
			.isEquals();
	}
}