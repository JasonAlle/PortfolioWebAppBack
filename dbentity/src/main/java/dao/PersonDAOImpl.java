package dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.sql.SQLException;
import java.util.List;

import entity.Person;

@ApplicationScoped
public class PersonDAOImpl implements PersonDAO<Person> {

	@PersistenceContext(unitName = "person-pu")

	EntityManager entMan;

	@Override
	public Person find(int id) throws SQLException {

		return entMan.find(Person.class, id);
	}

	@Override
	public List<Person> findAll() throws SQLException {

		// return null;
		return entMan.createQuery("SELECT p FROM Person p", Person.class).getResultList();
	}

	@Override
	@Transactional
	public boolean insert(Person person) throws SQLException {
		System.out.println("DAOaaa is inserting!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!: " + person);
		try {
			System.out.println("DAO is inserting!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!: " + person);

			entMan.persist(person);
		} catch (EntityExistsException | IllegalArgumentException e) {
			System.out.println("DAO failed!");
			return false;
		}
		System.out.println("DAO Succeeded!");
		return true;
	}

	@Override
	@Transactional
	public boolean update(int idToChange, Person personInfo) throws SQLException {
		Person updatePerson = this.find(idToChange);
		if (updatePerson != null) {
			updatePerson.setAge(personInfo.getAge());
			updatePerson.setName(personInfo.getName());
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean deleteID(int id) throws SQLException {

		Person person = find(id);
		if (person != null) {
			try {
				entMan.remove(person);
			} catch (IllegalArgumentException e) {
				return false;
			}
			return true;
		}
		return false;

	}

}
