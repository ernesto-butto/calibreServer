package application.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by poolebu on 1/19/16.
 */
@Service
public class SharedServices {

    String contentFolder="/Users/poolebu/Desktop/ebookContentDir/";

	public String executeCommand(String command) {

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader =
					new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}

	public String getContentFolder() {
        return contentFolder;
    }

    public void setContentFolder(String contentFolder) {
        this.contentFolder = contentFolder;
    }


}
