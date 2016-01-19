package application.shared;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by poolebu on 1/19/16.
 */
@Service
@Scope("singleton")
public class GlobalVariables {

    String contentFolder="/Users/poolebu/Desktop/ebookContentDir/";

    public String getContentFolder() {
        return contentFolder;
    }

    public void setContentFolder(String contentFolder) {
        this.contentFolder = contentFolder;
    }


}
