package application.controller;

import application.service.CalibreConnectionService;
import application.service.HtmlService;
import application.service.WkHtmlToPdfService;
import application.service.SharedServices;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ConvertController {

    private final Logger log = LoggerFactory.getLogger(ConvertController.class);


    @Autowired
    CalibreConnectionService calibreConnectionService;

    @Autowired
    WkHtmlToPdfService wkHtmlToPdfService;

    @Autowired
    HtmlService htmlService;

    @Autowired
    SharedServices sharedServices;


    @RequestMapping(value = "/convert", method = RequestMethod.GET )
    @ResponseBody
    public ResponseEntity<FileSystemResource> convert(@RequestParam(value = "htmlUrl", required = true) String htmlUrl,
                                                      @RequestParam(value = "title", required = true) String title,
                                                      @RequestParam(value = "outputFormat", defaultValue = "mobi") String outputFormat
    ) {

        long startTime = System.nanoTime();
        String outputPath = "";

        log.info("Receving request with htmlUrl: "+htmlUrl + ",title: "+title+", outputFormat: "+outputFormat);

        log.info("Removing suffix and replacing white spaces for title if necessary");

        // CLEAN TITLE

        // remove suffix
        title = FilenameUtils.removeExtension(title);
        //  replace white spaces with underscore
        title = title.replaceAll(" ", "_");

        // CONVERT
        if (outputFormat.equalsIgnoreCase("pdf")){

            outputPath = wkHtmlToPdfService.convert(htmlUrl,title,outputFormat);

        }else {

            outputPath = calibreConnectionService.convert(htmlUrl, title, outputFormat, this);


        }

        // 3 recoger el archivo y devolverlo

        log.info("Searchig for file "+outputPath);

        FileSystemResource resource = new FileSystemResource(outputPath);

        if (resource.getFile().exists()){
            log.info("file exists "+ resource.getFile().getAbsolutePath());
        }else{
            log.info("files does not exists "+resource.getFile().getAbsolutePath());

        }

        // log duration of process
        log.info("The process took :"+(System.nanoTime() - startTime)/ 1000000000.0 +" seconds");

        return new ResponseEntity<FileSystemResource>(resource, HttpStatus.OK);

    }

}
