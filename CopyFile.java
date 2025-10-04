import java.io.*;
import java.math.BigInteger;
import java.nio.file.*;
import java.security.*;
import java.util.zip.*;

public class CopyFile {

    private String directory;

    private boolean compression = false;

    public void setCompression(boolean b) {
        compression = b;
    }

    // Initializes Git repo, adds objects folder, index file, and heaad file
    public void initializerepo(String path) throws IOException {
        directory = path;
        File mainDir = new File(path);
        if (!mainDir.exists()) {
            mainDir.mkdir();
            System.out.println("Created main directory folder!");
        } else {
            System.out.println("Main directory folder already exists!");
        }
        File git = new File(path + "/git");
        if (!git.exists()) {
            git.mkdir();
            System.out.println("Created git folder!");
        } else {
            System.out.println("git folder already exists");
        }
        File objects = new File(path + "/git/objects");
        if (!objects.exists()) {
            objects.mkdir();
            System.out.println("Created objects folder!");
        } else {
            System.out.println("objects folder already exists");
        }
        File index = new File(path + "/git/index");
        if (!index.exists()) {
            index.createNewFile();
            System.out.println("Created index file!");
        } else {
            System.out.println("index file already exists");
        }
        File head = new File(path + "/git/HEAD");
        if (!head.exists()) {
            head.createNewFile();
            System.out.println("Created head file!");
        } else {
            System.out.println("head file already exists");
        }
        System.out.println("git repository created");
    }

    public File zipCompress(File f) throws IOException {
        FileInputStream fis = new FileInputStream(f + "");

        // Assign compressed file:file2 to FileOutputStream
        FileOutputStream fos = new FileOutputStream(f + ".zip");

        // Assign FileOutputStream to DeflaterOutputStream
        DeflaterOutputStream dos = new DeflaterOutputStream(fos);

        // read data from FileInputStream and write it into DeflaterOutputStream
        int data;
        while ((data = fis.read()) != -1) {
            dos.write(data);
        }

        // close the file
        fis.close();
        dos.close();
        return new File(f + ".zip");
    }

    public String genSha1(String s) throws NoSuchAlgorithmException, IOException {
        String content = s;

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
        File blob = new File(directory + "/git/objects/" + sha1);
        if (!blob.exists()) {
            blob.createNewFile();
            writeFile(blob.getPath(), Files.readString(f1.toPath()));

            System.out.println("Added new blob file to objects!");
        } else {
            System.out.println("New blob file was not added to objects");
        }
    }

    public void storeFileInd(File f) throws IOException, NoSuchAlgorithmException {
        String sha1 = genSha1(f);
        String data = "";
        String type = "";

        // im sorry sydney this didn't really work :((((
        if (false) {
            type = "tree";
        } else {
            type = "blob";
        }

        File index = new File(directory + "/git/index");
        if (!index.exists()) {
            index.createNewFile();
        }

        if (Files.readString(index.toPath()).contains(sha1))
            return;

        if (index.length() == 0) {
            data = type + " " + sha1 + " " + f.getPath().substring(f.getPath().indexOf("/") + 1);
        } else {
            data = "\n" + type + " " + sha1 + " " + f.getPath().substring(f.getPath().indexOf("/") + 1);
        }
        String filePath = directory + "/git/index";
        FileWriter writer = new FileWriter(filePath, true);
        writer.write(data);
        writer.close();
        System.out.println("File data hash and name stored in index");
    }

    public void writeFile(String path, String contents) throws IOException {
        File f = new File(path);
        if (!f.exists()) {
            f.createNewFile();
        }
        Path filePath = Path.of(path);
        Files.writeString(filePath, contents);
    }

