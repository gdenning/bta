package com.zerodes.bta.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "TTransaction")
//@NamedQueries({
//	@NamedQuery(name = "findActivitiesByUserId", query = "select myActivity from Activity myActivity join myActivity.user myUser where myUser.userId = ?1 order by myActivity.createDate DESC")
//})
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;

    @Column(name = "TransactionID", nullable = false)
	@Basic
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long transactionId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TransactionDate", nullable = false)
	@Basic
	private Date transactionDate;

	@Column(name = "Amount", scale = 2, precision = 10, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private double amount;

	@Column(name = "Description", length = 255, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String description;

	@Column(name = "Vendor", length = 255, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String vendor;

//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumns( { @JoinColumn(name = "UserID", referencedColumnName = "UserID", nullable = false) })
//	@ForeignKey(name = "FK_Activity_User")
//	private User user;

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
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

	public String toString() {
		return new ToStringBuilder(this)
			.append("transactionId", transactionId)
			.append("transactionDate", transactionDate)
			.append("amount", amount)
			.append("description", description)
			.append("vendor", vendor)
			.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(transactionDate)
			.append(amount)
			.append(description)
			.append(vendor)
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
			.append(transactionDate, other.transactionDate)
			.append(amount, other.amount)
			.append(description, other.description)
			.append(vendor, other.vendor)
			.isEquals();
	}
}
