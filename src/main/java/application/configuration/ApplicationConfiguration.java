package application.configuration;

import application.service.CalibreConnectionService;
import application.service.HtmlService;
import application.shared.GlobalVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.inject.Inject;

/**
 * Created by poolebu on 1/19/16.
 */
@Configuration
public class ApplicationConfiguration {

    @Inject
    Environment env;

    @Autowired
    CalibreConnectionService calibreConnectionService;

    @Autowired
    GlobalVariables globalVariables;


    @Bean
    public CalibreConnectionService configureCallibreService(){

       if (env.getProperty("ebook-convert-location")!=null)
            calibreConnectionService.setCallibreConvertLocation(env.getProperty("ebook-convert-location"));
        // else leave default


        return calibreConnectionService;
    }

    @Bean
    public GlobalVariables htmlServiceConfiguration(){

        if (env.getProperty("output-folder")!=null)

        globalVariables.setContentFolder(env.getProperty("output-folder"));

        return globalVariables;

    }

}
