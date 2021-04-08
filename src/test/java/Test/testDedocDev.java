package Test;

import main.Detokenize;
public class testDedocDev {

	public testDedocDev() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		long Begin = System.currentTimeMillis();
		Detokenize dtk = new Detokenize();
		dtk.processDetokenizeFileMultiThread("/home/chanwit/Documents/LSTokenize/A.Pre-Study/mosesdecoder/moses/scripts/tokenizer/InputDetokenize.txt",
				"/home/chanwit/Documents/LSTokenize/A.Pre-Study/mosesdecoder/moses/scripts/tokenizer/",
				"en",
				6);
//		dtk.processDetokenizeFile("/home/chanwit/Documents/LSTokenize/A.Pre-Study/mosesdecoder/moses/scripts/tokenizer/InputDetokenize.txt", "en");
		long End = System.currentTimeMillis();
		long Total = End - Begin;
		System.out.println("Total: "+Total);
	}
}
