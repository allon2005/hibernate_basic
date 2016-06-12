package HibernateBasic3;

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
		Integer cust1 = manageCust.addCustomer3("Jones", "Hardin", "Salt lake,UT");

		factory.close();
	}

	//method to add a new customer3 into database, only allow a single record for each customer3
	public Integer addCustomer3(String fname, String lname, String address)
	{
		Integer customer3ID = null; 
		List customer3List = searchCustomer3(fname,lname);
		if(customer3List != null && customer3List.size()>0) //first check if this customer3 exists or not. If existing, just update it
		{
			updateCustomer3(((Customer3)customer3List.get(0)).getId(),address); //since only allow a single record for each customer3, only need to get(0).
		}
		else //if customer3 not existing, create a new record
		{
			Session session = factory.openSession(); 
			Transaction tx = null;  

			try{
				tx = session.beginTransaction();
				Customer3 cust = new Customer3(fname,lname,address);
				customer3ID = (Integer) session.save(cust);
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
		return customer3ID;
	}

	//method to search certain customer3
	public List searchCustomer3(String fname, String lname)
	{
		Session session = factory.openSession();
		List result = null; 
		if(session.get(Customer3.class, 0) !=null)  //first check if the Customer3 table (object) already exists or not in database
		{
			try{
				String hql = " FROM Customer3 WHERE firstName = :fname AND lastName = :lname"; //in HQL, need to use "Entity name" for both class and variable
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

	//method to update the address for a customer3
	public void updateCustomer3(Integer customer3ID, String address)
	{
		Session session = factory.openSession(); 
		Transaction tx = null;  

		try{
			tx = session.beginTransaction(); 
			Customer3 cust = session.get(Customer3.class, customer3ID); 
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

/*
 * String hql = " FROM Customer3 WHERE firstName = :fname AND lastName = :lname"; //in HQL, need to use "Entity name" for both class and variable

				Query query = session.createQuery(hql);

in this HQL, we are querying the object, not the table.
So, if we use @Table, or @Column to re_assign names to table and columns, we still need to use the "Entity name" for both the class and the column, not the re-assigned table name and column name.

However, if we query directly in the Mysql, we have to use the re-assigned table name and column name.!!!!


 * 
 * 
 * 
 *  */
