package application.service;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by poolebu on 1/16/16.
 */
@Service
public class CalibreConnectionService {

	public static final String CONVERSION_SUCCESS = "Output saved to";
	public static final String PDF = "PDF";
	// In case is not in the local path
	String callibreConvertLocation="";

	private final Logger log = LoggerFactory.getLogger(CalibreConnectionService.class);


	public String getCallibreConvertLocation() {
		return callibreConvertLocation;
	}

	public void setCallibreConvertLocation(String callibreConvertLocation) {
		this.callibreConvertLocation = callibreConvertLocation;
	}


	public String convert(String inputFilePath, String format){

		String filePathWithNoSuffix=stripSuffix(inputFilePath);
		String command="";

		// Si hay que transformar a pdf, usar la app wkhtmltopdf, si no ebook-convert de Callibre
		command = ( format.equalsIgnoreCase(PDF) ) ? "/usr/local/bin/wkhtmltopdf.sh " : this.getCallibreConvertLocation() +"ebook-convert ";

		command += inputFilePath + " " + filePathWithNoSuffix+"."+format;


		log.info("Running conversion command:\n"+command);

		String commandResponse = executeCommand(command);

		if (commandResponse.contains(CONVERSION_SUCCESS)){

			log.info(commandResponse);

			return (extractResutlingPath(commandResponse));

		}   else{

			log.error(commandResponse);
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
