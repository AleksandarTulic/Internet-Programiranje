package models.dto;

import java.util.Objects;

public class SupportMessage {
	private Long id;
	private String title;
	private String message;
	private String datetime;
	private Boolean read;
	private String return_message;
	private Consumer consumer;
	
	public SupportMessage() {
		super();
	}
	
	public SupportMessage(Long id, String title, String message, String datetime, Boolean read, String return_message,
			Consumer consumer) {
		super();
		this.id = id;
		this.title = title;
		this.message = message;
		this.datetime = datetime;
		this.read = read;
		this.return_message = return_message;
		this.consumer = consumer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDateTime() {
		return datetime;
	}

	public void setDateTime(String datetime) {
		this.datetime = datetime;
	}

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public String getReturn_message() {
		return return_message;
	}

	public void setReturn_message(String return_message) {
		this.return_message = return_message;
	}

	public Consumer getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	@Override
	public String toString() {
		return "SupportMessage [id=" + id + ", title=" + title + ", message=" + message + ", calendar=" + datetime
				+ ", read=" + read + ", return_message=" + return_message + ", consumer=" + consumer + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(datetime, consumer, id, message, read, return_message, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SupportMessage other = (SupportMessage) obj;
		return Objects.equals(id, other.id);
	}
}
