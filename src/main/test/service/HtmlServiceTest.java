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

import java.io.File;

/**
 * Created by poolebu on 1/16/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration

public class HtmlServiceTest {


    @Autowired
    HtmlService htmlService;

    @Test
    public void getHtmlContentTest(){

        String urlToConvert = "http://jessewarden.com/2008/11/agile-chronicles-1-stressful.html";

        String htmlToConvert = htmlService.getHtmlContent(urlToConvert);

        Assert.assertTrue(!htmlToConvert.isEmpty());
    }

    @Test
    public void getHtmlContentAndSaveToFile(){

        HtmlService htmlService = new HtmlService();

        String urlToConvert = "http://jessewarden.com/2008/11/agile-chronicles-1-stressful.html";

        String htmlContent = htmlService.getHtmlContent(urlToConvert);

        File file = htmlService.saveHtmlContentToFile(htmlContent, "ebookContent");

        Assert.assertTrue(file.exists());
    }

}
