import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.nio.file.*;

public class Tester {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        CopyFile git = new CopyFile();
        git.setCompression(false);

        // setup a repo
        git.initializerepo("ProjectFolder");

        // git.writeFile("ProjectFolder/gitignore", "JUNKFOLDER"); // can do a gitignore
        // if you want

        // make some test files. this is what it makes:
        // .
        // └── ProjectFolder (the repository that we make git files/blobs/trees for)
        // └── JUNKFOLDER
        // ├────── h3h3h3
        // ├────── h2h2h2
        // └────── sub_folder
        // ├────────── SUBBEST_FILE
        // └────────── SUBBEST_FILE2222

        git.writeFile("ProjectFolder/crapppppp", "contents of crapppppp");

        git.createDir("ProjectFolder/JUNKFOLDER");

        git.writeFile("ProjectFolder/JUNKFOLDER/h3h3h3", "contents of h3h3h3");

        git.writeFile("ProjectFolder/JUNKFOLDER/h2h2h2h2", "contents of h2h2h2");

        git.createDir("ProjectFolder/JUNKFOLDER/sub_folder");

        git.writeFile("ProjectFolder/JUNKFOLDER/sub_folder/SUBBEST_FILE", "contents of subbest file");

        git.writeFile("ProjectFolder/JUNKFOLDER/sub_folder/SUBBEST_FILE2222", "contents of subbest file222");

        // "refresh", or generate blob/index files for all the stuff in the project
        // folder
        git.refresh("ProjectFolder"); // this will generate the git files and the blob files

        // get the contents of the index
        String indexContents = Files.readString(new File("ProjectFolder/git/index").toPath());

        // use the contents of the index to generate tree files for everything
        git.makeTree(indexContents);
    }
}
