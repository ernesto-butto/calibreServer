package application;

import application.service.CalibreConnectionService;
import application.service.SharedServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Configuration
@SpringBootApplication
public class Application {

    @Inject
    Environment env;

    @Autowired
    CalibreConnectionService calibreConnectionService;

    @Autowired
    SharedServices sharedServices;

    private final Logger log = LoggerFactory.getLogger(Application.class);



    @Bean
    @PostConstruct
    public CalibreConnectionService configureCallibreService(){

        if (env.getProperty("ebook-convert-location")!=null)
            calibreConnectionService.setCallibreConvertLocation(env.getProperty("ebook-convert-location"));
        // else leave default

        if (env.getProperty("output-folder")!=null)
                   sharedServices.setContentFolder(env.getProperty("output-folder"));

        log.info("Using calibre's ebook-convert command root: "+env.getProperty("ebook-convert-location"));


        return calibreConnectionService;
    }



    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

    }
}
