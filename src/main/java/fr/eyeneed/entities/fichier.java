package fr.eyeneed.entities;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class fichier {

    private String file;

    public String readFile() {
    	File f=new File("src/main/java/fr/eyeneed/web/" + file);
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(f));
            String line = null;
            while ((line = reader.readLine()) != null)
                builder.append(line);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(reader);
        }
        return builder.toString();
    }
public String template(String text){
	
	String contenu=readFile();
	contenu=contenu.replace("template", text);
	
	return contenu;
}
    private void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException ignored) {}
        }
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

	public fichier(String file) {
		super();
		this.file = file;
	}

}