import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class Process {
    public LinkedList<String> wordlist = new LinkedList<String>();
    private String[][] ergebnis;
    public String dir;
    public String name;

    public Process(String test){
        System.out.println(cleanUp(test));
    }

    public Process(String dir, String name) {
        //Liest den Text ein und verabreitet ihn
        this.dir = dir;
        this.name = name;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(dir + "\\textin\\" + name + ".txt"), StandardCharsets.UTF_8));
            String line;
            while ((line = br.readLine()) != null) {
                String clean = cleanUp(line);
                breakUpAndInsert(clean);
            }
            br.close();
        } catch (FileNotFoundException fnfe) {
            System.out.println("Error. Datei nicht gefunden.");
            System.out.println("Die Datei muss im Ausführungsordner " + dir + "\\textin\\ liegen mit dem Namen " + name + ".txt");
        } catch (IOException ioe) {
            System.out.println("Error. Unbekannt.");
        }
        //Sotiere die Liste
        String[] words = wordlist.toArray(new String[wordlist.size()]);
        Arrays.sort(words);
        count(words);
    }

    //Zähle gleich und gebe es in output.txt aus.
    public void count(String[] words) {
        try (PrintStream out = new PrintStream(new FileOutputStream(dir + "\\textout\\Out" + name + ".txt"))) {
            System.setOut(out);
            String write = "";
            ergebnis = new String[words.length][2];
            ergebnis[0][0] = words[0];
            int ergebnisPointer = 1;
            int i = 1;
            for (int j = 1; j < words.length; j++) {
                ergebnis[j - 1][1] = "0";
                if (words[j - 1].equals(words[j])) {
                    i++;
                } else {
                    ergebnis[ergebnisPointer - 1][1] = String.valueOf(i);
                    ergebnis[ergebnisPointer][0] = words[j];
                    ergebnisPointer++;
                    i = 1;
                }
            }
            ergebnis[ergebnisPointer - 1][1] = String.valueOf(i);
            if (ergebnis[ergebnis.length - 1][1] == null) {
                ergebnis[ergebnis.length - 1][1] = "0";
            }
            Arrays.sort(ergebnis, (a, b) -> Integer.compare(Integer.parseInt(a[1]), Integer.parseInt(b[1])));
            int uniqueWorter=0;
            int anzahlWorter = 0;
            int anzahlBuchstaben = 0;
            int j = ergebnis.length - 1;
            while (j >= 0 && !ergebnis[j][1].equals(String.valueOf(0))) {
                if (!ergebnis[j][0].equals(" ") && !ergebnis[j][0].equals("  ")) {
                    anzahlBuchstaben += Integer.parseInt(ergebnis[j][1]) * ergebnis[j][0].length();
                    anzahlWorter += Integer.parseInt(ergebnis[j][1]);
                    write += ergebnis[j][0] + "\t\t\t" + ergebnis[j][1] + "\n";
                    uniqueWorter++;
                }
                j--;
            }
            write = "Durchschnitt Wortschatz:\t" + ((float) anzahlWorter/uniqueWorter) + "\n\n" + write;
            write = "Durchschnitt Buchstaben:\t" + ((float) anzahlBuchstaben / anzahlWorter) + "\n" + write;
            write = "Anzahl von Buchstaben:  \t" + anzahlBuchstaben + "\n" + write;
            write = "Wortschatz:             \t" + uniqueWorter + "\n" + write;
            write = "Anzahl von Wörtern:     \t" + anzahlWorter + "\n" + write;
            out.println(write);
            out.close();
        } catch (FileNotFoundException fnfe) {
            System.out.println("Es gibt kein output.txt");
        }
    }

    //Bricht den String bei Leerzeichen in eigene Wörter und fügt diese der Wordlist hinzu.
    public void breakUpAndInsert(String s) {
        Collections.addAll(wordlist, s.split("\n"));
    }

    //Zusammenfassung der bigToSmall, replaceGerman und sanitize Funktion in eine simple.
    //Wörter, die durch delet() gelöscht werden, werden in der Endstatistik verworfen.
    public String cleanUp(String s) {
        return delet(bigToSmall(sanitize(replaceGerman(s))));
    }

    //Wanndelt die Großbuchstaben in Kleinbuchstaben um.
    public String bigToSmall(String s) {
        String re = "";
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i);
            if (c >= 65 && c <= 90) {
                re += (char) (c + 32);
            } else {
                re += (char) c;
            }
        }
        return re;
    }

    //Ersetzt Umlaute in gängliche Schreibweisen
    public String replaceGerman(String s) {
        String re = s;
        re = re.replace("Ä", "ae");
        re = re.replace("ä", "ae");
        re = re.replace("Ö", "oe");
        re = re.replace("ö", "oe");
        re = re.replace("Ü", "ue");
        re = re.replace("ü", "ue");
        re = re.replace("ß", "ss");
        return re;
    }

    //Entfernt alle Chars, bis auf Buchstaben, ' und Leerzeichen mit Sonderregeln
    public String sanitize(String s) {
        String re = " ";
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i);
            if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122) || c == 39 || (c>=48 && c<=57)) {
                re += (char) c;
            } else if (re.length() > 0 && re.charAt(re.length() - 1) != 10) {
                re += (char) 10+" ";
            } else if (c == 32 && re.length() > 1 && re.charAt(re.length() - 2) != 10) {
                re += (char) 10+" ";
            } else if (re.length() > 1 && re.charAt(re.length() - 2) != 10) {
                re += (char) 10+" ";
            }
        }
        return re;
    }

    //Löscht alle Strings im del Array
    public String delet(String s) {
        String re = s;
        String del[]={" der\n"," die\n"," das\n"," ich\n"," du\n"," er\n"," sie\n"," es\n"," wir\n"," ihr\n"," sie\n"," mein\n"," meine\n"," dein\n"," deine\n"," sein\n"," seine\n"," ihr\n"," ihre\n"," unser\n"," unsere\n"," uns\n" }
        for (int i=0;i<del.length();i++) {
            re=re.replace(del[i],"");
        }
        return re;
    }
}
