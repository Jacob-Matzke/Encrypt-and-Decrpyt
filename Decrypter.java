import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Decrypter {

	public static String readFile(String path, Charset encoding) throws IOException
    {
        ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(path), encoding);
        return String.join(System.lineSeparator(), lines);
    }
	
	public static void main(String[] args) {
		String filePath = "encoded.txt";
		
		String content = null;
        try {
            content = readFile(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        int radix = (int)content.charAt(0)-33;
        
        content = content.substring(content.indexOf("Z")+1);
        String key2 = null;
        try {
        	key2 = content.substring(0, content.indexOf("\r"));
        } catch(Exception e) {
        	e.printStackTrace();
        }
        String key = "";
        for(char x : key2.toCharArray()) {
        	if(x == 'Z')
        		key+=" ";
        	else
        		key+=(char)(x-4);
        }
        
        String code = content.substring(content.indexOf("\n")+1);
        String[] keyValues = key.split(" ");
        
        String decoded = "";
        for(int i = 0; i < code.length(); i++) {
        	int val = 0;
        	try {
        		val = Integer.parseInt(keyValues[i], radix);
        	} catch(Exception e) {
        		e.printStackTrace();
        	}
        	char x = code.charAt(i);
        	x+=62;
        	x-=val;
        	if(x>=126)
        		x-=94;
        	decoded+=x;
        }
        
        System.out.println(decoded);
	}
}