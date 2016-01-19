package application.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by poolebu on 1/16/16.
 */
@Service
public class CalibreConnectionService {

	public static final String CONVERSION_SUCCESS = "Output saved to";
	// In case is not in the local path
	String callibreConvertLocation="";

	public String getCallibreConvertLocation() {
		return callibreConvertLocation;
	}

	public void setCallibreConvertLocation(String callibreConvertLocation) {
		this.callibreConvertLocation = callibreConvertLocation;
	}


	public String convert(String inputFilePath, String format){

		String filePathWithNoSuffix=stripSuffix(inputFilePath);
		String resultingPath = "";
		String command = "ebook-convert "+inputFilePath + " " + filePathWithNoSuffix+"."+format;

		// add the ebook-convert location path if needed
		command=this.getCallibreConvertLocation() + "/"+command;

		String commandResponse = executeCommand(command);

		if (commandResponse.contains("Output saved to")){

			return (extractResutlingPath(commandResponse));

		}   else{

			return null;

		}

	}

	private String extractResutlingPath(String commandResponse) {

		String[] arrayOfLines = commandResponse.split("\n");
		String ouputFilePath = null;
		for (String line : arrayOfLines){

			if (line.contains(CONVERSION_SUCCESS)){

				ouputFilePath = line.split(" ")[line.split(" ").length-1];


			}

		}

		return ouputFilePath;
	}

	private String stripSuffix(String inputFilePath) {

		return FilenameUtils.removeExtension(inputFilePath);

	}

	public String executeCommand(String command) {

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader =
					new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}



}
