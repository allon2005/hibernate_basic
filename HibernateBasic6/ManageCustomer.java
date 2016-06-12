package HibernateBasic6;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


/*
 * store collections(ordered arrayList and unordered set) in Hibernate
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

		//ManageCustomer manageCust = new ManageCustomer();
		Address address1 = new Address();
		address1.setCity("salt lake city");
		address1.setState("UT");
		address1.setZipCode("12345");
		
		Address address2 = new Address();
		address2.setCity("LA");
		address2.setState("CA");
		address2.setZipCode("23456");
		
		Address address3 = new Address();
		address3.setCity("New York");
		address3.setState("NY");
		address3.setZipCode("34567");

		Customer6 cust6 = new Customer6();
		cust6.setFirstName("Jones");
		cust6.setLastName("Hardin");
		
		cust6.getAddressSet().add(address1);
		cust6.getAddressSet().add(address2);
		cust6.getAddressSet().add(address3);
		
		Phone phone1 = new Phone();
		phone1.setType("home");
		phone1.setNumber("123456-78");
		
		Phone phone2 = new Phone();
		phone2.setType("mobile");
		phone2.setNumber("23456-789");
		
		cust6.getPhoneList().add(phone1);
		cust6.getPhoneList().add(phone2);

		Session session = factory.openSession();
		Transaction tx = null;

		try
		{
			tx = session.beginTransaction();
			Integer customerID = (Integer)session.save(cust6);
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
