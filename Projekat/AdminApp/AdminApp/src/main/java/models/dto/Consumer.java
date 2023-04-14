package models.dto;

public class Consumer extends User{
	private String phone;
	private String email;
	private String avatar;
	private String city;
	
	public Consumer() {
		super();
	}
	
	public Consumer(Long id, String firstName, String lastName, String username, String password, String email, String phone, String city, String avatar) {
		super(id,firstName, lastName, username, password);
		this.phone = phone;
		this.city = city;
		this.email = email;
		this.avatar = avatar;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
