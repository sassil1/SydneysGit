import java.io.*;
import java.security.NoSuchAlgorithmException;

public class JoeTester {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        CopyFile gitproj = new CopyFile();
        gitproj.initializerepo();
        gitproj.setCompression(false);

        // create and store "Joe"
        gitproj.writeFile("Joe", "asdfasdflol");
        gitproj.storeFileObj(new File("Joe"));
        gitproj.storeFileInd(new File("Joe"));

        // create and store "JOEINDIR" in "FunnyDir"
        gitproj.createDir("FunnyDir");
        gitproj.writeFile("FunnyDir/JOEINDIR", "IM IN A DIR HELP ME");
        gitproj.storeFileObj(new File("FunnyDir/JOEINDIR"));
        gitproj.storeFileInd(new File("FunnyDir/JOEINDIR"));
    }
}
