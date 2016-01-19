package service;

import application.Application;
import application.service.HtmlService;
import application.shared.GlobalVariables;
import org.junit.Assert;
import org.junit.Ignore;
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

    @Autowired
    GlobalVariables globalVariables;


    @Test
    @Ignore
    public void executeCommandTest(){

        String outPut = calibreConnectionService.executeCommand("ls");

        Assert.assertTrue(!outPut.isEmpty());


    }

    @Test
    public void convertServiceTest(){
        String htmlUrl = "http://jessewarden.com/2008/11/agile-chronicles-1-stressful.html";
        String calibreConvertLocation = "/Applications/calibre.app/Contents/console.app/Contents/MacOS";
        calibreConnectionService.setCallibreConvertLocation(calibreConvertLocation);

        String urlToConvert = htmlUrl;
        String outputFormat="pdf";

        String htmlContent = htmlService.getHtmlContent(urlToConvert);
        String bookTitle = "ebookContent";

        File file = htmlService.saveHtmlContentToFile(htmlContent,globalVariables.getContentFolder(), bookTitle);

        // 2 correr ebook convert
        calibreConnectionService.convert(file.getPath(),outputFormat);


    }


}
