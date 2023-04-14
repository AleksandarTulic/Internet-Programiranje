package models.dto;

public class SupportSpecialist extends User{
	public SupportSpecialist() {
		super();
	}
	
	public SupportSpecialist(Long id, String firstName, String lastName, String username, String password) {
		super(id,firstName, lastName, username, password);
	}
}
