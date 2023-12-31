package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "person", schema = "portfoliowebapp")
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="person_id")
	private int person_id;

	@Column(name = "age")
	private int age;

	@Column(name = "name")
	private String name;
	
	@OneToMany(mappedBy="person")
	private Set<Tax> taxes;

	public Person() {
	}

	public Person(int id, int age, String name) {
		super();
		this.person_id = id;
		this.age = age;
		this.name = name;
	}

	public int getId() {
		return this.person_id;
	}

	public void setId(int id) {
		this.person_id = id;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public void swapInfo(Person personSwap)
	{
		this.name = personSwap.getName();
		this.age = personSwap.getAge();
		this.person_id = personSwap.getId();
		}

	@Override
	public String toString() {
		return "Person {person_id=" + person_id + ", age=" + age + ", name=" + name + "}";
	}

}
