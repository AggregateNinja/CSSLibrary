package DAOS.IDAOS;

import java.sql.SQLException;

/**
 * Generic DAO interface. Made to replace DAOInterface.java, which required casting
 *  to/from serializable and didn't force calling code to handle exceptions.
 * 
 * @author TomR
 * @param <T> Generic representing the type of the implementing DAO class
 */
public interface IDAO<T>
{
    public enum SearchType
    {
        ANY,
        ACTIVE_ONLY,
        INACTIVE_ONLY
    }
    public T Insert(T obj) throws SQLException, IllegalArgumentException, NullPointerException;
    public T Update(T obj) throws SQLException, IllegalArgumentException, NullPointerException;
    public void Delete(T obj) throws SQLException, IllegalArgumentException, NullPointerException;
    public T GetById(Integer id) throws SQLException, IllegalArgumentException, NullPointerException;
}
