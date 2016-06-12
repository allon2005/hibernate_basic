package HibernateBasic5;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;

/*
 * embeded objects and AttributesOverride
 */
@Entity     
public class Customer5 {
	@Id
	private int id;
	private String lastName; 
	private String firstName;
	@Embedded     //this one is optional if the Address class is annotated with "@Embeddable". But, it is good to add for an indication purpose
	private Address address; //the address now is a type which contains its own info
	
	@Embedded
	//@AttributeOverride(name="city", column=@Column(name="home_addr_city"))  //if only one Attribute override
	@AttributeOverrides({
		@AttributeOverride(name="city", column=@Column(name="home_addr_city")),
		@AttributeOverride(name="state", column=@Column(name="home_addr_state")), 
		@AttributeOverride(name="zipCode", column=@Column(name="home_addr_zipCode")) 
	})  //so, this override will not use the default in the "Address class" and will use the new values set up here.
	
	private Address homeAddress;  // now, we have two addresses. We can have the columns for each address different by using "AttributeOverride"
    
	/*@EmbeddedId    //if the embeded object is used as primary key for the main class, then we need to add "@EmbededId" here. Nothing need to do with "Address" class for this.
	private Address addressID;*/
	

	public Address getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(Address homeAddress) {
		this.homeAddress = homeAddress;
	}
	public Customer5() {	}  
	public Customer5(String firstName, String lastName, Address address) {
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
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}

}


