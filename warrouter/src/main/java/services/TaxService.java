package services;

import java.sql.SQLException;

import java.util.List;
import dao.TaxDAOImpl;
import entity.Tax;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TaxService implements ObjectService<Tax> {

	@Inject
	TaxDAOImpl taxDAO;

	@Override
	public Tax find(int id) throws SQLException {
		return taxDAO.find(id);
	}

	@Override
	public List<Tax> findAll() throws SQLException {

		return taxDAO.findAll();
	}
	
	public List<Tax> findAllTaxesByPersonId(int personId) throws SQLException {

		return taxDAO.findAllTaxesByPersonId(personId);
	}

	@Override
	public boolean insert(Tax object) throws SQLException {
		System.out.println("Serviceeeee is inserting!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!: " + object);

		return taxDAO.insert(object);
	}

	@Override
	public boolean edit(int id, Tax object) throws SQLException {
		return taxDAO.update(id, object);
	}

	@Override
	public boolean deleteID(int id) throws SQLException {
		return taxDAO.deleteID(id);
	}

}
