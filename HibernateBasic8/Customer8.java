package HibernateBasic8;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


/*
 * Entity mapping with @OneToOne
 */
@Entity     
public class Customer8 {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String lastName; 
	private String firstName;
	@OneToOne (cascade={CascadeType.ALL})  // this will map the phone with the customer in 1 to 1 fashion
	private Phone phone;  //now, phone is not an "@Embeded, it is an @Entity, which requires its own table.  We need to connect customer with the phone

	
	public Customer8() {	}  
	public Customer8(String firstName, String lastName, Phone phone) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.phone = phone;
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

	public Phone getPhone() {
		return phone;
	}
	public void setPhone(Phone phone) {
		this.phone = phone;
	}

}

/* 1. When we just use @OneToOne, we get the error:
 * 
 *  org.hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance beforeQuery flushing: HibernateBasic8.Phone
	
	see:  http://stackoverflow.com/questions/2302802/object-references-an-unsaved-transient-instance-save-the-transient-instance-be/2302814
	
	We need to add @OneToOne (cascade={CascadeType.ALL})!!!
 * 
 * 
 * 
 */
 


