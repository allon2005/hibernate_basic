package HibernateBasic7;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;


/*
 * demonstrate proxy concept
 */
@Entity     
public class Customer7 {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String lastName; 
	private String firstName;
	@ElementCollection  //with this annotation, the addressSet will be saved in a separate table (Customer6_addressSet) with the customer6_id mapped to each address. So, the records are connected.
	@JoinTable(name="customer_address",  // with this, the table is changed to "customer_address" instead of "Customer6_addressSet".
	           joinColumns=@JoinColumn(name="cust_id") //this is to change the "foreign key" name in the "customer_address" table
			)
	private Set<Address> addressSet = new HashSet<>(); //set has no order
	//if no annotation, will get error:
	/*Caused by: org.hibernate.MappingException: Could not determine type for: java.util.Set, at table: Customer6, for columns: [org.hibernate.mapping.Column(addressSet)]
	 */

	//@CollectionId(columns = { @Column }, generator = "", type = @Type ) this is not JPA standard. It is Hibernate specific. See comments in import part
	


	public Customer7() {	}  
	public Customer7(String firstName, String lastName, Set<Address> addressSet) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.addressSet = addressSet;
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

	public Set<Address> getAddressSet() {
		return addressSet;
	}
	public void setAddressSet(Set<Address> addressSet) {
		this.addressSet = addressSet;
	}
}


