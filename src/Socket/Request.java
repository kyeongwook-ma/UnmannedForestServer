package Socket;

import java.util.ArrayList;

import Environment.ForestCell;


public class Request {
	
	private ArrayList<ForestCell> forest_list;
	
	public ArrayList<ForestCell> getForest_list() {
		return forest_list;
	}

	public void setForest_list(ArrayList<ForestCell> forest_list) {
		this.forest_list = forest_list;
	}

	public Request(ArrayList<ForestCell> forest_list)
	{
		this.forest_list = forest_list;
	}

}
