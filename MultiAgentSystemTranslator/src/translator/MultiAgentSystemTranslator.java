package translator;

import java.io.FileNotFoundException;

public class MultiAgentSystemTranslator {

	public static void main(String[] args) throws FileNotFoundException {
		for(int i=1;i<12;i++) {
			Instance test = new Instance("./FullRLFAP/CELAR/scen",i);
			test.toXmlFile();
		}
		

		
	}
}
