package HibernateBasic6;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import org.hibernate.annotations.CollectionId;  //this is Hibernate specific, not JPA standard. So, later if we use this program outside of hibernate, we need to change it.
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;


/*
 * store collections in Hibernate
 */
@Entity     
public class Customer6 {
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
	@ElementCollection
	@JoinTable(name="customer_phone",
	           joinColumns=@JoinColumn(name="cust_id")
			)
//	@GenericGenerator(name = "hilo-gen", strategy = "hilo")  //"hilo" is not supported any more. See: http://stackoverflow.com/questions/33103355/hilo-generator-strategy-not-working
	@GenericGenerator(name = "sequence-gen", strategy = "sequence")
	@CollectionId(columns = { @Column(name="phone_id") }, generator = "sequence-gen", type = @Type(type="long"))
	private Collection<Phone> phoneList = new ArrayList<>();
	/*the whole thing of @CollectionId is about:
	 * 1. to add the index (or kind of order) and the primary key for the embedded object.
	 * 2. for this purpose, we have to use some data structures which support order or index(set or hashtable has not order).
	 * 3. "phone_id" will be the primary key in the phone collection
	 * 4. "hilo" is not supported any more. Need to use "sequence-gen"
	 */


	public Customer6() {	}  
	public Customer6(String firstName, String lastName, Set<Address> addressSet) {
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


	public Collection<Phone> getPhoneList() {
		return phoneList;
	}
	public void setPhoneList(Collection<Phone> phoneList) {
		this.phoneList = phoneList;
	}

}


