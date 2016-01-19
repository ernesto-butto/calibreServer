package application.service;

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
        try {


            file = new File(outputFolder + "/"+filename+".html");

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
