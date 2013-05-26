package com.zerodes.bta.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

@Entity
@Table(name = "TCategoryAssignment")
@NamedQueries({
	@NamedQuery(name = "findByUser", query = "select ca from CategoryAssignment ca where ca.user = ?1"),
	@NamedQuery(name = "findByVendorAndDescription", query = "select ca from CategoryAssignment ca where ca.user = ?1 and ca.vendor = ?2 and ca.description = ?3")
})
public class CategoryAssignment {
	@Column(name = "CategoryAssignmentId", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long categoryAssignmentId;

	@ManyToOne
	@JoinColumns( { @JoinColumn(name = "UserId", referencedColumnName = "UserId", nullable = false) })
	@ForeignKey(name = "FK_Transaction_User")
	private User user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns( { @JoinColumn(name = "CategoryId", referencedColumnName = "CategoryId", nullable = false) })
	@ForeignKey(name = "FK_VendorCategory_Category")
	private Category category;

	@Column(name = "Vendor", length = 255)
	private String vendor;

	@Column(name = "Description", length = 255)
	private String description;

	public long getCategoryAssignmentId() {
		return categoryAssignmentId;
	}

	public void setCategoryAssignmentId(long categoryAssignmentId) {
		this.categoryAssignmentId = categoryAssignmentId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
		return new ToStringBuilder(this)
			.append("categoryAssignmentId", categoryAssignmentId)
			.append("user", user)
			.append("category", category)
			.append("vendor", vendor)
			.append("description", description)
			.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(user)
			.append(category)
			.append(vendor)
			.append(description)
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
		if (!(obj instanceof CategoryAssignment)) {
			return false;
		}
		CategoryAssignment other = (CategoryAssignment) obj;
		return new EqualsBuilder()
			.append(user, other.user)
			.append(category, other.category)
			.append(vendor, other.vendor)
			.append(description, other.description)
			.isEquals();
	}
}
