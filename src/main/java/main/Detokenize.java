package main;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import LSLanguageid.LSLanguageid;



import java.nio.charset.StandardCharsets;

import util.Constant;
public class Detokenize {
	
//	static String rawInput = "&lt; ใน \\@\\-\\@ นิยาย ที่ ผม เป็น คน เขียน LSSPACELS ไม่มี ใคร ต้องตา ย &gt;";
//	static byte[] byteArrray = rawInput.getBytes();
//	public static String inputStr = "䍒 ใน \\@\\-\\@ นิยาย ที่ ผม เป็น คน เขียน LSSPACELS ไม่มี ใคร ต้องตา ย &gt;";
//	public static String inputStr = "&lt; I declare resumed the session of the European Parliament adjourned on Friday 17 December 1999 , and I would like once again to wish you a happy new year in the hope that you enjoyed a pleasant festive period .";
	//public static String inputStr = "Please rise , then , for this minute &apos; s silence .";
	//	public static String inputStr = new String(byteArrray, StandardCharsets.UTF_8);
	
	private String outputTempPathForMultiThread = null;
	private String outputFilePathFinal = null;
	 private Constant constant = new Constant(); 
	 private LSLanguageid languageid = new LSLanguageid();
	 int ignorecase = 2;
	 
	 private boolean deleteFileFlag = false;
	 private int countFileCreatedNum = 0;
	 private int fileCreateNum = 0;
		
	 
	 Pattern ppt1 = Pattern.compile("^[" + constant.IsSc + "\\(\\[\\{\\¿\\¡]+$");
	 Pattern ppt2 = Pattern.compile("^[\\,\\.\\?\\!\\:\\;\\\\%\\}\\]\\)]+$");
	 Pattern ppt3 = Pattern.compile("^[\\?\\!\\:\\;\\\\%]$");
	 Pattern ppt4 = Pattern.compile("^[']["+constant.IsAlpha+"}]");
	 Pattern ppt5 = Pattern.compile("^[0-9]+$");
	 Pattern ppt6 = Pattern.compile("^[.,]$");
	 Pattern ppt7 = Pattern.compile("^[0-9]+$");
	 Pattern ppt8 = Pattern.compile("["+constant.IsAlpha+"][']$");
	 Pattern ppt9 = Pattern.compile("^["+constant.IsAlpha+"]");
	 Pattern ppt10 = Pattern.compile("["+constant.IsAlpha+"][']$");
	 Pattern ppt11 = Pattern.compile("^[-–]$");
	 Pattern ppt12 = Pattern.compile("^li$|^mail.*");
	 Pattern ppt13 = Pattern.compile("^[\'\"„“`]+$");
	 Pattern ppt14 = Pattern.compile("^[„“”]+$");
	 Pattern ppt15 = Pattern.compile("[s]$");
	 Pattern ppt16 = Pattern.compile(":$");
	 Pattern ppt17 = Pattern.compile(constant.FINNISH_REGEX_REG);
	 Pattern ppt18 = Pattern.compile("\\s{2,}");
	 Pattern ppt19 = Pattern.compile("^[\\s]");
	 Pattern ppt20 = Pattern.compile(" \\@\\-\\@ ");
	 Pattern ppt21 = Pattern.compile("[a-zA-Z]");
	 Pattern ppt22 = Pattern.compile("^[•‣◦⁃∙]");
	 Pattern ppt23 = Pattern.compile("[\\d]");
	 Pattern ppt24 = Pattern.compile("[\\D]");
	 Pattern ppt25 = Pattern.compile("[)}\\]]");

	 
	 Pattern[] pptAll = {ppt1, ppt2, ppt3, ppt4, ppt5, ppt6, ppt7, ppt8, ppt9, ppt10, 
			 ppt11, ppt12, ppt13, ppt14, ppt15, ppt16, ppt17, ppt18, ppt19, ppt20,
			 ppt21, ppt22, ppt23, ppt24, ppt25
	 };
	
	//static String language = "en";
	
	 
	 String temp2;
	public Detokenize() {
		constant = new Constant();
	}
	
	class CountFile extends TimerTask {
		public void run() {
			int count = 0;
			getFile(outputTempPathForMultiThread);
			
//			if(countFileCreatedNum == fileCreateNum) {
//				System.out.println("Should Break" );
//				SummaryResult();
//				  new CountFile().cancel();
//				
//				
//				
//        	}
		}
	
	
	
