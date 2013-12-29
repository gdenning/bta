package com.zerodes.bta.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "TTransaction")
@NamedQueries({
	@NamedQuery(name = "findTransactionsByUserAndYear", query = "select txn from Transaction txn where user = ?1 and transactionYear = ?2 order by transactionMonth, transactionDay"),
	@NamedQuery(name = "findTransactionsByUserAndMonth", query = "select txn from Transaction txn where user = ?1 and transactionYear = ?2 and transactionMonth = ?3 order by transactionDay"),
	@NamedQuery(name = "findTransactionsByUserAndMonthAndCategory", query = "select txn from Transaction txn where user = ?1 and transactionYear = ?2 and transactionMonth = ?3 and txn.derivedCategory.name = ?4 order by transactionMonth, transactionDay"),
	@NamedQuery(name = "findTransactionsByUserAndMonthAndUnassignedCategory", query = "select txn from Transaction txn where user = ?1 and transactionYear = ?2 and transactionMonth = ?3 and txn.derivedCategory = null order by transactionMonth, transactionDay"),
	@NamedQuery(name = "findTransactionsByUserAndDescriptionAndVendor", query = "select txn from Transaction txn where user = ?1 and description = ?2 and vendor = ?3"),
	@NamedQuery(name = "findUniqueDescriptionVendorCombinations", query = "select distinct txn.description, txn.vendor from Transaction txn where user = ?1"),
	@NamedQuery(name = "findExistingTransaction", query = "select txn from Transaction txn where user = ?1 and transactionYear = ?2 and transactionMonth = ?3 and transactionDay = ?4 and amount = ?5 and description = ?6 and vendor = ?7")
})
public class Transaction {
	@Column(name = "TransactionId", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long transactionId;
	
	@ManyToOne
	@JoinColumn(name = "UserId", referencedColumnName = "UserId", nullable = false)
	@ForeignKey(name = "FK_Transaction_User")
	private User user;

	@Column(name = "TransactionYear", precision = 4, nullable = false)
	private int transactionYear;

	@Column(name = "TransactionMonth", precision = 2, nullable = false)
	private int transactionMonth;

	@Column(name = "TransactionDay", precision = 2, nullable = false)
	private int transactionDay;

	@Column(name = "Amount", scale = 2, precision = 10, nullable = false)
	private double amount;

	@Column(name = "Description", length = 255, nullable = false)
	private String description;

	@Column(name = "Vendor", length = 255, nullable = false)
	private String vendor;

	@ManyToOne
	@JoinColumn(name = "DerivedCategoryID", referencedColumnName = "CategoryID", nullable = true)
	@ForeignKey(name = "FK_Transaction_Category")
	private Category derivedCategory;
	
	@Column(name = "ImportSource", length = 255, nullable = false)
	private String importSource;
	
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

	public int getTransactionYear() {
		return transactionYear;
	}

	public void setTransactionYear(int transactionYear) {
		this.transactionYear = transactionYear;
	}

	public int getTransactionMonth() {
		return transactionMonth;
	}

	public void setTransactionMonth(int transactionMonth) {
		this.transactionMonth = transactionMonth;
	}

	public int getTransactionDay() {
		return transactionDay;
	}

	public void setTransactionDay(int transactionDay) {
		this.transactionDay = transactionDay;
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

	public Category getDerivedCategory() {
		return derivedCategory;
	}

	public void setDerivedCategory(Category derivedCategory) {
		this.derivedCategory = derivedCategory;
	}

	public String getImportSource() {
		return importSource;
	}

	public void setImportSource(String importSource) {
		this.importSource = importSource;
	}

	public String toString() {
		return new ToStringBuilder(this)
			.append("transactionId", transactionId)
			.append("user", user)
			.append("transactionYear", transactionYear)
			.append("transactionMonth", transactionMonth)
			.append("transactionDay", transactionDay)
			.append("amount", amount)
			.append("description", description)
			.append("vendor", vendor)
			.append("derivedCategory", derivedCategory)
			.append("importSource", importSource)
			.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(user)
			.append(transactionYear)
			.append(transactionMonth)
			.append(transactionDay)
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
			.append(user, other.user)
			.append(transactionYear, other.transactionYear)
			.append(transactionMonth, other.transactionMonth)
			.append(transactionDay, other.transactionDay)
			.append(amount, other.amount)
			.append(description, other.description)
			.append(vendor, other.vendor)
			.isEquals();
	}
}
