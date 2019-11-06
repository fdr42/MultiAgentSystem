package translator;

import java.io.FileNotFoundException;

public class MultiAgentSystemTranslator {

	public static void main(String[] args) throws FileNotFoundException {
		
		Instance test = new Instance("./FullRLFAP/CELAR/scen01/");
		
		
		test.toXmlFile();
		
	}
}
