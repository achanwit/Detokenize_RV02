package util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class Constant {

	public Constant() {
		// TODO Auto-generated constructor stub
		
		
	}
	
	public static final String IsAlnum = readFiletoString("/home/chanwit/workspace-LSTokenizeLocale/Detokenize_RV02/perluniprops/IsAlnum.txt");
	
	public static final String IsAlpha = readFiletoString("/home/chanwit/workspace-LSTokenizeLocale/Detokenize_RV02/perluniprops/IsAlpha.txt");
	
	public static final String IsSc = readFiletoString("/home/chanwit/workspace-LSTokenizeLocale/Detokenize_RV02/perluniprops/IsSc.txt");
	
	public static final String[] AGGRESSIVE_HYPHEN_SPLIT = {" \\@\\-\\@ ", "-"};
	
	public static final String[] ONE_SPACE = {"\\s{2,}", " "};
	
	public static final String[] UNESCAPE_FACTOR_SEPARATOR = {"&#124;", "|"};
	
	public static final String[] UNESCAPE_LEFT_ANGLE_BRACKET = {"&lt;", "<"};
	
	public static final String[] UNESCAPE_RIGHT_ANGLE_BRACKET = {"&gt;", ">"};
	
	public static final String[] UNESCAPE_DOUBLE_QUOTE = {"&quot;", "\""};
	
	public static final String[] UNESCAPE_SINGLE_QUOTE = {"&apos;", "'"};
	
	public static final String[] UNESCAPE_SYNTAX_NONTERMINAL_LEFT = {"&#91;", "["};
	
	public static final String[] UNESCAPE_SYNTAX_NONTERMINAL_RIGHT = {"&#93;", "]"};
	
	public static final String[] UNESCAPE_AMPERSAND = {"&amp;", "&"};
	
	public static final String[] UNESCAPE_FACTOR_SEPARATOR_LEGACY  = {"&bar;", "|"};
	
	public static final String[] UNESCAPE_SYNTAX_NONTERMINAL_LEFT_LEGACY = {"&bra;", "["};
	
	public static final String[] UNESCAPE_SYNTAX_NONTERMINAL_RIGHT_LEGACY = {"&ket;", "]"};
	
	public static final String TEMP_FOLDER_PROCESS = "/var/www/detokenize-7030/TMP/";
	
	public static final String[][] MOSES_UNESCAPE_XML_REGEXES = {
			UNESCAPE_FACTOR_SEPARATOR_LEGACY, 
			UNESCAPE_FACTOR_SEPARATOR,
	        UNESCAPE_LEFT_ANGLE_BRACKET,
	        UNESCAPE_RIGHT_ANGLE_BRACKET,
	        UNESCAPE_SYNTAX_NONTERMINAL_LEFT_LEGACY,
	        UNESCAPE_SYNTAX_NONTERMINAL_RIGHT_LEGACY,
	        UNESCAPE_DOUBLE_QUOTE,
	        UNESCAPE_SINGLE_QUOTE,
	        UNESCAPE_SYNTAX_NONTERMINAL_LEFT,
	        UNESCAPE_SYNTAX_NONTERMINAL_RIGHT,
	        UNESCAPE_AMPERSAND
			};
	
	public static final String[] FINNISH_MORPHSET_1 = {
			"N",
			"n",
			"A",
			"a",
			"\\xc4",
			"\\xe4",
			"ssa",
			"Ssa",
			"ss\\xe4",
			"Ss\\xe4",
			"sta",
			"st\\xe4",
			"Sta",
			"St\\xe4",
			"hn",
			"Hn",
			"hyn",
			"Hyn",
			"han",
			"Han",
			"h\\xe4n",
			"H\\xe4n",
			"h\\xf6n",
			"H\\xf6n",
			"n",
			"n",
			"yn",
			"Yn",
			"an",
			"An",
			"\\xe4n",
			"\\xc4n",
			"\\xf6n",
			"\\xd6n",
			"seen",
			"Seen",
			"lla",
			"Lla",
			"ll\\xe4",
			"Ll\\xe4",
			"lta",
			"Lta",
			"lt\\xe4",
			"Lt\\xe4",
			"lle",
			"Lle",
			"ksi",
			"Ksi",
			"kse",
			"Kse",
			"tta",
			"Tta",
			"ine",
			"Ine"
	};
	
	public static final String[] FINNISH_MORPHSET_2 = {"ni", "si", "mme","nne", "nsa"};
	
	public static final String[] FINNISH_MORPHSET_3 = {
			 	"ko",
		        "k\\xf6",
		        "han",
		        "h\\xe4n",
		        "pa",
		        "p\\xe4",
		        "kaan",
		        "k\\xe4\\xe4n",
		        "kin"
			
	};
	
	private static String combineArray(String[] FINNISH_MORPHSET_1, String[] FINNISH_MORPHSET_2, String[] FINNISH_MORPHSET_3) {
		
		StringBuffer strBuf = new StringBuffer();
		
		strBuf.append("^(");
		for(int i = 0; i<FINNISH_MORPHSET_1.length;i++ ) {
			if(i==FINNISH_MORPHSET_1.length-1) {
				strBuf.append(FINNISH_MORPHSET_1[i]);
			}else {
				strBuf.append(FINNISH_MORPHSET_1[i]).append(",");
			}
			
		}
		strBuf.append(")(");
		for(int i = 0; i<FINNISH_MORPHSET_2.length;i++ ) {
			if(i==FINNISH_MORPHSET_2.length-1) {
				strBuf.append(FINNISH_MORPHSET_2[i]);
			}else {
				strBuf.append(FINNISH_MORPHSET_2[i]).append(",");
			}
			
		}
		strBuf.append(")?(");
		for(int i = 0; i<FINNISH_MORPHSET_3.length;i++ ) {
			if(i==FINNISH_MORPHSET_3.length-1) {
				strBuf.append(FINNISH_MORPHSET_3[i]);
			}else {
				strBuf.append(FINNISH_MORPHSET_3[i]).append(",");
			}
			
		}
		strBuf.append(")$");
		return new String(strBuf);
		
	}
	
	public static final String FINNISH_REGEX_REG = combineArray(FINNISH_MORPHSET_1,FINNISH_MORPHSET_2, FINNISH_MORPHSET_3);
	public static final Pattern FINNISH_REGEX_PATTERN = Pattern.compile(FINNISH_REGEX_REG);
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	private static String readFiletoString(String filePath) {
		
		FileInputStream inputString = null;
		try {
			inputString = new FileInputStream(filePath);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		DataInputStream in = new DataInputStream(inputString);
		BufferedReader br1 = new BufferedReader(new InputStreamReader(in));
		StringBuffer inputBuf = new StringBuffer();
		
		long BeginReadFile = System.currentTimeMillis();
		try {
			String line = null;
			while ((line = br1.readLine()) != null){
				inputBuf = inputBuf.append(line).append("\n");
			}
		}catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				if (br1 != null)
					br1.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		long EndReadFile = System.currentTimeMillis();
		long TotalReadFile = EndReadFile - BeginReadFile;
//		System.out.println("TotalReadFile: "+TotalReadFile);
		
		return new String(inputBuf);
		
	}
	
	

}
