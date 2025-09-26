import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CopyFile {

    private boolean compression = true;

    public void setCompression(boolean b) {
        compression = b;
    }

    // Initializes Git repo, adds objects folder, index file, and heaad file
    public void initializerepo() throws IOException {
        File git = new File("./git");
        if (!git.exists()) {
            git.mkdir();
            System.out.println("Created git folder!");
        } else {
            System.out.println("git folder already exists");
        }
        File objects = new File("./git/objects");
        if (!objects.exists()) {
            objects.mkdir();
            System.out.println("Created objects folder!");
        } else {
            System.out.println("objects folder already exists");
        }
        File index = new File("./git/index");
        if (!index.exists()) {
            index.createNewFile();
            System.out.println("Created index file!");
        } else {
            System.out.println("index file already exists");
        }
        File head = new File("./git/HEAD");
        if (!head.exists()) {
            head.createNewFile();
            System.out.println("Created head file!");
        } else {
            System.out.println("head file already exists");
        }
        System.out.println("git repository created");
    }

    public File zipCompress(File f) throws IOException {
        FileInputStream fis=new FileInputStream(f + "");

        //Assign compressed file:file2 to FileOutputStream
        FileOutputStream fos=new FileOutputStream(f + ".zip");

        //Assign FileOutputStream to DeflaterOutputStream
        DeflaterOutputStream dos=new DeflaterOutputStream(fos);

        //read data from FileInputStream and write it into DeflaterOutputStream
        int data;
        while ((data=fis.read())!=-1)
        {
            dos.write(data);
        }

        //close the file
        fis.close();
        dos.close();
        return new File(f + ".zip");
    }
    
    
    public String genSha1(File f) throws NoSuchAlgorithmException, IOException {
        File f1;
        if (compression) {
            f1 = zipCompress(f);
        } else {
            f1 = f;
        }

        String content = new String(Files.readAllBytes(Paths.get(f1 + "")));

        // Get an instance of the MessageDigest for SHA-1
        MessageDigest md = MessageDigest.getInstance("SHA-1");

        // Convert the input string to bytes and update the message digest
        md.update(content.getBytes());

        // Compute the hash digest
        byte[] digest = md.digest();

        // Convert the byte array to a BigInteger (for easier hexadecimal conversion)
        BigInteger bigInt = new BigInteger(1, digest);

        // Convert the BigInteger to a hexadecimal string
        String hexString = bigInt.toString(16);

        // Pad with leading zeros if necessary to ensure a 40-character SHA-1 hash
        while (hexString.length() < 40) {
            hexString = "0" + hexString;
        }

        return hexString;
    }

    public void storeFileObj(File f) throws IOException, NoSuchAlgorithmException {
        String sha1 = genSha1(f);
        File f1 = f;
        if (compression) {
            f1 = zipCompress(f);
        } 
        File blob = new File("./git/objects/" + sha1);
        if (!blob.exists()) {
            blob.createNewFile();
            Path sourcePath = Paths.get("./" + f1); // Replace with your source file path
            Path destinationPath = Paths.get(blob + ""); // Replace with your desired new file path
            // Copy the file, replacing if the destination exists
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Added new blob file to objects!");
        } else {
            System.out.println("New blob file was not added to objects");
        }
    }

    public void storeFileInd(File f) throws IOException, NoSuchAlgorithmException {
        String sha1 = genSha1(f);
        String data = "";
        String type = "";
        if (f.isDirectory()) {
            type = "tree";
        } else {
            type = "blob";
        }
        File index = new File("./git/index");
        if (!index.exists()) {
            index.createNewFile();
        }
        if (index.length() == 0) {
            data = type + " " + sha1 + " " + f.getPath();
        } else {
            data = "\n" + type + " " + sha1 + " " + f.getPath();
        }
        String filePath = "./git/index";
        FileWriter writer = new FileWriter(filePath, true);
        writer.write(data);
        writer.close();
        System.out.println("File data and name stored in index");
    }
}