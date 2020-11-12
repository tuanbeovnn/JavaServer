package formdata;

import java.util.ArrayList;
import java.util.List;

public class MessageData {
	
	private static List<MessageForm> listMess = new ArrayList<MessageForm>();
	
//	public static void add(MessageForm mess) {
//		listMess.add(mess);
//	}
	
	public static List<MessageForm> getListMess() {
		return listMess;
	}
	

}
