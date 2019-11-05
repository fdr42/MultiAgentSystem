package translator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Instance {
	List<Agent> agentList;
	List<Domain> domainList;
	List<Variable> variableList;
	
	Instance(String folderPath) throws FileNotFoundException{
		this.agentList = new ArrayList<Agent>();
		this.domainList = new ArrayList<Domain>();
		this.variableList = new ArrayList<Variable>();
		
		
		//RECUPERATION DES DOMAINES :
		
		File dom = new File(folderPath + "dom.txt");
		
		Scanner input = new Scanner(dom);
		input.useDelimiter("\\n|\\r");
		
		while(input.hasNext()) {
			String line = input.next();
			String[] tab = line.split("\\s+");
			int indice = 0;
			if(tab[0].equals("")) {
				indice = 1;
			}
			//On decale tout de 1 (premier caractere de la ligne vide)
			String nameDom = tab[0+indice];
			
			System.out.println("------Nouvelle Ligne--------");
			List<Integer> listVal = new ArrayList<Integer>();
			for(int i = 2+indice; i < tab.length; i++) {
				listVal.add(Integer.parseInt(tab[i]));
			}
			System.out.println("nom = "+nameDom+ " listVal= "+listVal.toString());
			Domain domain = new Domain(nameDom, listVal);

			this.domainList.add(domain);
		}
		input.close();
		// RECUPERATION DES VARIABLES :
		for(int i=0;i<5;i++)
		System.out.println("------ FIN DOMAINES --------");
		
		File var = new File(folderPath + "var.txt");

		input = new Scanner(var);
		input.useDelimiter("\\n|\\r");

		while(input.hasNext()) {
			
			String line = input.next();
			String[] tab = line.split("\\s+");
			int indice = 0;
			if(tab[0].equals("")) {
				indice = 1;
			}
			String nameVar = tab[0+indice];
			Domain domain = null;
			System.out.println("------ Nouvelle variable --------");
			for(Domain d : this.domainList){
				if(d.getName().equals(tab[1+indice])) {
					domain = d;
					break;
				}
			}
			System.out.println("Domaine: "+ domain.getName() + "nom " + nameVar);
			Variable variable = new Variable(nameVar, domain,new Agent("Agent"+nameVar));

			this.domainList.add(domain);
		}

		
		
		
	}

	public List<Agent> getAgentList() {
		return agentList;
	}

	public void setAgentList(List<Agent> agentList) {
		this.agentList = agentList;
	}

	public List<Domain> getDomainList() {
		return domainList;
	}

	public void setDomainList(List<Domain> domainList) {
		this.domainList = domainList;
	}

	public List<Variable> getVariableList() {
		return variableList;
	}

	public void setVariableList(List<Variable> variableList) {
		this.variableList = variableList;
	}
	
}
