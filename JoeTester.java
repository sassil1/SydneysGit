import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.nio.file.*;

public class JoeTester {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        CopyFile git = new CopyFile();
        git.setCompression(false);

        git.initializerepo("ProjectFolder");

        git.writeFile("ProjectFolder/crapppppp", "sam krupp isnside mi patnazz");

        git.createDir("ProjectFolder/JUNKFOLDER");

        git.writeFile("ProjectFolder/JUNKFOLDER/h3h3h3", "h3h3h3h3h3h3h3h3");

        git.writeFile("ProjectFolder/JUNKFOLDER/h2h2h2h2", "hh2h2h222h2h22h2");

        git.refresh("ProjectFolder");

        git.makeTree(Files.readString(new File("ProjectFolder/git/index").toPath()));
    }
}
