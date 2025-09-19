import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CopyFileTester {

    public static void verifyInit() {
        boolean everythingsgood = true;
        File git = new File("./git");
        if (!git.exists()) {
            everythingsgood = false;
        }
        File objects = new File("./git/objects");
        if (!objects.exists()) {
            everythingsgood = false;
        }
        File index = new File("./git/index");
        if (!index.exists()) {
            everythingsgood = false;
        }
        File head = new File("./git/HEAD");
        if (!head.exists()) {
            everythingsgood = false;
        }

        if (!everythingsgood) {
            System.out.println("Not all folders and files were correctly created");
        } else {
            System.out.println("All folders and files were correctly created");
        }
    }

    public static void cleanUp(File f) throws IOException {
        if (f.isDirectory()) {
            for (File c : f.listFiles()) {
                CopyFileTester.cleanUp(c);
            }
        }
        if (!f.delete()) {
            System.out.println("Failed to delete all files and folders");
        } else {
            System.out.println("Deleted " + f.getName());

        }
    }

    public static void main(String[] args) throws IOException {
        CopyFile gitproj = new CopyFile();

        gitproj.initializerepo();

        verifyInit();
        File git = new File("./git");
        cleanUp(git);

    }

}
