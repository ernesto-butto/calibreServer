package application.controller;

import java.util.concurrent.atomic.AtomicLong;

import application.domain.Greeting;
import application.service.CalibreConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();


    @Autowired
    CalibreConnectionService calibreConnectionService;

    @RequestMapping(value = "/files/{file_name}", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource getFile(@PathVariable("file_name") String fileName) {

        String path = "/home/ubuntu/testFiles"+fileName+".pdf";

        FileSystemResource resource = new FileSystemResource(path);

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
