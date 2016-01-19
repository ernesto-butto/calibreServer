package application.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by poolebu on 1/16/16.
 */
@Service
public class CalibreConnectionService {

	// In case is not in the local path
	String callibreConvertLocation="";

	public String getCallibreConvertLocation() {
		return callibreConvertLocation;
	}

	public void setCallibreConvertLocation(String callibreConvertLocation) {
		this.callibreConvertLocation = callibreConvertLocation;
	}


	public String convert(String inputFilePath, String format){

		String command = "ebook-convert "+inputFilePath + " " + inputFilePath+"."+format;

		// add the ebook-convert location path if needed
		command=this.getCallibreConvertLocation() + "/"+command;

		return executeCommand(command);

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
