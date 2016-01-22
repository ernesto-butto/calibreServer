package application.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import application.service.CalibreConnectionService;
import application.service.HtmlService;
import application.shared.GlobalVariables;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ConvertController {

    private final Logger log = LoggerFactory.getLogger(ConvertController.class);


    @Autowired
    CalibreConnectionService calibreConnectionService;

    @Autowired
    HtmlService htmlService;

    @Autowired
    GlobalVariables globalVariables;


    @RequestMapping(value = "/convert", method = RequestMethod.GET )
    @ResponseBody
    public ResponseEntity<FileSystemResource> convert(@RequestParam(value = "htmlUrl", required = true) String htmlUrl,
                                                      @RequestParam(value = "title", required = true) String title,
                                                      @RequestParam(value = "outputFormat", defaultValue = "mobi") String outputFormat
    ) {

        long startTime = System.nanoTime();

        log.info("Receving request with htmlUrl: "+htmlUrl + ",title: "+title+", outputFormat: "+outputFormat);

        log.info("Removing suffix and replacing white spaces for title if necessary");
        // remove suffix
        title = FilenameUtils.removeExtension(title);
        // replace white spaces with underscore
        title = title.replaceAll(" ", "_");

        // 1 Guardar el html en local
        log.info("Retrieving html content form url");
        String htmlContent = htmlService.getHtmlContent(htmlUrl);

        log.info("Saving html content to file");
        File htmlContentFile = htmlService.saveHtmlContentToFile(htmlContent, globalVariables.getContentFolder(), title);

        // 2 correr ebook convert
        log.info("Converting html content");
        String path = calibreConnectionService.convert(htmlContentFile.getAbsolutePath(), outputFormat);

        if (path==null){
            log.error("There was an error converting the ebook ");
            throw new RuntimeException("There was an error converting the ebook");

        }

        log.info("OK");
        // log duration of process


        // 3 recoger el archivo y devolverlo

        log.info("Searchig for file "+path);




        FileSystemResource resource = new FileSystemResource(path);

        if (resource.getFile().exists()){
            log.info("file exists "+ resource.getFile().getAbsolutePath());
        }else{
            log.info("files does not exists "+resource.getFile().getAbsolutePath());

        }

        log.info("The process took :"+(System.nanoTime() - startTime)/ 1000000000.0 +" seconds");

        return new ResponseEntity<FileSystemResource>(resource, HttpStatus.OK);

    }
}
