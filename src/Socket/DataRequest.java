package Socket;

import java.io.Serializable;
import java.util.ArrayList;

public class DataRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<?> list;
	
	public ArrayList<?> getList(){
		return list;
	}
	
	public void setList(ArrayList<?> list){
		this.list = list;
	}
}
