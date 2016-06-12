package HibernateBasic7;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


/*
 * demonstrate proxy concept
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

		Customer7 cust6 = new Customer7();
		cust6.setFirstName("Jones");
		cust6.setLastName("Hardin");

		cust6.getAddressSet().add(address1);
		cust6.getAddressSet().add(address2);
		cust6.getAddressSet().add(address3);

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

		/*lay initialization: 
		 * 1. customer have both simple variables such as lname and fname, and the embeded AddressSet. The AddressSet 
		 * can be considered as a 2nd-level object associated with the customer.
		 * 
		 * 2. When we first fetch a customer, by default, we only get the 1st-level objects such as lname/fname. The 2nd-level 
		 * data addressSet is not included at this point. === here, a proxy Customer7 class is created.
		 * 
		 * 3. When we call "cust7.getAddressSet()", hibernate will run another query to get the 2nd-level data (addressSet)
		 */
		Session session2 = factory.openSession();
		Customer7 cust7 = (Customer7)session2.get(Customer7.class, 1);
		
		//Hibernate: select customer7x0_.id as id1_5_0_, customer7x0_.firstName as firstNam2_5_0_, customer7x0_.lastName as lastName3_5_0_ from Customer7 customer7x0_ where customer7x0_.id=?
		//we can see that the address info is not included. So, if we do not need to access address, we will not fetch it		

		Set<Address> test = cust7.getAddressSet();
		//	Hibernate: select addressset0_.cust_id as cust_id1_6_0_, addressset0_.cust_city as cust_cit2_6_0_, addressset0_.cust_state as cust_sta3_6_0_, addressset0_.zipCode as zipCode4_6_0_ from customer_address addressset0_ where addressset0_.cust_id=?

		for(Iterator<Address> it = test.iterator(); it.hasNext();)
		{
			Address addr = it.next();
			System.out.println("the address is :" + addr.getCity() + "," + addr.getState() + "," + addr.getZipCode());
		}

		session2.close();
		
		
		Session session3 = factory.openSession();
		Customer7 cust8 = (Customer7)session3.get(Customer7.class, 1);
		session3.close(); // now close the session
		System.out.println("After session closed: the customer's first name is [" +cust8.getFirstName() +"]"); // we can still get the result from the 1st-level data

		//however, after session3 closed, we wil not be able to access the 2nd-level data. Get error:
		/*Exception in thread "main" org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: HibernateBasic7.Customer7.addressSet, could not initialize proxy - no Session
		 * 
		 */
		
		Set<Address> test1 = cust8.getAddressSet();
		for(Iterator<Address> it = test1.iterator(); it.hasNext();)
		{
			Address addr = it.next();
			System.out.println("the address is :" + addr.getCity() + "," + addr.getState() + "," + addr.getZipCode());
		}

		session3.close();

		factory.close();
	}
}
/*the default data fetch for the 2nd-level data is "lazy". We can re-configure it for each individual 2nd-level data at where "@ElementCollection"
 * used. For example here, in Customer7.java:
 * @ElementCollection (fetch=FetchType.EAGER) 
	@JoinTable(name="customer_address", 
	           joinColumns=@JoinColumn(name="cust_id") 
			)
	private Set<Address> addressSet = new HashSet<>(); 
 * 
 * 
 */
