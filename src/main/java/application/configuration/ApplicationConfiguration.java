package application.configuration;

import application.service.CalibreConnectionService;
import application.service.SharedServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Created by poolebu on 1/19/16.
 */
//@Configuration
public class ApplicationConfiguration {

    @Inject
    Environment env;

    @Autowired
    CalibreConnectionService calibreConnectionService;

    @Autowired
    SharedServices sharedServices;

    private final Logger log = LoggerFactory.getLogger(ApplicationConfiguration.class);


    @Bean
    public CalibreConnectionService configureCallibreService(){

       if (env.getProperty("ebook-convert-location")!=null)
            calibreConnectionService.setCallibreConvertLocation(env.getProperty("ebook-convert-location"));
        // else leave default

        log.info("Using calibre's ebook-convert command root: "+env.getProperty("ebook-convert-location"));


        return calibreConnectionService;
    }

    @Bean
    @PostConstruct
    public SharedServices configureGlobalServices(){

            log.info("Setting output folder as " + env.getProperty("output-folder"));

            if (env.getProperty("output-folder") != null)
                sharedServices.setContentFolder(env.getProperty("output-folder"));

            return sharedServices;


    }

}
