package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.Constant;

public class DetokenizeMultithread implements Runnable{
	private StringBuffer inputBuff;
	private String language;
	private Constant constant;
	private int ignorecase = 2;
	private String outPutChunk;
	private Pattern[] pptAll;
	
	
//	private Pattern ppt1 = Pattern.compile("^[" + constant.IsSc + "\\(\\[\\{\\¿\\¡]+$");
//	private Pattern ppt2 = Pattern.compile("^[\\,\\.\\?\\!\\:\\;\\\\%\\}\\]\\)]+$");
//	private Pattern ppt3 = Pattern.compile("^[\\?\\!\\:\\;\\\\%]$");
//	private Pattern ppt4 = Pattern.compile("^[']["+constant.IsAlpha+"}]");
//	private Pattern ppt5 = Pattern.compile("^[0-9]+$");
//	private Pattern ppt6 = Pattern.compile("^[.,]$");
//	private Pattern ppt7 = Pattern.compile("^[0-9]+$");
//	private Pattern ppt8 = Pattern.compile("["+constant.IsAlpha+"][']$");
//	private Pattern ppt9 = Pattern.compile("^["+constant.IsAlpha+"]");
//	private Pattern ppt10 = Pattern.compile("["+constant.IsAlpha+"][']$");
//	private Pattern ppt11 = Pattern.compile("^[-–]$");
//	private Pattern ppt12 = Pattern.compile("^li$|^mail.*");
//	private Pattern ppt13 = Pattern.compile("^[\'\"„“`]+$");
//	private Pattern ppt14 = Pattern.compile("^[„“”]+$");
//	private Pattern ppt15 = Pattern.compile("[s]$");
//	private Pattern ppt16 = Pattern.compile(":$");
//	private Pattern ppt17 = Pattern.compile(constant.FINNISH_REGEX_REG);
	
	

	public DetokenizeMultithread(StringBuffer inputBuff, String outputFilePath, String language, Constant constant, Pattern[] pptAll) {
		this.inputBuff = inputBuff;
		this.language = language;
		this.constant = constant;
		this.outPutChunk = outputFilePath;
		this.pptAll = pptAll;
	}

	public void run() {
		// TODO Auto-generated method stub
		String inputRaw = new String(inputBuff);
		String[] inputRawArr = inputRaw.split("\n");
		
		//String line = null;
		for(int i = 1; i < inputRawArr.length; i++) {
			String outputAfterDetok = processDetokenizeStr(inputRawArr[i], language);
			writeFile(outputAfterDetok, outPutChunk);
		}
		
		
	}
	
