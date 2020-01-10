package translator;

import java.util.List;

public class Domain {
	
	String name;
	List<Integer> listVal;
	
	Domain(String name, List<Integer> listVal){
		this.name = name;
		this.listVal = listVal;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Integer> getListVar() {
		return listVal;
	}
	public void setListVar(List<Integer> listVal) {
		this.listVal = listVal;
	}
	
}
