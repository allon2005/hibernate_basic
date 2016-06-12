package HibernateBasic3;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

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
	@Column(name="customer_id")
	private int id;
	@Column(name="last_name")
	private String lastName;
	@Column(name="first_name")
	private String firstName; 
	@Basic     //@Basic means this variable will be persisted into the table using the variable name. If no any annotation, it is the default.
	private String address;
	private String phoneNumber;
	private static String comments;  //if "static", will not be saved into database
	private transient String comments1; //if "transient", will not be saved into database
	@Transient   //if "transient" or "Static", will not be saved into database
	private String comment3;
	//private final String comments2 = "hi";  // can't be final. Otherwise, no setter because can not re-assign value to it!!!
    @Temporal(TemporalType.DATE)  // if no annotation, the "Date" will be the complete time stamp (01/01/2016 12:00:00..). If using @Temporal, you can change the saved data format
	private Date registerDate;
    @Column(length=512)// the default size for String type is 255. We can change it
    private String comment4;
    @Lob                     //with @Lob, Hibernate will treat this variable as "large" object, not limited by the default size of 255
    private String comment5;
    


    @Column(length=512)
	public String getComment4() {
		return comment4;
	}
	public void setComment4(String comment4) {
		this.comment4 = comment4;
	}
	public String getComment5() {
		return comment5;
	}
	public void setComment5(String comment5) {
		this.comment5 = comment5;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public String getComment3() {
		return comment3;
	}
	public void setComment3(String comment3) {
		this.comment3 = comment3;
	}
	/*public String getComments2() {
		return comments2;
	}*/
	public static String getComments() {
		return comments;
	}
	public static void setComments(String comments) {
		Customer3.comments = comments;
	}
	public String getComments1() {
		return comments1;
	}
	public void setComments1(String comments1) {
		this.comments1 = comments1;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
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
