package FormData;

import java.util.Date;

public class MessageForm {
	private String firstName;
	private Date date;
	private String contentMessage;
//	private String image;
	
	
 
	
//	public String getImage() {
//		return image;
//	}
//	public void setImage(String image) {
//		this.image = image;
//	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getContentMessage() {
		return contentMessage;
	}
	public void setContentMessage(String contentMessage) {
		this.contentMessage = contentMessage;
	}
	
	
	public MessageForm() {
		super();
	}
	public MessageForm(String firstName, Date date, String contentMessage) {
		super();
		this.firstName = firstName;
		this.date = date;
		this.contentMessage = contentMessage;
	
	}
	@Override
	public String toString() {
		return "MessageForm [firstName=" + firstName + ", date=" + date + ", contentMessage=" + contentMessage
				+ ",]";
	}
	
	
	
	
	
}
