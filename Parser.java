package pdfParser;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Arrays;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
/**
 * PDF Parser class
 * Reads in a given .pdf file, and gives the ability to search 
 * 
 * Improve: 
 * Class design - implement child classes
 * Prompt user for search terms/rule 
 * Output to excel file, not .txt file, and extract relevant information (HR #, page #, bill extension, etc.) 
 *
 * @author christianschoeberl
 *
 */
public class Parser {
	public static void main(String args[]) throws IOException{
		File file = new File("/Users/christianschoeberl/Downloads/89Hres.Sess2.Index.pdf"); //load in file
		PDDocument toReadPdf = PDDocument.load(file); //convert to pdf
		
		PDFTextStripper pdfScanner = new PDFTextStripper(); //creating pdfScanner
		String text = pdfScanner.getText(toReadPdf); //parsing pdf into string, better way to do this? word array ideal
		
		long textSize = text.length(); //total size of string
		String searchText = "H. Res."; 
		int[] hResArray = new int[(int)(textSize/100)]; //hResArray contains indexes of H for H. Res, in order to eventually
											//make an array of H. Res text one by one (not just full document)
		int hResCounter = 0;
		int hArray = 0; 
		int charCount = 0;
			for(int i=0; i<textSize; i++){
				char testChar = text.charAt(i); 
				boolean equals = false;
				if(testChar == 'H'){
					String testRes = "H"; 
					for(int t=1; t<6; t++){
						char nextChar = text.charAt(i+t);
						testRes+=nextChar; 
					}
					for(int resTestCount=0; resTestCount<6; resTestCount++){
						if(resTestCount+4<=text.length()){
							if(searchText.charAt(resTestCount)==testRes.charAt(resTestCount)){
								equals = true; 
							} 
							else{
								equals = false; 
							}
						}
						else{
							equals = false; 
						}
					}
					if(equals==true){
						hResArray[hResCounter]=i; 
						hResCounter++; 
					}
				}
			}
		String[] hResString = new String[hResCounter];//seems like we can find, so check after this for if we can search
		for(int i=0; i<hResCounter; i++){//fix this section, can't use while loop for j, find better way to reference 
			if(i+1<=hResCounter){
				int j = i+1;
				int startIndex = hResArray[i];
				int endIndex = hResArray[j];
				if(endIndex!=0){
					String hRestText = text.substring(startIndex, endIndex); 
					hResString[i]=hRestText;
				}
				else{
					break; 
				}
			}
		}
		String resTest = "Providing for the consideration"; 
		String[] foundRes = new String[hResCounter]; 
		int foundResCounter = 0;
		boolean firstHyphen = true; 
		for(int i=0; i<hResCounter-2; i++){
			int hyphenIndex = 0; 
			firstHyphen = true; 
			String resText = ""; 
			String testString = ""; 
				resText = hResString[i];
				testString = ""; 
				hyphenIndex = 0; 
				for(int findHyphen=0; findHyphen<resText.length(); findHyphen++){
					char testHyphen = resText.charAt(findHyphen);
					if(findHyphen+1<resText.length()){
						char testHyphen2=resText.charAt(findHyphen+1);
							if(testHyphen2=='-'){
								hyphenIndex=findHyphen+2; 
								break; 
							}
						}
				}
			for(int buildString=hyphenIndex; buildString<resText.length(); buildString++){
				char addChar = resText.charAt(buildString); 
				testString+=addChar; 
			}
			int testStringCount=0;
			//int testStringLength = testString.length();
			for(testStringCount=0; testStringCount<31; testStringCount++){
				if(testString.charAt(testStringCount)==resTest.charAt(testStringCount)){
					testStringCount++; 
				}
				else{
					break;
				}
			}
			if(testStringCount>=25){
				foundRes[foundResCounter] = hResString[i]; 
				foundResCounter++; 
			}
		}
		String resTest2 = "Agreeing to the Senate amendment"; 
		for(int i=0; i<hResCounter-2; i++){
			String resText2 = hResString[i];
			String testString = ""; 
			int hyphenIndex = 0; 
			for(int findHyphen=0; findHyphen<resText2.length(); findHyphen++){
				char testHyphen = resText2.charAt(findHyphen);
				if(Character.isDigit(testHyphen)==true&&findHyphen+1<=resText2.length()){
					char testHyphen2=resText2.charAt(findHyphen+1);
					if(testHyphen2=='-'){
						hyphenIndex=findHyphen+2; 
						break; 
					}
				}
			}
			for(int buildString=hyphenIndex; buildString<resText2.length(); buildString++){
				char addChar = resText2.charAt(buildString); 
				testString+=addChar; 
			}
			int testStringCount=0;
			int testStringLength = testString.length(); 
			for(testStringCount=0; testStringCount<31; testStringCount++){
				if(testString.charAt(testStringCount)==resTest2.charAt(testStringCount)){
					continue;
				}
				else{
					break;
				}
			}
			if(testStringCount>=25){
				foundRes[foundResCounter] = hResString[i]; 
				foundResCounter++; 
			}
		}
		String resTest3 = "Taking"; 
		for(int i=0; i<hResCounter-2; i++){
			String resText3 = hResString[i];
			String testString = ""; 
			int hyphenIndex = 0; 
			for(int findHyphen=0; findHyphen<resText3.length(); findHyphen++){
				char testHyphen = resText3.charAt(findHyphen);
				if(Character.isDigit(testHyphen)==true&&findHyphen+1<=resText3.length()){
					char testHyphen2=resText3.charAt(findHyphen+1);
					if(testHyphen2=='-'){
						hyphenIndex=findHyphen+2; 
						break; 
					}
				}
			}
			for(int buildString=hyphenIndex; buildString<resText3.length(); buildString++){
				char addChar = resText3.charAt(buildString); 
				testString+=addChar; 
			}
			int testStringCount=0;
			int testStringLength = testString.length(); 
			for(testStringCount=0; testStringCount<6; testStringCount++){
				if(testString.charAt(testStringCount)==resTest3.charAt(testStringCount)){
					continue;
				}
				else{
					break;
				}
			}
			if(testStringCount>4){
				foundRes[foundResCounter] = hResString[i]; 
				foundResCounter++; 
			}
		}
		String resTest4 = "Providing for consideration"; 
		for(int i=0; i<hResCounter-2; i++){//weird times at the circle k
			String resText4 = hResString[i];
			int resTextLength = resText4.length(); 
			String testString = ""; 
			int hyphenIndex = 0; 
			if(resTextLength>0&&resTextLength<650){
				for(int findHyphen=0; findHyphen<resText4.length(); findHyphen++){
					char testHyphen = resText4.charAt(findHyphen);
					if(Character.isDigit(testHyphen)==true&&findHyphen+1<=resText4.length()){
						char testHyphen2=resText4.charAt(findHyphen+1);
						if(testHyphen2=='-'){
							hyphenIndex=findHyphen+2; 
							break;
						}
					}
				}
			}
			if(hyphenIndex<100){
				for(int buildString=hyphenIndex; buildString<resText4.length(); buildString++){
					char addChar = resText4.charAt(buildString); 
					testString+=addChar; 
				}
			}
			int testStringCount=0;
			int testStringLength = testString.length(); 
			if(testString.length()>10){
				for(testStringCount=0; testStringCount<resTest4.length(); testStringCount++){
					if(testString.charAt(testStringCount)==resTest4.charAt(testStringCount)){
						continue;
					}
					else{
						break;
					}
				}
			}
			if(testStringCount>=20){
				foundRes[foundResCounter] = hResString[i]; 
				foundResCounter++; 
			}
		}
		String resTest5 = "Waiving"; 
		for(int i=0; i<hResCounter-2; i++){
			String resText5 = hResString[i];
			String testString = ""; 
			int hyphenIndex = 0; 
			for(int findHyphen=0; findHyphen<resText5.length(); findHyphen++){
				char testHyphen = resText5.charAt(findHyphen);
				if(Character.isDigit(testHyphen)==true&&findHyphen+1<=resText5.length()){
					char testHyphen2=resText5.charAt(findHyphen+1);
					if(testHyphen2=='-'){
						hyphenIndex=findHyphen+2; 
						break; 
					}
				}
			}
			for(int buildString=hyphenIndex; buildString<resText5.length(); buildString++){
				char addChar = resText5.charAt(buildString); 
				testString+=addChar; 
			}
			int testStringCount=0;
			int testStringLength = testString.length(); 
			for(testStringCount=0; testStringCount<7; testStringCount++){
				if(testString.charAt(testStringCount)==resTest5.charAt(testStringCount)){
					continue;
				}
				else{
					break;
				}
			}
			if(testStringCount>=5){
				foundRes[foundResCounter] = hResString[i]; 
				foundResCounter++; 
			}
		}
		String resTest6 = "Providing for the consideration"; 
		for(int i=0; i<hResCounter-2; i++){//weird times at the circle k
			String resText6 = hResString[i];
			int resTextLength = resText6.length(); 
			String testString = ""; 
			int hyphenIndex = 0; 
			if(resTextLength>0&&resTextLength<650){
				for(int findHyphen=0; findHyphen<resText6.length(); findHyphen++){
						char testHyphen = resText6.charAt(findHyphen);
						if(Character.isDigit(testHyphen)==true&&findHyphen+1<=resText6.length()){
							char testHyphen2=resText6.charAt(findHyphen+1);
							if(testHyphen2=='-'){
								hyphenIndex=findHyphen+2; 
								break; 
							}
						}
				}
			}
			if(hyphenIndex<100){
				for(int buildString=hyphenIndex; buildString<resText6.length(); buildString++){
					char addChar = resText6.charAt(buildString); 
					testString+=addChar; 
				}
			}
			int testStringCount=0;
			int testStringLength = testString.length(); 
			if(testString.length()>10){
				for(testStringCount=0; testStringCount<resTest6.length(); testStringCount++){
					if(testString.charAt(testStringCount)==resTest6.charAt(testStringCount)){
						continue;
					}
					else{
						break;
					}
				}
			}
			if(testStringCount>=23){
				foundRes[foundResCounter] = hResString[i]; 
				foundResCounter++; 
			}
		}
		String resTest7 = "-Providing for the consideration"; 
		for(int i=0; i<hResCounter-2; i++){//weird times at the circle k
			String resText7 = hResString[i];
			int resTextLength = resText7.length(); 
			String testString = ""; 
			int hyphenIndex = 0; 
			if(resTextLength>0&&resTextLength<650){
				for(int findHyphen=0; findHyphen<resText7.length(); findHyphen++){
						char testHyphen = resText7.charAt(findHyphen);
						if(Character.isDigit(testHyphen)==true&&findHyphen+1<=resText7.length()){
							char testHyphen2=resText7.charAt(findHyphen+1);
							if(testHyphen2=='-'){
								hyphenIndex=findHyphen+2; 
								break; 
							}
						}
				}
			}
			if(hyphenIndex<100){
				for(int buildString=hyphenIndex; buildString<resText7.length(); buildString++){
					char addChar = resText7.charAt(buildString); 
					testString+=addChar; 
				}
			}
			int testStringCount=0;
			int testStringLength = testString.length(); 
			if(testString.length()>10){
				for(testStringCount=0; testStringCount<resTest7.length(); testStringCount++){
					if(testString.charAt(testStringCount)==resTest7.charAt(testStringCount)){
						continue;
					}
					else{
						break;
					}
				}
			}
			if(testStringCount>=23){
				foundRes[foundResCounter] = hResString[i]; 
				foundResCounter++; 
			}
		}
		String resTest8 = "-Providing for consideration"; 
		for(int i=0; i<hResCounter-2; i++){//weird times at the circle k
			String resText8 = hResString[i];
			int resTextLength = resText8.length(); 
			String testString = ""; 
			int hyphenIndex = 0; 
			if(resTextLength>0&&resTextLength<650){
				for(int findHyphen=0; findHyphen<resText8.length(); findHyphen++){
					char testHyphen = resText8.charAt(findHyphen);
					if(Character.isDigit(testHyphen)==true&&findHyphen+1<=resText8.length()){
						char testHyphen2=resText8.charAt(findHyphen+1);
						if(testHyphen2=='-'){
							hyphenIndex=findHyphen+2; 
							break;
						}
					}
				}
			}
			if(hyphenIndex<100){
				for(int buildString=hyphenIndex; buildString<resText8.length(); buildString++){
					char addChar = resText8.charAt(buildString); 
					testString+=addChar; 
				}
			}
			int testStringCount=0;
			int testStringLength = testString.length(); 
			if(testString.length()>10){
				for(testStringCount=0; testStringCount<resTest8.length(); testStringCount++){
					if(testString.charAt(testStringCount)==resTest8.charAt(testStringCount)){
						continue;
					}
					else{
						break;
					}
				}
			}
			if(testStringCount>=20){
				foundRes[foundResCounter] = hResString[i]; 
				foundResCounter++; 
			}
		}
		File output = new File("89th_Sess2_Found"); 
		FileWriter writer = new FileWriter(output); 
		try{
			writer.write(Arrays.toString(foundRes)+System.lineSeparator()); 
		}catch (IOException e){
			e.printStackTrace(); 
		}
		finally{
			writer.close(); 
		}
		System.out.printf("File is located:%s%n", output.getAbsolutePath()); 
		System.out.print(foundResCounter); 
	}
}
