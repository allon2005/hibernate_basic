package HibernateBasic4;

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
		String cust1 = manageCust.addCustomer4("Jones", "Hardin", "abc@def.com");
		
		factory.close();
	}
	
	//method to add a new customer4 into database, only allow a single record for each customer4
	public String addCustomer4(String fname, String lname, String address)
	{
		String customer4ID = null; 
		List customer4List = searchCustomer4(fname,lname);
		if(customer4List != null && customer4List.size()>0) //first check if this customer4 exists or not. If existing, just update it
		{
			updateCustomer4(((Customer4)customer4List.get(0)).getId(),address); //since only allow a single record for each customer4, only need to get(0).
		}
		else //if customer4 not existing, create a new record
		{
			Session session = factory.openSession(); 
			Transaction tx = null;  

			try{
				tx = session.beginTransaction();
				Customer4 cust = new Customer4(fname,lname,address);
				customer4ID = (String) session.save(cust);
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
		return customer4ID;
	}

	//method to search certain customer4
	public List searchCustomer4(String fname, String lname)
	{
		Session session = factory.openSession();
		List result = null; 
		if(session.get(Customer4.class, "") !=null)  //first check if the Customer4 table (object) already exists or not in database
		{
			try{
				String hql = " FROM Customer4 WHERE firstName = :fname AND lastName = :lname";  //in HQL, from "Entity name", not table name. The "field name" is also the "variable name", not the table field name. 
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

	//method to update the address for a customer4
	public void updateCustomer4(Integer customer4ID, String email)
	{
		Session session = factory.openSession(); 
		Transaction tx = null;  

		try{
			tx = session.beginTransaction(); 
			Customer4 cust = session.get(Customer4.class, customer4ID); 
			cust.setEmail(email);
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
