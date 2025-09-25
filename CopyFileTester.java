import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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

    // public static void cleanUp(File f) throws IOException {
    //     if (f.isDirectory()) {
    //         for (File c : f.listFiles()) {
    //             CopyFileTester.cleanUp(c);
    //         }
    //     }
    //     if (!f.delete()) {
    //         System.out.println("Failed to delete all files and folders");
    //     } else {
    //         System.out.println("Deleted " + f.getName());
    //     }
    // }

    public static boolean isBlobInObjects(File f, boolean compression) throws NoSuchAlgorithmException, IOException {
        CopyFile gitproj = new CopyFile();
        String directoryPath = "./git/objects";
        File folder = new File(directoryPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    if ((files[i].getName()).equals(gitproj.genSha1(f, compression))) {
                        System.out.println("File: " + gitproj.genSha1(f, compression));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void cleanUp() throws IOException {
        File objects = new File("./git/objects");
        if (!objects.exists()) {
            objects.mkdir();
        }
        File[] files = objects.listFiles();
        for (int i = files.length - 1; i >= 0; i--) {
            files[i].delete();
            System.out.println("Deleted " + files[i].getName());
        }
        File f = new File("output");
        if (!f.exists()) {
            f.createNewFile();
        }
        f.delete();
        File g = new File("output2");
        if (!g.exists()) {
            g.createNewFile();
        }
        g.delete();
        File h = new File("output3");
        if (!h.exists()) {
            h.createNewFile();
        }
        h.delete();
        String filePath = "./git/index";
        FileWriter writer = new FileWriter(filePath, false);
        writer.close();
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        // WARNING!! - if you change any of the file names assigned in here, the tester methods will no longer work properly!!!!
        
        // initializing CopyFile Obj
        CopyFile gitproj = new CopyFile();
        gitproj.initializerepo();

        // For Compression - to turn off compression, just set c = false
        boolean c = false;

        // Create and fills files for practicing with sha1 later
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
        String data2 = "My name is Sydney";
        Path file2 = Path.of("output2");
        Files.writeString(file2, data2);

        File h = new File("output3");
        if (!h.exists()) {
            h.createNewFile();
        }
        String data3 = "I'm 18 years old and i love dogs";
        Path file3 = Path.of("output3");
        Files.writeString(file3, data3);

        // Verifies repo initialization
        verifyInit();

        // Makes sure Sha1 blob is created properly from random file
        System.out.println(gitproj.genSha1(f, c));

        // Takes 3 files, turns into sha1 blobs, stores in index and creates files in objects with the sha1 hash as their names
        gitproj.storeFileObj(f, c);
        gitproj.storeFileObj(g, c);
        gitproj.storeFileObj(h, c);
        gitproj.storeFileInd(f, c);
        gitproj.storeFileInd(g, c);
        gitproj.storeFileInd(h, c);

        // Making sure blobs are in objects (Stretch Goal 2.3.1)
        System.out.println(isBlobInObjects(f, c));
        System.out.println(isBlobInObjects(g, c));
        System.out.println(isBlobInObjects(h, c));

        // Reseting (Stretch Goal 2.3.1--> 2.4.2) (Comment out for testing the above, then uncomment and run before testing again)
        cleanUp();

        //TO-DO: MAKE SURE ZIP COMPRESSION IS CORRECT AND MAKE SURE HEAD IS CORRECT


    }
}
