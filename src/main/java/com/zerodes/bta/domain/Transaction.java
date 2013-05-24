package com.zerodes.bta.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "TTransaction")
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "TransactionId", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long transactionId;
	
	@ManyToOne
	@JoinColumns( { @JoinColumn(name = "UserId", referencedColumnName = "UserId", nullable = false) })
	@ForeignKey(name = "FK_Transaction_User")
	private User user;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TransactionDate", nullable = false)
	private Date transactionDate;

	@Column(name = "Amount", scale = 2, precision = 10, nullable = false)
	private double amount;

	@Column(name = "Description", length = 255, nullable = false)
	private String description;

	@Column(name = "Vendor", length = 255, nullable = false)
	private String vendor;
	
	@Column(name = "ImportSource", length = 255, nullable = false)
	private String importSource;
	
	@Column(name = "Ignore", nullable = false)
	private boolean ignore;

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreateDate() {
		return transactionDate;
	}

	public void setCreateDate(Date createDate) {
		this.transactionDate = createDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getImportSource() {
		return importSource;
	}

	public void setImportSource(String importSource) {
		this.importSource = importSource;
	}

	public boolean isIgnore() {
		return ignore;
	}

	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}

	public String toString() {
		return new ToStringBuilder(this)
			.append("transactionId", transactionId)
			.append("user", user)
			.append("transactionDate", transactionDate)
			.append("amount", amount)
			.append("description", description)
			.append("vendor", vendor)
			.append("importSource", importSource)
			.append("ignore", ignore)
			.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(user)
			.append(transactionDate)
			.append(amount)
			.append(description)
			.append(vendor)
			.append(importSource)
			.append(ignore)
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
		if (!(obj instanceof Transaction)) {
			return false;
		}
		Transaction other = (Transaction) obj;
		return new EqualsBuilder()
			.append(user, other.user)
			.append(transactionDate, other.transactionDate)
			.append(amount, other.amount)
			.append(description, other.description)
			.append(vendor, other.vendor)
			.append(importSource, other.importSource)
			.append(ignore, other.ignore)
			.isEquals();
	}
}
