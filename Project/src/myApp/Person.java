package myApp;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name = "person")
@XmlType(propOrder = { "id", "firstName", "lastName", "car", "country", "city"})
public class Person {
	private String id;
	private String firstName;
	private String lastName;
	private String car;
	private String city;
	private String country;
	
	public Person(String id, String firstName, String lastName, String car, String country, String city) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.car = car;
		this.city = city;
		this.country = country;
	}
	public Person(String firstName, String lastName, String car, String city, String country) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.car = car;
		this.city = city;
		this.country = country;
	}
	public Person() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCar() {
		return car;
	}

	public void setCar(String car) {
		this.car = car;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", car=" + car + ", city="
				+ city + ", country=" + country + "]";
	}
	
}
