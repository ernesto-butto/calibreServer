package service;

import application.Application;
import application.service.WkHtmlToPdfService;
import application.shared.GlobalServices;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;

/**
 * Created by poolebu on 2/4/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class WkHtmlToPdfServiceTest {

    @Autowired
    WkHtmlToPdfService wkHtmlToPdfService;

    @Autowired
    GlobalServices globalServices;

    @Test
    public void WkHtmlToPdfServiceConvertTest(){

        String urlToConvert = "http://jessewarden.com/2008/11/agile-chronicles-1-stressful.html";

        // URL que no existe
        //String urlToConvert = "http://estesitio.uy/content-1";

        String title ="testingTitlePDF";
        String format = "pdf";

        String path =wkHtmlToPdfService.convert(urlToConvert, title, format);

        Assert.assertTrue(new File(path).exists());


    }
}
