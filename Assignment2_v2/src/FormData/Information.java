package FormData;

import java.io.FileWriter;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Information implements Serializable {
	private String firstName;
	private Date date;
	private String contentMessage;
	private List<String> favorites;
	
	@Override
	public String toString() {
		return "Information [firstName=" + firstName + ", date=" + date + ", contentMessage=" + contentMessage
				+ ", favorites=" + favorites + "]";
	}
	
	public List<String> getFavorites() {
		return favorites;
	}
	public void setFavorites(List<String> favorites) {
		this.favorites = favorites;
	}
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

	

	
	
}
