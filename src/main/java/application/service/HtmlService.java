package application.service;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Created by poolebu on 1/16/16.
 */
@Service
public class HtmlService {

    private final Logger log = LoggerFactory.getLogger(HtmlService.class);


    public String getHtmlContent(String targetUrl){

        String content = null;

        URLConnection connection = null;

        try {

            connection =  new URL(targetUrl).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();

        }catch ( Exception ex ) {

            ex.printStackTrace();

        }

        return content;

    }

    public File saveHtmlContentToFile(String htmlContent,String outputFolder,String filename){
        File file = null;

        String htmlFile = outputFolder + "/"+filename+".html";

        try {

            log.info("Saving html content to "+htmlFile);
            file = new File(htmlFile);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(htmlContent);
            bw.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;

    }




}
