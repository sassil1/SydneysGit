import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.nio.file.*;

public class JoeTester {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        CopyFile git = new CopyFile();
        git.setCompression(false);

        git.initializerepo("ProjectFolder");

        git.writeFile("ProjectFolder/gitignore", "JUNKFOLDER");

        git.writeFile("ProjectFolder/crapppppp", "contents of crapppppp");

        git.createDir("ProjectFolder/JUNKFOLDER");

        git.writeFile("ProjectFolder/JUNKFOLDER/h3h3h3", "contents of h3h3h3");

        git.writeFile("ProjectFolder/JUNKFOLDER/h2h2h2h2", "contents of h2h2h2");

        git.createDir("ProjectFolder/JUNKFOLDER/sub_folder");

        git.writeFile("ProjectFolder/JUNKFOLDER/sub_folder/SUBBEST_FILE", "contents of subbest file");

        git.refresh("ProjectFolder");

        String indexContents = Files.readString(new File("ProjectFolder/git/index").toPath());

        git.makeTree(indexContents);
    }
}
