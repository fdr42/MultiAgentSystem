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
			 
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
 
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
 
            Document document = documentBuilder.newDocument();
 
            // CREATION DE LA RACINE "instance"
            Element root = document.createElement("instance");
            document.appendChild(root);
            
 
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
                nameDomain.setValue(this.domainList.get(i).getName());
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
                nameVariable.setValue(this.variableList.get(i).getName());
                variable.setAttributeNode(nameVariable);
                
                //Domaine
                Attr domainName = document.createAttribute("domain");
                domainName.setValue(this.variableList.get(i).getDomain().getName());
                variable.setAttributeNode(domainName);
                
                //Agent
                Attr agentName = document.createAttribute("agent");
                agentName.setValue(this.variableList.get(i).getAgent().getName());
                variable.setAttributeNode(agentName);
                
            }
            
 
            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("./XmlOutput/instance0.xml"));
 
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
