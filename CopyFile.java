import java.io.File;

public class CopyFile {

    public void initializerepo() {
        File git = new File("./HTCSProjects/SydneysGit/Git");
        //Make sure that's right^
        if (!git.exists()) {
            git.mkdir();
        }
        File objects = new File("./HTCSProjects/SydneysGit/Git/objects");
        //Make sure that's right^
        if (!objects.exists()) {
            objects.mkdir();
        }
    }
}