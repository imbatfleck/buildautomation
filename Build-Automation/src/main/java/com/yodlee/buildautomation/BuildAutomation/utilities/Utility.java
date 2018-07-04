package com.yodlee.buildautomation.BuildAutomation.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utility {
	
	public static String readFromFile(String path) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(path));
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		String ls = System.getProperty("line.separator");
		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(ls);
		}
		// delete the last new line separator
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		reader.close();

		String content = stringBuilder.toString();
		return content;
	}
	
	public static ArrayList<String> readFromFileList(String path) throws IOException
	{
		ArrayList<String> listItems=new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line = null;
		while ((line = reader.readLine()) != null) {
			listItems.add(line);
		}
		reader.close();

		return listItems;
	}
	public static boolean writeToFile(String fileName,String fileFormat,String fileContent)
	{
		boolean isSuccssWrite=true;
		BufferedWriter bufferedWriter=null;
		File myFile =new File(fileName+"."+fileFormat);
		if (!myFile.exists()) {
            try {
				myFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				isSuccssWrite=false;
			}
        }
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(myFile));
		} catch (IOException e) {
			isSuccssWrite=false;
		}
        try {
			bufferedWriter.write(fileContent);
		} catch (IOException e) {
			isSuccssWrite=false;
		}
		try {
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return isSuccssWrite;
	}
	public static long getCurrentTImeStamp() throws ParseException
	{
		 Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		 String str=timestamp.toString();
	     SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	     Date date = df.parse(str);
	     long epoch = date.getTime();
	     return epoch;
	}
	public static String createHtml()
	{
		return null;
	}

}
