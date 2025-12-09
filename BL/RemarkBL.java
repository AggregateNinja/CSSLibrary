
package BL;

import DAOS.RemarkCategoryDAO;
import DAOS.RemarkDAO;
import DAOS.RemarkTypeDAO;
import DOS.RemarkCategory;
import DOS.RemarkType;
import DOS.Remarks;
import java.sql.SQLException;

public class RemarkBL
{
    /**
     * Return the string representing the reserved system name of the remark
     *  category for the remark Id provided. If the remark does not have a type
     *  or a category, returns [NULL].
     *  If a remark for the supplied Id doesn't exist, throws a SQLException
     * @param remarkId
     * @return 
     */
    public static String getCategorySystemNameForRemarkId(Integer remarkId)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (remarkId == null)
        {
            throw new IllegalArgumentException("RemarkBL::getCategorySystemNameForRemarkId:"
                    + " Received a [NULL] remarkId integer argument");
        }
        
        Remarks remark = null;
        try
        {
            RemarkDAO rdao = new RemarkDAO();
            remark = rdao.GetRemarkFromID(remarkId);
            if (remark == null || remark.getIdremarks() == null || remark.getIdremarks() <= 0)
            {
                throw new SQLException("Could not retrieve remark for supplied remarkId: " + remarkId.toString());
            }
        }
        catch (SQLException ex)
        {
            System.out.println("RemarkBL::getCategorySystemNameForRemarkId: Exception:"
                    + (ex.getMessage() == null ? "[NULL]": ex.getMessage()));
            throw ex;
        }
        
        try
        {
            RemarkTypeDAO rtdao = new RemarkTypeDAO();
            RemarkType remarkType = rtdao.GetRemarkTypeById(remark.getRemarkType());
            if (remarkType != null && remarkType.getRemarkCategoryId() != null &&
                    remarkType.getRemarkCategoryId() > 0)
            {
                RemarkCategory remarkCategory = RemarkCategoryDAO.get(remarkType.getRemarkCategoryId());
                if (remarkCategory != null && remarkCategory.getSystemName() != null &&
                        remarkCategory.getSystemName().isEmpty() == false)
                {
                    return remarkCategory.getSystemName();
                }
            }
        }
        catch (Exception ex)
        {
            System.out.println("Couldn't retrieve remark type data for remarkId: " + remarkId.toString());
        }
        
        // The provided remark exists, but it either doesn't have a remark type assigned,
        // or the remark type isn't assigned a category, or the category doesn't
        // have a system name defined.
        return null;
    }
}
