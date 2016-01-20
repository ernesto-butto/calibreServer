package application.controller;

import java.io.File;
import application.service.CalibreConnectionService;
import application.service.HtmlService;
import application.shared.GlobalVariables;
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


    @RequestMapping(value = "/convert", method = RequestMethod.GET , produces = MediaType.APPLICATION_OCTET_STREAM_VALUE )
    @ResponseBody
    public ResponseEntity<FileSystemResource> convert(@RequestParam(value = "htmlUrl", required = true) String htmlUrl,
                                      @RequestParam(value = "title", required = true) String title,
                                      @RequestParam(value = "outputFormat", defaultValue = "mobi") String outputFormat
    ) {
        log.info("Received reques for url: "+htmlUrl);
        long startTime = System.nanoTime();

        // 1 Guardar el html en local
        String htmlContent = htmlService.getHtmlContent(htmlUrl);

        File file = htmlService.saveHtmlContentToFile(htmlContent, globalVariables.getContentFolder(), title);

        // 2 correr ebook convert

        String path = calibreConnectionService.convert(file.getAbsolutePath(), outputFormat);

        if (path==null){
            log.error("There was an error converting the ebook ");
            return new ResponseEntity<FileSystemResource>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

        // log duration of process

        log.info("The process took :"+(System.nanoTime() - startTime) +" nano seconds");

        // 3 recoger el archivo y devolverlo

        log.info("Searchig for file "+path);

        FileSystemResource resource = new FileSystemResource(path);

        if (resource.getFile().exists()){
            log.info("file exists "+ resource.getFile().getAbsolutePath());
        }else{
            log.info("files does not exists "+resource.getFile().getAbsolutePath());

        }

        return new ResponseEntity<FileSystemResource>(resource, HttpStatus.OK);

    }
}
