package models.dto;

public class Log {
	private Long id;
	private String description;
	private String level;
	private String date;
	private String time;
	private User user;
	
	public Log() {
	}

	public Log(Long id, String description, String level, String date, String time, User user) {
		super();
		this.id = id;
		this.description = description;
		this.level = level;
		this.date = date;
		this.time = time;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
