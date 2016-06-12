package HibernateBasic8;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Phone {
	@Id @GeneratedValue
	private int phone_id;
	//@Id   //will have error if use this as primary key. See bottom comments
	private String number;
	private String type;
	
	public int getPhone_id() {
		return phone_id;
	}
	public void setPhone_id(int phone_id) {
		this.phone_id = phone_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}

}

/*1. if using "number" as the @Id (primary key) here, will get error:
 * com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Cannot add or update a child row: a foreign key constraint fails (`hibernate_test`.`customer8`, CONSTRAINT `FKke6bgcm2x7vxxb5s7680tpf59` FOREIGN KEY (`phone_number`) REFERENCES `phone` (`number`))

 see: http://stackoverflow.com/questions/32791244/hibernate-onetoone-cannot-add-or-update-a-child-row-a-foreign-key-constraint
 
 we should use an seperate varaible like the phone_id as primary key for this purpose.
 * 
 * 
 */
