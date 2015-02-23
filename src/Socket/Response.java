package Socket;

import java.util.ArrayList;


public class Response {
	   private double opt_score;
	   private ArrayList<String> device_id;
	   private ArrayList<String> device_type;
	   private ArrayList<ArrayList<String>> available_set;
	   private ArrayList<Double> avilable_score;
	   
	   public Response()
	   {
		   available_set = new ArrayList<ArrayList<String>>();
		   avilable_score = new ArrayList<Double>();
	   }
	   
	   public void setOpt_score(double opt_score) {
	      this.opt_score = opt_score;
	   }
	   public void setDevice_id(ArrayList<String> device_id) {
	      this.device_id = device_id;
	   }
	   public ArrayList<String> getDevice_id() {
	      return device_id;
	   }
	   public double getOpt_score() {
	      return opt_score;
	   }
	   public ArrayList<String> getDevice_type() {
		return device_type;
	   }
	   public void setDevice_type(ArrayList<String> device_type) {
		this.device_type = device_type; 
	   }
	   public ArrayList<ArrayList<String>> getAvailable_set() {
		return available_set;
	   }
	
	   public void addAvailable_set(ArrayList<String> available)
	   {
		   this.available_set.add(available);
	   }
	   public void addAvailable_score(double score)
	   {
		   this.avilable_score.add(score);
	   }
	   public ArrayList<Double> getAvilable_score() {
		return avilable_score;
	}

}