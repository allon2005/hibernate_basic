package HibernateBasic8;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


/*
 * Entity mapping with @OneToOne
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

		Customer8 cust8 = new Customer8();
		cust8.setFirstName("Jones");
		cust8.setLastName("Hardin");
		
		Phone phone1 = new Phone();
		phone1.setType("home");
		phone1.setNumber("123456-78");

		cust8.setPhone(phone1);  //this is to associate the phone with the customer, which will use the "@OneToOne" strategy

		Session session = factory.openSession();
		Transaction tx = null;

		try
		{
			tx = session.beginTransaction();
			Integer customerID = (Integer)session.save(cust8);
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
