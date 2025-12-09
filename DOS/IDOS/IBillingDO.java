
package DOS.IDOS;

/**
 * To use the BillingLogDAO, a "loggable" object must be able to return
 *  the table it is written to.
 */
public interface IBillingDO
{
    String getTableName();
}
