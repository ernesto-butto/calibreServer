package application.service;

import application.shared.GlobalServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by poolebu on 2/4/16.
 */
@Service
public class WkHtmlToPdfService {

    @Autowired
    GlobalServices globalServices;

    public String convert(String htmlUrl, String title, String outputFormat) {

        String outputPath = globalServices.getContentFolder()+ title+"."+outputFormat;
        String command = "wkhtmltopdf "+htmlUrl +" " + outputPath;

        String result =globalServices.executeCommand(command);

        // por alguna razon wkhtmltopdf no retorna nada cuando devuelve el resultado, un string vacio, por eso la validacion
        // no se hace contra el string result si no se revisa si se ha creado el archivo
        if (new File(outputPath).exists()){

            return outputPath;

        }

        return null;
    }
}
