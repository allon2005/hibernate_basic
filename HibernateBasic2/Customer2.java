package HibernateBasic2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * Store customer using annotation
 */
@Entity     //if just using "@Entity, the class name Customer2 will be used as the table name.
public class Customer2 {
	@Id
	private int id;
	private String lastName; //if no special annotation, the column name will be "lastName"
	private String firstName; //if no special annotation, the column name will be "firsttName"
	private String address;

	public Customer2() {	}  //required
	/* //if without this default contructor, you will get an info:
	INFO: HHH000327: Error performing load command : org.hibernate.InstantiationException: No default constructor for entity:  : HibernateBasic2.Customer2

	 */
	public Customer2(String firstName, String lastName, String address) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = address;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}

/*requirements:
 * 1. variable as "private"
 * 2. getter and setter methods for each variable
 * 3. must have the default constructor (if another with-argument constructor present)
 * 4. Because Hibernate works on the concept of proxy, all the methods should not be "final" or private
 * 5. need an Id for primary key purpose
 */
