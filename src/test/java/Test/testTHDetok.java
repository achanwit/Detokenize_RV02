package Test;

import main.Detokenize;

public class testTHDetok {

	public testTHDetok() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String inputStr = "ใน นิยาย ที่ ผม เป็น คน เขียน LSSPACELS ไม่มี ใคร ต้องตา ย";
		Detokenize dtk = new Detokenize();
//		String output = dtk.processDetokenizeStr(inputStr, "th");
//		System.out.println(output);
		
		long Begin = System.currentTimeMillis();
//		dtk.processDetokenizeFile("/home/chanwit/Documents/LSSpace/A.Input/Larg_TH-TestSet1.txt", "th");
		dtk.processDetokenizeFile("/home/chanwit/Documents/LSSpace/A.Input/TH-TestSet1.txt", "/home/chanwit/Documents/LSSpace/A.Input/Output_TH-TestSet1.txt","th");
//		dtk.processDetokenizeFileMultiThread("/home/chanwit/Documents/LSSpace/A.Input/Larg_TH-TestSet1.txt",
//				"/home/chanwit/Documents/LSSpace/A.Input/Output_Larg_TH-TestSet2.txt",
//				"th",
//				6);
		long End = System.currentTimeMillis();
		long Total = End - Begin;
		System.out.println("Total: "+Total);
		
	}

}
