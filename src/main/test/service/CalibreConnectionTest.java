package service;

import application.Application;
import application.service.HtmlService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import application.service.CalibreConnectionService;

import java.io.File;

/**
 * Created by poolebu on 1/16/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class CalibreConnectionTest {

    @Autowired
    CalibreConnectionService calibreConnectionService;


    @Autowired
    HtmlService htmlService;


    @Test
    public void executeCommandTest(){

        String outPut = calibreConnectionService.executeCommand("ls");

        Assert.assertTrue(!outPut.isEmpty());


    }

    public void convertServiceTest(){
        String htmlUrl = "htmlUrl=http://jessewarden.com/2008/11/agile-chronicles-1-stressful.html";
        String calibreConvertLocation = "/Applications/calibre.app/Contents/console.app/Contents/MacOS/ebook-convert";

        String urlToConvert = htmlUrl;
        String outputFormat="pdf";

        String htmlContent = htmlService.getHtmlContent(urlToConvert);
        String bookTitle="ebookContent";

        File file = htmlService.saveHtmlContentToFile(htmlContent, bookTitle);

        String filename = file.getName();


        // 2 correr ebook convert

        calibreConnectionService.convert(file.getName(),outputFormat);


    }


}
