package HibernateBasic4;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 * annotation for primary key
 */
@Entity     
public class Customer4 {
	//@Id 
	//@GeneratedValue  //hibernate will generate the primary key automatically
	//@GeneratedValue (strategy=GenerationType.IDENTITY) //hibernate will generate the primary key use "IDENTITY" strategy
	private int id;
	@Id 
	private String email;   //if a string is set as primary key, then return type of session.save(..) should be changed to String type due to session.save(..) return the primary key
	private String lastName; 
	private String firstName;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Customer4() {	}  
	
	public Customer4(String firstName, String lastName, String email) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
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

}

/* Key terminology:
 * see:  http://www.agiledata.org/essays/keys.html
 * 
 * natural key: with business meaning. For instance, each customer can only have a unique email 
 * Surrogate key: no business meaning. For instance, autoincremented integer.
 * 
 * 2. use composite keys (multiple variables) as primary key
 * see: http://stackoverflow.com/questions/21284175/how-to-make-two-column-as-a-primary-key-in-hibernate-annotation-class
 * http://www.simplecodestuffs.com/composite-primary-keys-in-hibernate/
 * 
 */
