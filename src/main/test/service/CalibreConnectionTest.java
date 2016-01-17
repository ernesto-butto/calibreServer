package service;

import application.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import application.service.CalibreConnectionService;

/**
 * Created by poolebu on 1/16/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class CalibreConnectionTest {

    @Autowired
    CalibreConnectionService calibreConnectionService;

    @Test
    public void executeCommandTest(){

        String outPut = calibreConnectionService.executeCommand("ls");

        Assert.assertTrue(!outPut.isEmpty());


    }


}
