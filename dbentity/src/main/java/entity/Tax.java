package entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tax", schema="portfoliowebapp")
public class Tax implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="tax_id")
	
	private int tax_id;


	@Column(name = "year")
	private int year;
	
	@Column(name = "amount")
	private int amount;

	@ManyToOne
	@JoinColumn(name="person_id", nullable=false)
	private Person person;

	public Tax() {
	}

	public Tax(int id, int amount, int year, Person person) {
		super();
		this.tax_id = id;
		this.year = year;
		this.amount = amount;
		this.person = person;
	}

	public int getId() {
		return this.tax_id;
	}

	public void setId(int id) {
		this.tax_id = id;
	}

	public int getTaxAmount() {
		return this.amount;
	}

	public void setTaxAmount(int amount) {
		this.amount = amount;
	}

	public int getTaxYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public Person getPerson() {
	return person;
	}
	public void setPerson(Person personToSet) {
		this.person = personToSet;
	}
	public void swapInfo(Tax taxSwap)
	{
		this.amount = taxSwap.getTaxAmount();
		this.year = taxSwap.getTaxYear();
		this.tax_id = taxSwap.getId();
	}

	@Override
	public String toString() {
		return "Tax {tax_id=" + tax_id + ", year=" + year + ", amount=" + amount + " person_id=" + person.getId() +"}";
	}

}
