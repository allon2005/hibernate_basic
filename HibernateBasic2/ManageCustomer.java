package HibernateBasic2;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


public class ManageCustomer {
	private static SessionFactory factory;  
	public static void main(String[] args) {

		try
		{
			factory = new Configuration().configure().buildSessionFactory();
		}
		catch(Throwable e)
		{
			System.out.println("error in creating sessionFactory :" + e.toString());
			throw new ExceptionInInitializerError(e); 
		}

		ManageCustomer manageCust = new ManageCustomer();
		Integer cust1 = manageCust.addCustomer2("Jones", "Hardin", "Salt lake,UT");
		
		factory.close();
	}
	
	//method to add a new customer2 into database, only allow a single record for each customer2
	public Integer addCustomer2(String fname, String lname, String address)
	{
		Integer customer2ID = null; 
		List customer2List = searchCustomer2(fname,lname);
		if(customer2List != null && customer2List.size()>0) //first check if this customer2 exists or not. If existing, just update it
		{
			updateCustomer2(((Customer2)customer2List.get(0)).getId(),address); //since only allow a single record for each customer2, only need to get(0).
		}
		else //if customer2 not existing, create a new record
		{
			Session session = factory.openSession(); 
			Transaction tx = null;  

			try{
				tx = session.beginTransaction();
				Customer2 cust = new Customer2(fname,lname,address);
				customer2ID = (Integer) session.save(cust);
				tx.commit();
			}
			catch(HibernateException e)
			{
				if(tx !=null) //if we have an exception and the transaction is not null, we must roll back. Otherwise, we will get "dirty write".
				{
					tx.rollback();
				}
				e.printStackTrace();
			}
			finally
			{
				session.close();
			}
		}
		return customer2ID;
	}

	//method to search certain customer2
	public List searchCustomer2(String fname, String lname)
	{
		Session session = factory.openSession();
		List result = null; 
		if(session.get(Customer2.class, 0) !=null)  //first check if the Customer2 table (object) already exists or not in database
		{
			try{
				String hql = " FROM Customer2 WHERE firstName = :fname AND lastName = :lname";  
				Query query = session.createQuery(hql);
				query.setParameter("fname", fname);
				query.setParameter("lname", lname);
				result = query.getResultList(); 
				session.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return result; 
	}

	//method to update the address for a customer2
	public void updateCustomer2(Integer customer2ID, String address)
	{
		Session session = factory.openSession(); 
		Transaction tx = null;  

		try{
			tx = session.beginTransaction(); 
			Customer2 cust = session.get(Customer2.class, customer2ID); 
			cust.setAddress(address);
			session.update(cust);
			tx.commit();
		}
		catch(HibernateException e)
		{
			if(tx !=null) 
			{
				tx.rollback();
			}
			e.printStackTrace();
		}
		finally
		{
			session.close(); 
		}

	}

}
