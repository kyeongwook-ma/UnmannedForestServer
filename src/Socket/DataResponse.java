package Socket;

import java.io.Serializable;
import java.util.ArrayList;

public class DataResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<?> list;
	double opt_score;
	
	public double getOpt_score() {
		return opt_score;
	}

	public void setOpt_score(double opt_score) {
		this.opt_score = opt_score;
	}

	public ArrayList<?> getList(){
		return list;
	}
	
	public void setList(ArrayList<?> list){
		this.list = list;
	}
}
