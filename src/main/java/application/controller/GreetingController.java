package application.controller;

import java.util.concurrent.atomic.AtomicLong;

import application.Application;
import application.domain.Greeting;
import application.service.CalibreConnectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    private final Logger log = LoggerFactory.getLogger(Application.class);


    @Autowired
    CalibreConnectionService calibreConnectionService;

    @RequestMapping(value = "/files/{file_name}", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource getFile(@PathVariable("file_name") String fileName) {

        log.info("Received reques for file "+fileName);

        String path = "/home/ubuntu/testFiles/"+fileName+".pdf";

        log.info("Searchig for file "+path);

        FileSystemResource resource = new FileSystemResource(path);

        if (resource.getFile().exists()){
            log.info("file exists");
        }else{
            log.info("files does not exists");

        }


        return resource;

    }

    @RequestMapping("/convert")
    public Greeting greeting(@RequestParam(value="inputFile", required = true) String inputFile,
                             @RequestParam(value="outputFormat", defaultValue="mobi") String outputFormat
                             ) {


        return new Greeting(counter.incrementAndGet(),
                            String.format(template, "temp"));
    }
}
