package translator;

import java.io.FileNotFoundException;

public class MultiAgentSystemTranslator {

	public static void main(String[] args) throws FileNotFoundException {
		/*	Instance test = new Instance("./FullRLFAP/exemples/exemple4/",-1);
		test.toXmlFile();
		 test = new Instance("./FullRLFAP/exemples/exemple10/",-1);
		test.toXmlFile();
		 test = new Instance("./FullRLFAP/exemples/exemple20/",-1);
		test.toXmlFile();*/
	for(int i=1;i<12;i++) {
			Instance test = new Instance("./FullRLFAP/CELAR/scen",i);
			test.toXmlFile();
		}
		

		
	}
}
