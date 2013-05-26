package com.zerodes.bta.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.ForeignKey;

import com.zerodes.bta.enums.CategoryTypeEnum;

@Entity
@Table(name = "TCategory")
@NamedQueries({
	@NamedQuery(name = "findCategoriesByUser", query = "select cat from Category cat where user = ?1 order by type, name"),
	@NamedQuery(name = "findCategoryByName", query = "select cat from Category cat where user = ?1 and name = ?2")
})
public class Category {
	@Column(name = "CategoryId", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long categoryId;

	@ManyToOne
	@JoinColumns( { @JoinColumn(name = "UserId", referencedColumnName = "UserId", nullable = false) })
	@ForeignKey(name = "FK_Transaction_User")
	private User user;

	@Column(name = "Name", length = 255, nullable = false)
	private String name;
	
	@Column(name = "Type", length = 20, nullable = false)
	@Enumerated(EnumType.STRING)
	private CategoryTypeEnum type;
	
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

	public CategoryTypeEnum getType() {
		return type;
	}

	public void setType(CategoryTypeEnum type) {
		this.type = type;
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