    public void createDir(String path) throws IOException {
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }
    }

    // blobs and indexes all files
    public void refresh(String path) throws IOException, NoSuchAlgorithmException {
        File[] subFiles = new File(path).listFiles();

        String gitignore = "";
        if (new File(path + "/gitignore").exists())
            gitignore = Files.readString(new File(path + "/gitignore").toPath());

        for (File f : subFiles) {
            if (f.getName().equals("git") || gitignore.contains(f.getName())) {
                continue; // ignore our git files that we make or gitignored files
            }
            if (f.isDirectory()) {
                refresh(f.getPath());
            } else {
                storeFileObj(f);
                storeFileInd(f);
            }
        }
    }

    public void makeTree(String workingIndex) throws IOException, NoSuchAlgorithmException {
        // find the longest path.
        // this will be a line in the index, like blob h4sh file/name.
        String longestPath = longestPath(workingIndex.split("\n"));

        // if we're done, stop
        if (!longestPath.contains("/"))
            return;

        // define the path of the parent folder of whatever the deepest file is
        String parentFolderPath = directory + "/"
                + substringFromLastOccuranceOf(substringToLastOccuranceOf(longestPath, '/'), ' ');

        // define the name the deepest file
        String deepestFile = substringFromLastOccuranceOf(longestPath, '/');

        // figure out the type (blob or tree)
        String type = getType(parentFolderPath + "/" + deepestFile);

        // save what should be written to the tree file
        // we don't make the file yet, because we need to generate a sha1 based on the
        // trees entire contents.
        String treeContents = type + " " + genSha1(deepestFile) + " "
                + deepestFile;

        // shorten working index to subtract the stuff from the line we just "treed"
        workingIndex = workingIndex.substring(0, workingIndex.indexOf(deepestFile) - 1)
                + workingIndex.substring(workingIndex.indexOf(deepestFile) + deepestFile.length());

        // then check other lines and do the same thing, if they have the same parent
        for (String line : workingIndex.split("\n")) {
            // define the path of the line
            String linePath = directory + "/" + substringFromLastOccuranceOf(line, ' ');

            // previously we simlpy subtract the deepest file from the workingindex path.
            // so, we need to make sure we aren't doing the same thing again on it already.
            // we make sure that isn't the case when we check for other files that
            // are in the same parent folder.
            if (!(linePath.substring(0, linePath.length())).equals(parentFolderPath)
                    && (linePath).contains(parentFolderPath)) {
                // define the other subfile (could be a folder also)
                String otherSubFile = substringFromLastOccuranceOf(linePath, '/');
                // define the other subfile's type
                String otherType = getType(parentFolderPath + "/" + otherSubFile);

                // add this stuff to the tree as well
                String potentialNewEntryToTree = "\n" + otherType + " " + genSha1(otherSubFile) + " "
                        + otherSubFile;

                // it's possible there is an entry already and we don't need to do it again.
                if (!treeContents.contains(potentialNewEntryToTree))
                    treeContents += potentialNewEntryToTree;

                // shorten the working index again
                workingIndex = workingIndex.substring(0, workingIndex.indexOf(otherSubFile) - 1)
                        + workingIndex.substring(workingIndex.indexOf(otherSubFile) + otherSubFile.length());
            }
        }

        // write the tree file that we just worked so hard to figure out
        writeFile(directory + "/git/objects/" + genSha1(treeContents), treeContents);

        System.out.println("Tree'd " + parentFolderPath + "!");

        // do it again until we're done!
        makeTree(workingIndex);
    }

    public int countOccurances(String str, char c) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == c)
                count++;
        }
        return count;
    }

    public String substringToLastOccuranceOf(String str, char c) {
        int count = countOccurances(str, c);
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == c)
                count--;
            if (count == 0)
                return str.substring(0, i);
        }
        return null;
    }

    public String substringFromLastOccuranceOf(String str, char c) {
        int count = countOccurances(str, c);
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == c)
                count--;
            if (count == 0)
                return str.substring(i + 1);
        }
        return null;
    }

    public String longestPath(String[] paths) {
        int mostSlashes = 0;
        String longestPath = paths[0];
        for (String line : paths) {
            int numSlashes = countOccurances(line, '/');
            if (numSlashes > mostSlashes) {
                longestPath = line;
                mostSlashes = numSlashes;
            }
        }
        return longestPath;
    }

    public String getType(String path) {
        String type = "";
        if (new File(path).isDirectory())
            type = "tree";
        else
            type = "blob";
        return type;
    }
}