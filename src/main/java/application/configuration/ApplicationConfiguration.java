package application.configuration;

import application.service.CalibreConnectionService;
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

    @Bean
    public CalibreConnectionService configureCallibreService(){

       if (env.getProperty("ebook-convert-location")!=null)
            calibreConnectionService.setCallibreConvertLocation(env.getProperty("ebook-convert-location"));
        // else leave default

        return calibreConnectionService;
    }


}