	private void getFile(String outputTempPath) {
		    File f = new File(outputTempPath); 
		    File[] files = f.listFiles();
		    
		    if (!deleteFileFlag) {
		        for (int i = 0; i < files.length; i++) {
		        	
		        	countFileCreatedNum = files.length;
//		        	System.out.println("Number of files: "+countFileCreatedNum );
		        	//System.out.println("countFileCreatedNum: "+countFileCreatedNum);
					//System.out.println("fileCreateNum: "+fileCreateNum);
		        	long progressLong = (countFileCreatedNum/fileCreateNum)*100;
		        	
		        	if(progressLong == 100) {
		        		System.out.println("Create output temp file finish 100%");
		        	}else if(progressLong >= 90){
		        		System.out.println("Create output temp file progress: "+progressLong+" %");
		        	}else if(progressLong >= 80){
		        		System.out.println("Create output temp file progress: "+progressLong+" %");
		        	}else if(progressLong >= 70){
		        		System.out.println("Create output temp file progress: "+progressLong+" %");
		        	}else if(progressLong >= 60){
		        		System.out.println("Create output temp file progress: "+progressLong+" %");
		        	}else if(progressLong >= 50){
		        		System.out.println("Create output temp file progress: "+progressLong+" %");
		        	}else if(progressLong >= 40){
		        		System.out.println("Create output temp file progress: "+progressLong+" %");
		        	}else if(progressLong >= 30){
		        		System.out.println("Create output temp file progress: "+progressLong+" %");
		        	}else if(progressLong >= 20){
		        		System.out.println("Create output temp file progress: "+progressLong+" %");
		        	}else if(progressLong >= 10){
		        		System.out.println("Create output temp file progress: "+progressLong+" %");
		        	}else if(progressLong >= 5){
		        		System.out.println("Create output temp file progress: "+progressLong+" %");
		        	}
		        	
		        	
		        	
		        	if(countFileCreatedNum == fileCreateNum) {
//						System.out.println("Should Break" );
						
						cancel();
						
						SummaryResult();
		        	}
		        	
		        	if(deleteFileFlag) {
		        		break;
		        	}
		        	
		        	
//		            File file = files[i]; 
//		     
//		            if (file.isDirectory()) {    
//		                 getFile(file.getAbsolutePath());  
//		            } 
		        } 
		}
			
		}



	public void SummaryResult() {
		  File f = new File(outputTempPathForMultiThread); 
		    File[] files = f.listFiles();
		    if(files != null) {
		    for(int i = 0; i< files.length; i++) {
//		    	 System.out.println("["+i+"] "+"file: "+files[i].toString());
		    	String outputFilePathTemp = files[i].toString();
		    	
		    	 List<String> listFile = new ArrayList<String>();
		    	 listFile.add(outputFilePathTemp);  
		    	String resultTemp = null;
		    	try {
					resultTemp = getResultStringFromFile(outputFilePathTemp);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
		    	writeFile(resultTemp, outputFilePathFinal);
		    }
		    }
		    
		    deleteDirectoryOutputTemp(outputTempPathForMultiThread);
		    
		    deleteFileFlag = true;
		    
//		    System.out.println("Process detokenize finish ");
		    
		    
		    
		   
		
		
	}



	private void deleteDirectoryOutputTemp(String outputTempPath) {
		Path path = Paths.get(outputTempPath);
		
		  File f = new File(outputTempPath); 
		    File[] files = f.listFiles();
		    
		   
		    if (files != null) {
		        for (File file : files) {
		            deleteDirectory(file);
		        }
		    }
		
		try {
			Files.delete(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}

		
	}



	private void deleteDirectory(File file) {
		File myObj = file;
	    if (myObj.delete()) { 
//	      System.out.println("Deleted the file: " + myObj.getName());
	    } else {
//	      System.out.println("Failed to delete the file.");
	    } 
		
	}



	public String getResultStringFromFile(String inputFilePath) throws FileNotFoundException {
		
		FileInputStream inputString = new FileInputStream(inputFilePath);
		
		DataInputStream in = new DataInputStream(inputString);
		BufferedReader br1 = new BufferedReader(new InputStreamReader(in));
		StringBuffer inputBuf = new StringBuffer();
		
//		long BeginReadFile = System.currentTimeMillis();
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
//		long EndReadFile = System.currentTimeMillis();
//		long TotalReadFile = EndReadFile - BeginReadFile;
//		System.out.println("TotalReadFile: "+TotalReadFile);
		
		return new String(inputBuf);
	}
		
	}



	public void processDetokenizeFile(String inputFilePath, String language) {
		
		String outputFilePath = null;
		
		String[] outputPathArr = inputFilePath.split("/");
		
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i< outputPathArr.length-1;i++) {
			sb.append(outputPathArr[i]).append("/");
		}
		outputFilePath = sb.toString()+"Output_"+outputPathArr[outputPathArr.length-1];
		
		processDetokenizeFile(inputFilePath, outputFilePath, language);
	}
	
