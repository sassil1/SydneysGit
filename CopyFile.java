import java.io.File;
import java.io.IOException;

public class CopyFile {

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
}