package services;



import java.sql.SQLException;

import java.util.List;

import dao.PersonDAOImpl;
import entity.Person;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PersonService implements ObjectService<Person> {

	@Inject
	PersonDAOImpl personDAO;

	@Override
	public Person find(int id)throws SQLException{
		System.out.println("Finding Person: " + id);
			return personDAO.find(id);
	}

	@Override
	public List<Person> findAll()throws SQLException {
		
		return personDAO.findAll();
	}

	@Override
	public boolean insert(Person object)throws SQLException {
		System.out.println("Serviceeeee is inserting!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!: " + object);

		return personDAO.insert(object);
	}

	@Override
	public boolean edit(int id, Person object)throws SQLException {
		return personDAO.update(id, object);
	}

	@Override
	public boolean deleteID(int id)throws SQLException {
		return personDAO.deleteID(id);
	}

}
