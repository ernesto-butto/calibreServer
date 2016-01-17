package application.controller;

import java.io.File;
import java.util.concurrent.atomic.AtomicLong;

import application.Application;
import application.domain.Greeting;
import application.service.CalibreConnectionService;
import application.service.HtmlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class ConvertController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    private final Logger log = LoggerFactory.getLogger(Application.class);


    @Autowired
    CalibreConnectionService calibreConnectionService;

    @Autowired
    HtmlService htmlService;


    @RequestMapping(value = "/convert", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource convert(@RequestParam(value = "htmlUrl", required = true) String htmlUrl,
                                      @RequestParam(value = "outputFormat", defaultValue = "mobi") String outputFormat
    ) {
        log.info("Received reques for url: "+htmlUrl);

        // 1 Guardar el html en local
        String urlToConvert = htmlUrl;

        String htmlContent = htmlService.getHtmlContent(urlToConvert);
        String filename="ebookContent";

        File file = htmlService.saveHtmlContentToFile(htmlContent, filename);

        // 2 correr ebook convert



        // 3 recoger el archivo y devolverlo

        String path = "/home/ubuntu/testFiles/.pdf";

        log.info("Searchig for file "+path);

        FileSystemResource resource = new FileSystemResource(path);

        if (resource.getFile().exists()){
            log.info("file exists");
        }else{
            log.info("files does not exists");

        }


        return resource;
    }
}