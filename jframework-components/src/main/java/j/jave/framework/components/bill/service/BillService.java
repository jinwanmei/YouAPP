/**
 * 
 */
package j.jave.framework.components.bill.service;

import j.jave.framework.components.bill.model.Bill;
import j.jave.framework.components.core.context.ServiceContext;
import j.jave.framework.components.core.exception.ServiceException;

import java.util.List;

/**
 * @author Administrator
 *
 */
public interface BillService {
	
	/**
	 * 
	 * @bill context 
	 * @bill user
	 * @throws ServiceException
	 */
	public void saveBill(ServiceContext context, Bill bill) throws ServiceException;
	
	
	/**
	 * 
	 * @bill context
	 * @bill user
	 * @throws ServiceException
	 */
	public void updateBill(ServiceContext context, Bill bill) throws ServiceException;
	
	/**
	 * make the record not available
	 * @bill context
	 * @bill id
	 */
	public void delete(ServiceContext context, String id);
	
	/**
	 * get one .
	 * @bill id
	 * @return
	 */
	public Bill getBillById(ServiceContext context, String id);
	
	public List<Bill> getBillsByPage(ServiceContext context, Bill bill) ;
	
	public List<Bill> getBillByUserName(ServiceContext context, String userName);
	
	
}
