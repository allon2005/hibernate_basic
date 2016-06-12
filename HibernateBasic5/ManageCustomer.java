package HibernateBasic5;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/*
 * embeded objects and AttributesOverride
 */

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
		Address address = new Address();
		address.setCity("salt lake city");
		address.setState("UT");
		address.setZipCode("12345");

		Address homeAddress = new Address();
		homeAddress.setCity("la");
		homeAddress.setState("CA");
		homeAddress.setZipCode("23456");

		Customer5 cust5 = new Customer5();
		cust5.setAddress(address);
		cust5.setHomeAddress(homeAddress);
		cust5.setFirstName("Jones");
		cust5.setLastName("Hardin");

		Session session = factory.openSession();
		Transaction tx = null;

		try
		{
			tx = session.beginTransaction();
			Integer customerID = (Integer)session.save(cust5);
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

		factory.close();
	}
}
