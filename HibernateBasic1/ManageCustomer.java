package HibernateBasic1;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class ManageCustomer {
	private static SessionFactory factory;  //this is a class level variable since it is heavyweight, we should create it only once
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
		Integer cust1 = manageCust.addCustomer("Mike", "Henson", "la,CA");
		Integer cust2 = manageCust.addCustomer("Derick", "Dustin", "Denver,CO");
		Integer cust3 = manageCust.addCustomer("Derick", "Dustin", "Spring,CO");
		
		factory.close();
	}
	
	//method to add a new customer into database, only allow a single record for each customer
	public Integer addCustomer(String fname, String lname, String address)
	{
		Integer customerID = null; // save(..) return Integer
		List customerList = searchCustomer(fname,lname);
		if(customerList != null && customerList.size()>0) //first check if this customer exists or not. If existing, just update it
		{
			updateCustomer(((Customer)customerList.get(0)).getId(),address); //since only allow a single record for each customer, only need to get(0).
		}
		else //if customer not existing, create a new record
		{
			Session session = factory.openSession(); //we open a session only when needed
			Transaction tx = null;  //each save, update, or delete is an transaction

			try{
				tx = session.beginTransaction(); //start the transaction;
				Customer cust = new Customer(fname,lname,address); //create customer persistent object using the other constructor
				customerID = (Integer) session.save(cust); //customerID is the primary key
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
				session.close(); //we must close the session when it is done. We should not leave it open for long time since it is not thread safe
			}
		}
		return customerID;
	}

	//method to search certain customer
	public List searchCustomer(String fname, String lname)
	{
		Session session = factory.openSession();
		//Transaction tx = null;  //no transaction needed since we only search the database
		List result = null; 
		if(session.get(HibernateBasic1.Customer.class, 1) !=null)  //first check if the Customer table (object) already exists or not in database
		{
			try{
				String hql = " FROM Customer WHERE first_name = :fname AND last_name = :lname"; //"Customer" is the entity name (not the table name!!!) here for hql. It is case-sensitive. 
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

	//method to update the address for a customer
	public void updateCustomer(Integer customerID, String address)
	{
		Session session = factory.openSession(); 
		Transaction tx = null;  

		try{
			tx = session.beginTransaction(); 
			Customer cust = session.get(Customer.class, customerID); //need to use the primary key (customerID) to retrieve the customer object in the database for update
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
			session.close(); //we must close the session when it is done. We should not leave it open for long time since it is not thread safe
		}

	}

}
