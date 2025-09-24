import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CopyFile {

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

    public String genSha1(File f) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(f));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
            line = br.readLine();
        }
        String fileData = sb.toString();
        br.close();

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(fileData.getBytes());
            byte[] hashedData = messageDigest.digest();

            BigInteger bigInteger = new BigInteger(1, hashedData);
            String hashedText = bigInteger.toString(16);
            return hashedText;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void storeFile(File f) throws IOException {
        String sha1 = genSha1(f);
        String data = sha1 + " " + f.getName();
        Path file = Path.of("./git/index");
        Files.writeString(file, data);
        System.out.println("File data and name stored in index");

        File blob = new File("./git/objects/" + sha1);
        if (!blob.exists()) {
            blob.createNewFile();
            System.out.println("Added new blob file to objects!");
        } else {
            System.out.println("New blob file was not added to objects");
        }
    }
}