import java.io.*;
import java.nio.file.*;
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

    public static boolean isBlobInObjects(File f) throws NoSuchAlgorithmException, IOException {
        CopyFile gitproj = new CopyFile();
        String directoryPath = "./git/objects";
        File folder = new File(directoryPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    if ((files[i].getName()).equals(gitproj.genSha1(f))) {
                        System.out.println("File: " + gitproj.genSha1(f));
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
        String filePath = "./git/index";
        FileWriter writer = new FileWriter(filePath, false);
        writer.close();

        File f = new File("output");
        if (!f.exists()) {
            f.createNewFile();
        }
        File g = new File("output2");
        if (!g.exists()) {
            g.createNewFile();
        }
        File h = new File("output3");
        if (!h.exists()) {
            h.createNewFile();
        }
        f.delete();
        g.delete();
        h.delete();
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        // WARNING!! - if you change any of the file names assigned in here, the tester
        // methods will no longer work properly!!!!
        // ALSO - scroll to bottom and comment out the cleanup method before running if
        // you want to see all the files and folders :)

        // initializing CopyFile Obj
        CopyFile gitproj = new CopyFile();
        gitproj.initializerepo();

        // For Compression - to turn off compression, set as false, to turn on, set as
        // true
        gitproj.setCompression(true);

        // Create and fills files for practicing with sha1 later
        File f = new File("output");
        if (!f.exists()) {
            f.createNewFile();
        }
        String data = "This is some text to write to the file. This is some text to write to the file. This is some text to write to the file. This is some text to write to the file. This is some text to write to the file. This is some text to write to the file. This is some text to write to the file.";
        Path file = Path.of("output");
        Files.writeString(file, data);

        File g = new File("output2");
        if (!g.exists()) {
            g.createNewFile();
        }
        String data2 = "My name is Sydney. My name is Sydney. My name is Sydney. My name is Sydney. My name is Sydney. My name is Sydney. My name is Sydney. My name is Sydney. My name is Sydney. My name is Sydney.";
        Path file2 = Path.of("output2");
        Files.writeString(file2, data2);

        File h = new File("output3");
        if (!h.exists()) {
            h.createNewFile();
        }
        String data3 = "I'm 18 years old and i love dogs. I'm 18 years old and i love dogs. I'm 18 years old and i love dogs. I'm 18 years old and i love dogs. I'm 18 years old and i love dogs. I'm 18 years old and i love dogs. I'm 18 years old and i love dogs. I'm 18 years old and i love dogs.";
        Path file3 = Path.of("output3");
        Files.writeString(file3, data3);

        // Verifies repo initialization
        verifyInit();

        // Makes sure Sha1 blob is created properly from random file
        System.out.println(gitproj.genSha1(f));

        // Takes 3 files, turns into sha1 blobs, stores in index and creates files in
        // objects with the sha1 hash as their names
        gitproj.storeFileObj(f);
        gitproj.storeFileObj(g);
        gitproj.storeFileObj(h);

        gitproj.storeFileInd(f);
        gitproj.storeFileInd(g);
        gitproj.storeFileInd(h);

        // Making sure blobs are in objects (Stretch Goal 2.3.1)
        System.out.println(isBlobInObjects(f));
        System.out.println(isBlobInObjects(g));
        System.out.println(isBlobInObjects(h));

        File i = new File(f + ".zip");
        if (!i.exists()) {
            i.createNewFile();
        }
        File j = new File(g + ".zip");
        if (!j.exists()) {
            j.createNewFile();
        }
        File k = new File(h + ".zip");
        if (!k.exists()) {
            k.createNewFile();
        }
        i.delete();
        j.delete();
        k.delete();

        // Reseting (Stretch Goal 2.3.1--> 2.4.2) (Comment out for testing the above,
        // then uncomment and run before testing again)
        // cleanUp();

    }
}