	public void processDetokenizeFileMultiThread(String inputFilePath, String outputFilePath, String language, int threadNum) {
//		System.out.println("outputFilePath: "+outputFilePath);
//		outputTempPathForMultiThread = outputFilePath+"Output/";
		String[] outputFileNameArr  = outputFilePath.split("/");
		String outputFileNameStr = outputFileNameArr[outputFileNameArr.length-1];
		outputTempPathForMultiThread = constant.TEMP_FOLDER_PROCESS+outputFileNameStr.replace(".txt", "")+"/";
//		System.out.println("outputTempPathForMultiThread: "+outputTempPathForMultiThread);
		outputFilePathFinal = outputFilePath;
//		System.out.println("outputFilePathFinal: "+outputFilePathFinal);
		
		if(!folderExisting(outputTempPathForMultiThread)) {
			createDirectory(outputTempPathForMultiThread);
		}
		
		double chunkSizeDouble = 0;
		int chunkSizeInt = 0;
		
		//Count total line
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(inputFilePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int lines = 0;
		try {
			while (reader.readLine() != null) lines++;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("Total Line:"+lines);
		chunkSizeDouble = lines*0.00074;
		chunkSizeInt = (int) chunkSizeDouble;
//		System.out.println("chunkSizeInt:"+chunkSizeInt);
		
		FileInputStream inputString = null;
		try {
			inputString = new FileInputStream(inputFilePath);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		DataInputStream in = new DataInputStream(inputString);
		BufferedReader br1 = new BufferedReader(new InputStreamReader(in));
		//StringBuffer inputBuf = new StringBuffer();
		
		long BeginReadFile = System.currentTimeMillis();
		try {
			String line = null;
			int chunkCounkCount = 0;
			int countLine = 0;
			StringBuffer sb = new StringBuffer();
			ExecutorService service = Executors.newFixedThreadPool(threadNum);
			while ((line = br1.readLine()) != null){
				if(chunkCounkCount<=chunkSizeInt) {
					sb.append("\n").append(line);
					chunkCounkCount++;
					countLine++;
					
					if(countLine == chunkSizeInt-1) {
						fileCreateNum++;
						service.execute(new DetokenizeMultithread(
								sb,
								outputTempPathForMultiThread+countLine+".txt",
								language, constant, pptAll));
						chunkCounkCount = 0;
						sb = new StringBuffer();
					}
				}else {
					fileCreateNum++;
					service.execute(new DetokenizeMultithread(
									sb,
									outputTempPathForMultiThread+countLine+".txt",
									language, constant, pptAll));
					chunkCounkCount = 0;
					sb = new StringBuffer();
				}				
			}
			service.shutdown();
			
			Timer timer = new Timer();
			timer.schedule(new CountFile(), 0, 1000);
//			System.out.println("Read file finish.!!!!!");
			
			
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
		
		
		
		
		
		
		
		
	}
	
	
	
	private void createDirectory(String outputFilePath) {
		File theDir = new File(outputFilePath);
		theDir.mkdirs();
		
	}

	private boolean folderExisting(String outputFilePath) {
		 File file = new File(outputFilePath);
		 if (file.isDirectory()) {
	            return true;
	        }
		 return false;
	}

	public void processDetokenizeFile(String inputFilePath, String outputFilePath, String language) {
		// TODO Auto-generated method stub
		//System.out.println("outputFilePath: "+outputFilePath);
		
		FileInputStream inputString = null;
		try {
			inputString = new FileInputStream(inputFilePath);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		DataInputStream in = new DataInputStream(inputString);
		BufferedReader br1 = new BufferedReader(new InputStreamReader(in));
		StringBuffer inputBuf = new StringBuffer();
		
//		long BeginReadFile = System.currentTimeMillis();
		try {
			String line = null;
			while ((line = br1.readLine()) != null){
				String outputAfterDetok = processDetokenizeStr(line, language);
				writeFile(outputAfterDetok, outputFilePath);
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
//		long EndReadFile = System.currentTimeMillis();
//		long TotalReadFile = EndReadFile - BeginReadFile;
//		System.out.println("TotalReadFile: "+TotalReadFile);
		
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
		
		
		
		fisrtInputTempStr = getvalueFromReplacement(ppt20, fisrtInputTempStr, constant.AGGRESSIVE_HYPHEN_SPLIT[1]);
		
		fisrtInputTempStr = getUnescapeXML(fisrtInputTempStr);
		
		String[] fisrtInputTempArr = fisrtInputTempStr.split(" ");
		
//		for(String token: fisrtInputTempArr) {
////			System.out.println("Top Token: "+token);
//		}
		
		String prepend_space = " ";
		StringBuffer detokenized_text = new StringBuffer();
		
		for(int i = 0; i<fisrtInputTempArr.length; i++ ) {
			String token = fisrtInputTempArr[i];
			//System.out.println("i="+i+" token: "+token);
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
			}else if(checkPatternRegex(ppt1, token)) {
				detokenized_text.append(prepend_space).append(token);
				prepend_space = "";
			}else if(checkPatternRegex(ppt2, token) ){
					if( language == "fr" && checkPatternRegex(ppt3, token)) {
						detokenized_text.append(" ");
					}else {
						detokenized_text.append(token);
					}

				

					
				
			}else if(language == "en" && i >1 && checkPatternRegex(ppt4, token) ) {
				detokenized_text.append(token);
				prepend_space = " ";
			}
			
			
			else if(language == "cs" && i >1 
					&& checkPatternRegex(ppt5, fisrtInputTempArr[fisrtInputTempArr.length-2]) 
					&& checkPatternRegex(ppt6, fisrtInputTempArr[fisrtInputTempArr.length-1])
					&& checkPatternRegex(ppt7, token)
					) {
				detokenized_text.append(token);
				prepend_space = " ";
			}else if((language == "fr" || language == "it" || language == "ga") 
					&& (i <= fisrtInputTempArr.length - 2)
					&& checkPatternRegex(ppt8, token)
					&& (checkPatternRegex(ppt9, fisrtInputTempArr[i+1])))
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
					&& checkPatternRegex(ppt10, token)
					&& checkPatternRegex(ppt11, fisrtInputTempArr[i+1])
					&& checkPatternRegex(ppt12, fisrtInputTempArr[i+2], ignorecase)
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
				
			}else if(checkPatternRegex(ppt13, token)) {
				String normalized_quo = token;
				if(checkPatternRegex(ppt14, token)) {
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
							if(checkPatternRegex(ppt15, fisrtInputTempArr[i-1])) {
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
				if(checkPatternRegex(ppt16, fisrtInputTempArr[i-1])) {
					if(checkPatternRegex(ppt17, token)) {
						detokenized_text.append(prepend_space).append(token);
						prepend_space = " ";
					}
				}
				
			}else {
				if(language == "th" || language.equals("th")) {
				
					
					
					if(token.equals("LSSPACELS")) {
						//System.out.println("LSSPACELS");
						detokenized_text.append(prepend_space);
						prepend_space = " ";
						
						
					}else if (checkPatternRegex(ppt21, token)) {
						detokenized_text.append(prepend_space).append(token).append(prepend_space);
					}else if(checkPatternRegex(ppt23, token) && fisrtInputTempArr[i-1].equals(".")) {
						detokenized_text.append(token);
					}
					else if(i >2 && !checkPatternRegex(ppt23, token) && checkPatternRegex(ppt23, fisrtInputTempArr[i-1])){
//						System.out.println(token);
						detokenized_text.append(prepend_space).append(token);
					}
					else if(checkPatternRegex(ppt22, token)) {
						detokenized_text.append(token).append(prepend_space);
					}else if(i > 2 && checkPatternRegex(ppt23, fisrtInputTempArr[i-2]) && fisrtInputTempArr[i-1].equals(".") && checkPatternRegex(ppt24, token)) {
						//System.out.println(token);
						detokenized_text.append(prepend_space).append(token);					
					}else if(i > 2 && checkPatternRegex(ppt24, fisrtInputTempArr[i-1])  && checkPatternRegex(ppt23, token)) {
						
						detokenized_text.append(prepend_space).append(token).append(prepend_space);
					}else if(i>2 && checkPatternRegex(ppt25, fisrtInputTempArr[i-1])){
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
	
private  boolean checkPatternRegex( Pattern r, String token) {
	//System.out.println("Regex: "+stringRegex); 
		
		// Create a Pattern object
	     // Pattern r = Pattern.compile(stringRegex);
//		 Pattern r = Pattern.compile(stringRegex,Pattern.CASE_INSENSITIVE); 
	      // Now create matcher object.
	      Matcher m = r.matcher(token);
	      if (m.find( )) {
	    	  return true;
	      }
		return false;
	}

	private  boolean checkPatternRegex(Pattern r, String token, int flag) {
		
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
