package translator;

public class Variable {
	String name;
	Domain domain;
	Agent agent;
	
	Variable(String name, Domain domain, Agent agent){
		this.name = name;
		this.domain = domain;
		this.agent = agent;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Domain getDomain() {
		return domain;
	}
	public void setDomain(Domain domain) {
		this.domain = domain;
	}
	public Agent getAgent() {
		return agent;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	
	
		
}