	public String processDetokenizeStr(String inputStr, String language) {
		String output = null;
//		String[] inputArr = inputStr.split(" ");
//		System.out.println(inputArr.length);
//		System.out.println("Input: "+inputStr);
		
		HashMap<String, Integer> quote_counts = new HashMap<String, Integer>();
		
		quote_counts.put("'", 0);
		quote_counts.put("\"", 0);
		quote_counts.put("``", 0);
		quote_counts.put("`", 0);
		quote_counts.put("''", 0);
		
		
		StringBuffer fisrtPrepareInputTemp = new StringBuffer();
		fisrtPrepareInputTemp.append(" ").append(inputStr).append(" ");
		
		String fisrtInputTempStr = new String(fisrtPrepareInputTemp);
		
		
		
		fisrtInputTempStr = getvalueFromReplacement(pptAll[19], fisrtInputTempStr, "-");
		
		fisrtInputTempStr = getUnescapeXML(fisrtInputTempStr);
		
		String[] fisrtInputTempArr = fisrtInputTempStr.split(" ");
		
//		for(String token: fisrtInputTempArr) {
////			System.out.println("Top Token: "+token);
//		}
		
		String prepend_space = " ";
		StringBuffer detokenized_text = new StringBuffer();
		
		for(int i = 0; i<fisrtInputTempArr.length; i++ ) {
			String token = fisrtInputTempArr[i];
//			System.out.println("i="+i+" token: "+token);
			int lengOfToken =  token.length();
			
			String tmpForCJKFirst = "";
			if(lengOfToken == 0) {
				tmpForCJKFirst = token;
			}else {
				tmpForCJKFirst = token.substring(token.length()-1, token.length());
			}
			String tmpForCJKPrevious = "";
			if (i > 0) {
				
				int lengOfTokenPrevous = fisrtInputTempArr[i - 1].length();
				if (lengOfTokenPrevous == 0) {
					tmpForCJKPrevious = fisrtInputTempArr[i - 1];
				} else {
					tmpForCJKPrevious = fisrtInputTempArr[i - 1].substring(fisrtInputTempArr[i - 1].length() - 1,
							fisrtInputTempArr[i - 1].length());
				}
			}
			
			
			if(isCJK(tmpForCJKFirst) && !language.equals("KO")) {
				if(i>0 && isCJK(tmpForCJKPrevious)) {
					detokenized_text.append(token);
				}else {
					detokenized_text.append(prepend_space).append(token);
					prepend_space = " ";
				}
			}else if(checkPatternRegex(pptAll[0], token)) {
				detokenized_text.append(prepend_space).append(token);
				prepend_space = "";
			}else if(checkPatternRegex(pptAll[1], token) ){
					if( language == "fr" && checkPatternRegex(pptAll[2], token)) {
						detokenized_text.append(" ");
					}else {
						detokenized_text.append(token);
					}

				

					
				
			}else if(language == "en" && i >1 && checkPatternRegex(pptAll[3], token) ) {
				detokenized_text.append(token);
				prepend_space = " ";
			}
			
			
			else if(language == "cs" && i >1 
					&& checkPatternRegex(pptAll[4], fisrtInputTempArr[fisrtInputTempArr.length-2]) 
					&& checkPatternRegex(pptAll[5], fisrtInputTempArr[fisrtInputTempArr.length-1])
					&& checkPatternRegex(pptAll[6], token)
					) {
				detokenized_text.append(token);
				prepend_space = " ";
			}else if((language == "fr" || language == "it" || language == "ga") 
					&& (i <= fisrtInputTempArr.length - 2)
					&& checkPatternRegex(pptAll[7], token)
					&& (checkPatternRegex(pptAll[8], fisrtInputTempArr[i+1])))
					 {
//				if(i <= fisrtInputTempArr.length - 2) {
//					if(checkPatternRegex("["+constant.IsAlpha+"][']$", token)) {
//						if(checkPatternRegex("^["+constant.IsAlpha+"]", fisrtInputTempArr[i+1])) {
							detokenized_text.append(prepend_space).append(token);
							prepend_space = "";
//						}
//					}
//				}
			}else if(language == "cs" 
					&& i <= fisrtInputTempArr.length - 3
					&& checkPatternRegex(pptAll[9], token)
					&& checkPatternRegex(pptAll[10], fisrtInputTempArr[i+1])
					&& checkPatternRegex(pptAll[11], fisrtInputTempArr[i+2], ignorecase)
					) {
//				if(i <= fisrtInputTempArr.length - 3) {
//					if(checkPatternRegex("["+constant.IsAlpha+"][']$", token)) {
//						if(checkPatternRegex("^[-–]$", fisrtInputTempArr[i+1])) {
//							if(checkPatternRegex("^li$|^mail.*", fisrtInputTempArr[i+2], ignorecase)) {
								detokenized_text.append(prepend_space).append(token).append(fisrtInputTempArr[i+1]);
								
//								next(tokens, None)  # Advance over the dash
								
								prepend_space = "";
//							}
//						}
//					}
//				}
				
			}else if(checkPatternRegex(pptAll[12], token)) {
				String normalized_quo = token;
				if(checkPatternRegex(pptAll[13], token)) {
					normalized_quo = "\"";
				}
				/***
				quote_counts.put("'", 0);
				quote_counts.put("\"", 0);
				quote_counts.put("``", 0);
				quote_counts.put("`", 0);
				quote_counts.put("''", 0);
				***/
				//quote_counts.put(normalized_quo, null)
				
				if(language == "cs" && token == "„") {
					quote_counts.put(normalized_quo, 0);
				}
				if(language == "cs" && token == "“") {
					quote_counts.put(normalized_quo, 1);
				}
				if(quote_counts.get(normalized_quo)%2 == 0) {
					if(language == "en" && token == "'") {
						if(i > 0) {
							if(checkPatternRegex(pptAll[14], fisrtInputTempArr[i-1])) {
								detokenized_text.append(token);
								prepend_space = " ";
							}
						}
					}else {
						detokenized_text.append(prepend_space).append(token);
						prepend_space = "";
						quote_counts.put(normalized_quo, quote_counts.get(normalized_quo)+1);
					}
				}
				
				
				
			}else if(language == "fi") {
				if(checkPatternRegex(pptAll[15], fisrtInputTempArr[i-1])) {
					if(checkPatternRegex(pptAll[16], token)) {
						detokenized_text.append(prepend_space).append(token);
						prepend_space = " ";
					}
				}
				
			}else {
if(language == "th") {
				
					
					
					if(token.equals("LSSPACELS")) {
						//System.out.println("LSSPACELS");
						detokenized_text.append(prepend_space);
						prepend_space = " ";
						
						
					}else if (checkPatternRegex(pptAll[20], token)) {
						detokenized_text.append(prepend_space).append(token).append(prepend_space);
					}else if(checkPatternRegex(pptAll[22], token) && fisrtInputTempArr[i-1].equals(".")) {
						detokenized_text.append(token);
					}
					else if(i >2 && !checkPatternRegex(pptAll[22], token) && checkPatternRegex(pptAll[22], fisrtInputTempArr[i-1])){
//						System.out.println(token);
						detokenized_text.append(prepend_space).append(token);
					}
					else if(checkPatternRegex(pptAll[21], token)) {
						detokenized_text.append(token).append(prepend_space);
					}else if(i > 2 && checkPatternRegex(pptAll[22], fisrtInputTempArr[i-2]) && fisrtInputTempArr[i-1].equals(".") && checkPatternRegex(pptAll[23], token)) {
						//System.out.println(token);
						detokenized_text.append(prepend_space).append(token);					
					}else if(i > 2 && checkPatternRegex(pptAll[23], fisrtInputTempArr[i-1])  && checkPatternRegex(pptAll[22], token)) {
						
						detokenized_text.append(prepend_space).append(token).append(prepend_space);
					}else if(i>2 && checkPatternRegex(pptAll[24], fisrtInputTempArr[i-1])){
						detokenized_text.append(prepend_space).append(token);
					
					}else {
						detokenized_text.append(token);
						prepend_space = " ";
					}
				}else {
					detokenized_text.append(prepend_space).append(token);
					prepend_space = " ";
				}
				
			}
			
			output = detokenized_text.toString();
			output = getvalueFromReplacement(pptAll[17], output, " ");
			output = getvalueFromReplacement(pptAll[18], output, "");
			
			
			
			
			
			
		}

		return output;
	}
	
private  boolean checkPatternRegex(Pattern r, String token) {
		
		// Create a Pattern object
	      //Pattern r = Pattern.compile(stringRegex);
//		 Pattern r = Pattern.compile(stringRegex,Pattern.CASE_INSENSITIVE); 
	      // Now create matcher object.
	      Matcher m = r.matcher(token);
	      if (m.find( )) {
	    	  return true;
	      }
		return false;
	}

