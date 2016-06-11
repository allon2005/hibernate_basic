package HibernateBasic1;
/*
 * Store customer using xml mapping file
 */

public class Customer {
	private int id;
	private String lastName;
	private String firstName;
	private String address;
	
	public Customer() {	}  //required!!!

	public Customer(String firstName, String lastName, String address) {
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
