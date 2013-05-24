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
@Table(name = "TVendorCategory")
@NamedQueries({
	@NamedQuery(name = "findCategoryForVendor", query = "select category from VendorCategory vc where vc.vendor = ?1")
})
public class VendorCategory {
	@Column(name = "VendorCategoryId", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long vendorCategoryId;

	@ManyToOne
	@JoinColumns( { @JoinColumn(name = "UserId", referencedColumnName = "UserId", nullable = false) })
	@ForeignKey(name = "FK_Transaction_User")
	private User user;

	@Column(name = "Vendor")
	private String vendor;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns( { @JoinColumn(name = "CategoryId", referencedColumnName = "CategoryId", nullable = false) })
	@ForeignKey(name = "FK_VendorCategory_Category")
	private Category category;

	public long getVendorCategoryId() {
		return vendorCategoryId;
	}

	public void setVendorCategoryId(long vendorCategoryId) {
		this.vendorCategoryId = vendorCategoryId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String toString() {
		return new ToStringBuilder(this)
			.append("vendorCategoryId", vendorCategoryId)
			.append("vendor", vendor)
			.append("category", category)
			.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(vendor)
			.append(category)
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
		if (!(obj instanceof VendorCategory)) {
			return false;
		}
		VendorCategory other = (VendorCategory) obj;
		return new EqualsBuilder()
			.append(vendor, other.vendor)
			.append(category, other.category)
			.isEquals();
	}
}
