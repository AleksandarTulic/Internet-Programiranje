package models.dto;

import java.util.Objects;

public class Consumer extends User{
	private String email;

	public Consumer(String email) {
		super();
		this.email = email;
	}
	
	public Consumer(String email, String firstName, String lastName) {
		super(firstName, lastName);
		this.email = email;
	}
	
	public Consumer(Long id, String firstName, String lastName, String username, String password, String email) {
		super(id, firstName, lastName, username, password);
		this.email= email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(email);
		return result;
	}
}
