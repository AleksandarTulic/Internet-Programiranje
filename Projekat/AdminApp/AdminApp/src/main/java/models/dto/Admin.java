package models.dto;

public class Admin extends User{
	public Admin() {
		super();
	}
	
	public Admin(Long id, String firstName, String lastName, String username, String password) {
		super(id,firstName, lastName, username, password);
	}
}
