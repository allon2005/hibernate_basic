package HibernateBasic5;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/*
 * Address object is an embedded object inside the customer.
 * 
 */

@Embeddable               //using @Embeddable means this one can be embedded in other objects.  Note: not "@Embedded"!!!
public class Address {
	
	@Column(name="cust_city")   // in the embeddable class, we can configure the variables, which will be used when being saved to DB.
	private String city;
	@Column(name="cust_state")
	private String state;
	private String zipCode;
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	

}


