/***
*	Liest alle .txt ein, welche sich im Ordner textin relativ zum Programm Verzeichnis.
*/

import java.io.File;

public class Main {
    public static void main(String[] args) {
        final String dir= System.getProperty("user.dir");
        File userDir=new File(dir+"\\textin\\");
        File[] allfiles=userDir.listFiles();
        for (int i = 0; i < allfiles.length; i++) {
            String name=allfiles[i].getName();
            if (name.substring(name.length()-4,name.length()).equals(".txt")){
                Process p=new Process(dir,name.substring(0,name.length()-4));
            }
        }
    }
}