	private  boolean checkPatternRegex( Pattern r, String token, int flag) {
		
		// Create a Pattern object
//	      Pattern r = Pattern.compile(stringRegex);
//		 Pattern r = Pattern.compile(stringRegex,Pattern.CASE_INSENSITIVE); 
	      // Now create matcher object.
	      Matcher m = r.matcher(token);
	      if (m.find( )) {
	    	  return true;
	      }
		return false;
	}

	private  boolean isCJK(String string) {
		int[] koLength1 =  {4352, 4607};
		int[] koLength2 =  {11904, 42191};
		int[] koLength3 =  {43072, 43135};
		int[] koLength4 =  {44032, 55215};
		int[] koLength5 =  {63744, 6425};
		int[] koLength6 =  {65072, 65103};
		int[] koLength7 =  {65381, 65500};
		int[] koLength8 =  {94208, 101119};
		int[] koLength9 =  {110592, 11089};
		int[] koLength10 =  {110960, 111359};
		int[] koLength11 =  {131072, 196607};
		
		int[][] koLength = {koLength1, koLength2, koLength3,koLength4,koLength5,koLength6,koLength7,koLength8,koLength9,koLength10,koLength11};
		
		StringBuffer sb = new StringBuffer();
		char ch[] = string.toCharArray();
		
		for(int i = 0; i < ch.length; i++) {
	         String hexString = Integer.toHexString(ch[i]);
//	         System.out.println("hexString: "+hexString);
	         sb.append(hexString);
	      }
//		System.out.println("sb.toString(): "+sb.toString());
		if(isInteger(sb.toString())) {
		if(!sb.toString().equals("")) {
			if(sb.toString() != null) {
			int result = Integer.parseInt(sb.toString());
			
			for(int i=0;i<koLength.length;i++) {
				int min = koLength[i][0];
				int max = koLength[i][1];
				
				if(result >= min) {
					if(result<= max) {
						return true;
					}
				}
			}
			}
		}
		}

		return false;
	}
	
