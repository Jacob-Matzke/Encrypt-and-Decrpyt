import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

public class Encrypter {

	public static String readFile(String path, Charset encoding) throws IOException
    {
        ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(path), encoding);
        return String.join(System.lineSeparator(), lines);
    }
 
    public static void main(String[] args) throws IOException
    {
        String filePath = "data.txt";
        File encodedFile = new File("encoded.txt");
        encodedFile.createNewFile();
        FileWriter writer = new FileWriter(encodedFile);
 
        String content = null;
        try {
            content = readFile(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Random r = new Random();
        int offset = r.nextInt(20)+2;
        String key = (char)(offset+33)+"Z";
        int contentLength = content.length();
        String[] keyValues = new String[contentLength];
        for(int i = 0; i < contentLength; i++) {
        	char code = (char)(r.nextInt(10)+32);
        	keyValues[i] =Integer.toString((int)code, offset); 
        }
        
        for(String x : keyValues) {
        	for(char y : x.toCharArray()) {
        		key+=(char)(y+4);
        	}
        	key+="Z";
        }
        key = key.substring(0, key.length()-1);
        
        String encodedString = "";
        for(int i = 0; i < contentLength; i++) {
        	encodedString+=(char)((content.charAt(i)+Integer.parseInt(keyValues[i], offset))%94+32);
        }
        
        writer.write(key+"\n");
        writer.write(encodedString);
        writer.flush();
        writer.close();
    }
}
