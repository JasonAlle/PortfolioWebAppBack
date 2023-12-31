package dao;



import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.sql.SQLException;
import java.util.List;

import entity.Tax;

@ApplicationScoped
public class TaxDAOImpl implements TaxDAO<Tax> {

	@PersistenceContext(unitName = "tax-pu")

	EntityManager entMan;

	@Override
	public Tax find(int id) throws SQLException {

		return entMan.find(Tax.class, id);
	}

	@Override
	public List<Tax> findAll() throws SQLException {
		//return null;
	    return entMan.createNamedQuery("SELECT t FROM Tax t", Tax.class).getResultList();
	}
	public List<Tax> findAllTaxesByPersonId(int personId) {
       // String jpql = "SELECT t FROM Tax t WHERE t.person.id = :personId";
		/*
		 * Query query = entMan.createQuery(jpql, Tax.class);
		 * query.setParameter("personId", personId);
		 */
       // return query.getResultList();
        return entMan.createQuery("SELECT t FROM Tax t WHERE t.person.person_id = :personId", Tax.class).setParameter("personId", personId).getResultList();
    }

	@Override
	@Transactional
	public boolean insert(Tax tax) throws SQLException {
		try {
			entMan.persist(tax);
		} catch (EntityExistsException | IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public boolean update(int idToChange, Tax taxInfo) throws SQLException {
		Tax updateTax = find(idToChange);
		if (updateTax != null) {
			updateTax.swapInfo(taxInfo);
			try {

				entMan.merge(updateTax);

			} catch (IllegalArgumentException e) {
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean deleteID(int id) throws SQLException {

		Tax tax = find(id);
		if (tax != null) {
			try {
				entMan.remove(tax);
			} catch (IllegalArgumentException e) {
				return false;
			}
			return true;
		}
		return false;

	}

}
