package translator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Instance {
	List<Agent> agentList;
	List<Domain> domainList;
	List<Variable> variableList;
	List<Constraint> constraintList;
	Integer nb;

	
	Instance(String folderPath,int nb) throws FileNotFoundException{
		this.agentList = new ArrayList<Agent>();
		this.domainList = new ArrayList<Domain>();
		this.variableList = new ArrayList<Variable>();
		this.constraintList = new ArrayList<Constraint>();
		this.nb=nb;
		
		if(nb>=10){
			folderPath=folderPath+nb+"/";
		}else{
			folderPath=folderPath+"0"+nb+"/";
		}
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
			
			//System.out.println("------Nouvelle Ligne--------");
			List<Integer> listVal = new ArrayList<Integer>();
			for(int i = 2+indice; i < tab.length; i++) {
				listVal.add(Integer.parseInt(tab[i]));
			}
			//System.out.println("nom = "+nameDom+ " listVal= "+listVal.toString());
			Domain domain = new Domain(nameDom, listVal);

			this.domainList.add(domain);
		}
		input.close();
		// RECUPERATION DES VARIABLES et AGENTS:
		//for(int i=0;i<5;i++)
		// System.out.println("------ FIN DOMAINES --------");
		
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
			//System.out.println("------ Nouvelle variable --------");
			for(Domain d : this.domainList){
				if(d.getName().equals(tab[1+indice])) {
					domain = d;
					break;
				}
			}
			//System.out.println("Domaine: "+ domain.getName() + "nom " + nameVar);
			
			Agent agent = new Agent("Agent"+nameVar);
			this.agentList.add(agent);
			
			Variable variable = new Variable(nameVar, domain, agent);
			this.variableList.add(variable);
		}
		File con = new File(folderPath + "ctr.txt");

		input = new Scanner(con);
		input.useDelimiter("\\n|\\r");

		while(input.hasNext()) {

			String line = input.next();
			String[] tab = line.split("\\s+");
			int indice = 0;
			if(tab[0].equals("")) {
				indice = 1;
			}
			String nameVar = tab[0+indice];
			Constraint constraint = new Constraint();
			constraint.setF1(Integer.parseInt(tab[indice]));
			indice++;
			constraint.setF2(Integer.parseInt(tab[indice]));
			indice++;
			constraint.setDifference(tab[indice].equals("D"));

			indice++;
			indice++;
			constraint.setValue(Integer.parseInt(tab[indice]));
			if(tab.length==7){
				constraint.setCost(Integer.parseInt(tab[indice]));
			} else {
				constraint.setCost(-1);
			}
			this.constraintList.add(constraint);

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
	
	public void toXmlFile() {
		try {
			int a1=1, a2=1, a3=1, a4=1;
			if(this.nb==4 || this.nb==5 || this.nb==6 || this.nb==9  || this.nb==11)
			{
				a1 = 1000;
				a2 = 100;
				a3 = 10;
				a4 = 1;
			}
			if(this.nb==7)
			{
				a1 = 1000000;
				a2 = 10000;
				a3 = 100;
				a4 = 1;
			}
			if(this.nb==8)
			{
				a1 = 4;
				a2 = 3;
				a3 = 2;
				a4 = 1;
			}
			if(this.nb==10)
			{
				a1 = 1000;
				a2 = 100;
				a3 = 2;
				a4 = 1;
			}
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
 
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
 
            Document document = documentBuilder.newDocument();
 
            // CREATION DE LA RACINE "instance"
            Element root = document.createElement("instance");
            document.appendChild(root);

			//Presentation
			Element presentation = document.createElement("presentation");
			presentation.setAttribute("name", "sampleProblem");
			presentation.setAttribute("maxConstraintArity", "2");
			presentation.setAttribute("maximize", "false");
			presentation.setAttribute("format", "XCSP 2.1_FRODO");
			root.appendChild(presentation);

			// CREATION DES AGENTS
            Element agents = document.createElement("agents");
            
            root.appendChild(agents);
            
            Attr nbAgents = document.createAttribute("nbAgents");
            nbAgents.setValue(new Integer(this.agentList.size()).toString());
            agents.setAttributeNode(nbAgents);
            
            for(int i = 0; i < this.agentList.size(); i++) {
            	Element agent = document.createElement("agent");
            	agents.appendChild(agent);
            	Attr nameAgent = document.createAttribute("name");
                nameAgent.setValue(this.agentList.get(i).getName());
                agent.setAttributeNode(nameAgent);
            }
            
            
            // CREATION DE DOMAINES
            Element domains = document.createElement("domains");
            
            root.appendChild(domains);
            
            Attr nbDomains = document.createAttribute("nbDomains");
            nbDomains.setValue(new Integer(this.domainList.size()).toString());
            domains.setAttributeNode(nbDomains);
            
            for(int i = 0; i < this.domainList.size(); i++) {
            	Element domain = document.createElement("domain");
            	domains.appendChild(domain);
            	Attr nameDomain = document.createAttribute("name");
                nameDomain.setValue("domain"+this.domainList.get(i).getName());
                domain.setAttributeNode(nameDomain);
                
                Attr nbValues = document.createAttribute("nbValues");
                nbValues.setValue(new Integer(this.domainList.get(i).getListVar().size()).toString());
                domain.setAttributeNode(nbValues);
                for(int j = 0; j < this.domainList.get(i).getListVar().size(); j++) {
                	domain.appendChild(document.createTextNode(this.domainList.get(i).getListVar().get(j).toString() + " "));
                } 
            }
            
            // CREATION DES VARIABLES
            Element variables = document.createElement("variables");
            
            root.appendChild(variables);
            
            Attr nbVariables = document.createAttribute("nbVariables");
            nbVariables.setValue(new Integer(this.variableList.size()).toString());
            variables.setAttributeNode(nbVariables);
            
            for(int i = 0; i < this.variableList.size(); i++) {
            	Element variable = document.createElement("variable");
            	variables.appendChild(variable);
            	//Nom
            	Attr nameVariable = document.createAttribute("name");
                nameVariable.setValue("V"+this.variableList.get(i).getName());
                variable.setAttributeNode(nameVariable);
                
                //Domaine
                Attr domainName = document.createAttribute("domain");
                domainName.setValue("domain"+this.variableList.get(i).getDomain().getName());
                variable.setAttributeNode(domainName);
                
                //Agent
                Attr agentName = document.createAttribute("agent");
                agentName.setValue(this.variableList.get(i).getAgent().getName());
                variable.setAttributeNode(agentName);
                
            }
			//Predicates
			Element predicatesElement = document.createElement("predicates");
			predicatesElement.setAttribute("nbPredicates", "3");
			root.appendChild(predicatesElement);

			//Predicate EQUAL => Hard constraint
			Element predicate = document.createElement("predicate");
			predicate.setAttribute("name", "EQUAL");
			Element parameters = document.createElement("parameters");
			parameters.appendChild(document.createTextNode(" int V1 int V2 int VALUE "));
			predicate.appendChild(parameters);
			Element expression = document.createElement("expression");
			Element functional = document.createElement("functional");
			functional.appendChild(document.createTextNode(" eq(abs(sub(V1, V2)),VALUE) "));
			expression.appendChild(functional);
			predicate.appendChild(expression);
			predicatesElement.appendChild(predicate);

			//Predicate GTHARD => Hard constraint
			predicate = document.createElement("predicate");
			predicate.setAttribute("name", "GTHARD");
			parameters = document.createElement("parameters");
			parameters.appendChild(document.createTextNode(" int V1 int V2 int VALUE "));
			predicate.appendChild(parameters);
			expression = document.createElement("expression");
			functional = document.createElement("functional");
			functional.appendChild(document.createTextNode(" gt(abs(sub(V1, V2)),VALUE) "));
			expression.appendChild(functional);
			predicate.appendChild(expression);
			predicatesElement.appendChild(predicate);

			//Predicate GTSOFT => soft constraint
			predicate = document.createElement("function");
			predicate.setAttribute("name", "GTSOFT");
			parameters = document.createElement("parameters");
			parameters.appendChild(document.createTextNode(" int V1 int V2 int VALUE int COST"));
			predicate.appendChild(parameters);
			expression = document.createElement("expression");
			functional = document.createElement("functional");
			functional.appendChild(document.createTextNode(" if(gt(abs(sub(V1, V2)),VALUE),0, COST) "));
			expression.appendChild(functional);
			predicate.appendChild(expression);
			predicatesElement.appendChild(predicate);
			// CREATION DE contraintes
			Element constraints = document.createElement("constraints");
			constraints.setAttribute("nbConstraints", String.valueOf(constraintList.size()));
			root.appendChild(constraints);


			for(Constraint c : constraintList) {
				Element constraint = document.createElement("constraint");
				constraint.setAttribute("name", c.getF1()+"_"+c.getF2()+"_"+c.getValue());
				constraint.setAttribute("arity", "2");
				constraint.setAttribute("scope", "V"+c.getF1()+" V"+c.getF2());
				if (c.isDifference()){
					constraint.setAttribute("reference", "EQUAL");
				}
				else if (c.getCost()!= -1) {
					constraint.setAttribute("reference", "GTSOFT");
				} else {
					constraint.setAttribute("reference", "GTHARD");
				}
				Element param = document.createElement("parameters");
				int cost=-1;
				switch (c.getCost()){
					case 1:
						cost = a1;
						break;
					case 2:
						cost = a2;
						break;
					case 3:
						cost = a3;
						break;
					case 4:
						cost = a4;
						break;
				}
				String params = "V"+c.getF1()+" V"+c.getF2()+ " "+c.getValue()+ (cost!=-1?" "+cost:"");
				param.appendChild(document.createTextNode(params));
				constraint.appendChild(param);
				constraints.appendChild(constraint);


			}



            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("./XmlOutput/instance"+this.nb+".xml"));
 
            // If you use
            //StreamResult result = new StreamResult(System.out);
            // the output will be pushed to the standard output ...
            // You can use that for debugging
            transformer.transform(domSource, streamResult);
            
            System.out.println("Done creating XML File");
 
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
	}
	
}
