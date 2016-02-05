package application.service;

import application.controller.ConvertController;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;

/**
 * Created by poolebu on 1/16/16.
 */
@Service
public class CalibreConnectionService {

	@Autowired
	HtmlService htmlService;

	@Autowired
	SharedServices sharedServices;

	public static final String CONVERSION_SUCCESS = "Output saved to";
	// In case is not in the local path
	String callibreConvertLocation="";

	private final Logger log = LoggerFactory.getLogger(CalibreConnectionService.class);


	public String getCallibreConvertLocation() {
		return callibreConvertLocation;
	}

	public void setCallibreConvertLocation(String callibreConvertLocation) {
		this.callibreConvertLocation = callibreConvertLocation;
	}


	public String convertUsingCallibre(String inputFilePath, String format){

		String filePathWithNoSuffix=stripSuffix(inputFilePath);

		String command = "ebook-convert "+inputFilePath + " " + filePathWithNoSuffix+"."+format;

		// add the ebook-convert location path if needed
		command=this.getCallibreConvertLocation() +command;

		String commandResponse = sharedServices.executeCommand(command);

		if (commandResponse.contains("Output saved to")){

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


	public String convert(@RequestParam(value = "htmlUrl", required = true) String htmlUrl, @RequestParam(value = "title", required = true) String title, @RequestParam(value = "outputFormat", defaultValue = "mobi") String outputFormat, ConvertController convertController) {

        String outputPath;// 1 Guardar el html en local
        log.info("Retrieving html content form url");
        String htmlContent = htmlService.getHtmlContent(htmlUrl);

        log.info("Saving html content to file");
        File htmlContentFile = htmlService.saveHtmlContentToFile(htmlContent, sharedServices.getContentFolder(), title);

        // 2 correr ebook convert
        log.info("Converting html content");
        outputPath = convertUsingCallibre(htmlContentFile.getAbsolutePath(), outputFormat);

        if (outputPath == null) {
            log.error("There was an error converting the ebook ");
            throw new RuntimeException("There was an error converting the ebook");

        }
        return outputPath;
    }
}
