package FormData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InforData implements Serializable {
	
	private List<Information> listMess = new ArrayList<Information>();
	
//	public static void add(MessageForm mess) {
//		listMess.add(mess);
//	}
	
	public List<Information> getListMess() {
		return listMess;
	}

	@Override
	public String toString() {
		return "InforData [listMess=" + listMess + "]";
	}
	
	
}
