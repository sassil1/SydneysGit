import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

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

    public static boolean isBlobInObjects(File f) {
        String directoryPath = "./git/objects/"; // Replace with your actual folder path
        File folder = new File(directoryPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    System.out.println("File: " + file.getName());
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        // initializing CopyFile Obj
        CopyFile gitproj = new CopyFile();
        gitproj.initializerepo();

        //Create and fills files for practicing with sha1 later
        File f = new File("output");
        if (!f.exists()) {
            f.createNewFile();
        }
        String data = "This is some text to write to the file.";
        Path file = Path.of("output");
        Files.writeString(file, data);

        File g = new File("output2");
        if (!g.exists()) {
            g.createNewFile();
        }
        String data2 = "This is some more text to write to the file.";
        Path file2 = Path.of("output2");
        Files.writeString(file2, data2);

        File h = new File("output3");
        if (!h.exists()) {
            h.createNewFile();
        }
        String data3 = "This is even more text to write to the file.";
        Path file3 = Path.of("output3");
        Files.writeString(file3, data3);

        File i = gitproj.zipCompress(f);

        // // Verifies repo initialization
        verifyInit();

        // Makes sure Sha1 blob is created properly from random file
        System.out.println(gitproj.genSha1(f));

        // Takes 3 files, turns it into a sha1 blob, stores it in index and creates a
        // file in objects with the sha1 hash as its name
        gitproj.storeFileObj(f);
        gitproj.storeFileObj(g);
        gitproj.storeFileObj(h);
        gitproj.storeFileInd(f);
        gitproj.storeFileInd(g);
        gitproj.storeFileInd(h);

        //Making sure blobs are in objects (Stretch Goal 2.3.1)
        System.out.println(isBlobInObjects(f));
        System.out.println(isBlobInObjects(g));
        System.out.println(isBlobInObjects(h));

        // Reseting (Stretch Goal 2.3.1) (Comment out for testing the above, then
        // uncomment and run before testing again)
        File git = new File("./git");
        cleanUp(git);
        f.delete();
        g.delete();
        h.delete();
        //i.delete();
    }
}
