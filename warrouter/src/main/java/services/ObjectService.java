package services;

import java.sql.SQLException;
import java.util.List;

public interface ObjectService<T> {
	
	T find(int id) throws SQLException;

	List<T> findAll() throws SQLException;

	boolean insert(T object) throws SQLException;

	boolean edit(int id, T object) throws SQLException;

	boolean deleteID(int id) throws SQLException;
}