	public  boolean isInteger(String str) {
	    if (str == null) {
	        return false;
	    }
	    int length = str.length();
	    if (length == 0) {
	        return false;
	    }
	    int i = 0;
	    if (str.charAt(0) == '-') {
	        if (length == 1) {
	            return false;
	        }
	        i = 1;
	    }
	    for (; i < length; i++) {
	        char c = str.charAt(i);
	        if (c < '0' || c > '9') {
	            return false;
	        }
	    }
	    return true;
	}


	private  String getUnescapeXML(String fisrtInputTempStr) {
		String output = fisrtInputTempStr;
		for(int i = 0; i< constant.MOSES_UNESCAPE_XML_REGEXES.length; i++) {
			String[] UNESCAPE_INDEXS = constant.MOSES_UNESCAPE_XML_REGEXES[i];
//			for(int j =0; j< UNESCAPE_INDEXS.length; j++) {
			
			Pattern pattern = Pattern.compile(UNESCAPE_INDEXS[0]);
		    Matcher matcher = pattern.matcher(fisrtInputTempStr);
		    output =  matcher.replaceAll(UNESCAPE_INDEXS[1]); 
				//output = fisrtInputTempStr.replace(UNESCAPE_INDEXS[0], UNESCAPE_INDEXS[1]);
				fisrtInputTempStr = output;
//			}
		}
		return output;
	}

	private  String getvalueFromReplacement(Pattern pattern, String input, String replace) {
		
		//String output = fisrtInputTempStr.replace(aggressiveHyphenSplit[0], aggressiveHyphenSplit[1]);
		
//		Pattern pattern = Pattern.compile(aggressiveHyphenSplit[0]);
	    Matcher matcher = pattern.matcher(input);
	    return matcher.replaceAll(replace); 
		
		//return output;
	}
	
	/*** Common method for create new file ***/
	public void writeFile(String result, String outFilePath) {
		File file = new File(outFilePath);	
		try {
			if(file.createNewFile()) {
				writeLineinFile(result, outFilePath, file);
			}else {
				writeLineinFile(result, outFilePath, file);
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	/*** Common method for write file ***/
	private void writeLineinFile(String result, String outFilePath, File file) {
		
		FileWriter writer;
		try {
			writer = new FileWriter(file, true);
			writer.write(result+"\n");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}



}
