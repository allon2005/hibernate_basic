package HibernateBasic3;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * Store customer using annotation, change the table name, column_name
 */
@Entity    //if just using "@Entity, the class name Customer2 will be used as the table name.
//@Entity(name="CustomerInfo")   //if using the "name" attribute, we are change both the Entity name and the table name for this class. All info will be saved into "CustomerInfo"
@Table(name="customerInfo1")  //if we keep "@Entity" and add "@Table", the Entity name will still be the class name. However, the table name will be changed to "CustomerInfo".
// so, in HQL, "FROM Customer3" is correct. But "From customerInfo1" is wrong.
//However, when we query in mysql, we need to use "select * from customerinfo1"!!!
//!!!HQL always takes the Entity name

public class Customer3 {
	@Id
	private int id;
	@Column(name="last_name")
	private String lastName;
	@Column(name="first_name")
	private String firstName; 
	private String address;

	public Customer3() {	}  //required
	/* //if without this default contructor, you will get an info:
	INFO: HHH000327: Error performing load command : org.hibernate.InstantiationException: No default constructor for entity:  : HibernateBasic2.Customer2

	 */
	public Customer3(String firstName, String lastName, String address) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = address;
	}
	@Id
	@Column(name="customer_id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="lname")  //we can also put the @Column at the getter level
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Column(name="fname")  //we can also put the @Column at the getter level
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

/*1. If the annotation for the "id" is on the variable level, then all other annotations should also be on the variable level.
 * Otherwise, they will use the variable name for the column name.
 * 2. If the annotation for the "id" is on the getter level, then all other annotatins should also be on the getter level.
 * Otherwise, they will use the variable name for the column name.
 * 
 * 3. If both the variable level and the getter level are annotated, the getter level will be the priority and will be used in the table.
 */
