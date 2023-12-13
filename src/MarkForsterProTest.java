import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;

public class MarkForsterProTest extends Primspieler {
    public static class Zug implements Comparable<Zug> {
        private final int index;
        private final int zahl;
        private final int gewinn;

        private Zug(int index, int zahl, int gewinn) {
            this.index = index;
            this.zahl = zahl;
            this.gewinn = gewinn;
        }

        public int compareTo(Zug z) {
            return z.gewinn - this.gewinn;
        }
    }


    public int auswahl(int[] feld) {
        int anzahlZuege = Math.max(7, (int)(feld.length * 0.01D));
        TreeSet<Zug> besteZuege = getBesteZuege(feld, anzahlZuege);

        return getBesterZug(besteZuege, feld).zahl;
    }

    public TreeSet<Zug> getBesteZuege(int[] feld, int anzahlZuege) {
        TreeSet<Zug> besteZuege = new TreeSet<>();
        for (int i = feld.length - 1; i >= 0; i--) {
            Zug zug = new Zug(i, feld[i], feld[i] - getFaktorSumme(i, feld));
            besteZuege.add(zug);
            if (besteZuege.size() > anzahlZuege)
                besteZuege.remove(besteZuege.last());
            if (feld[i] <= besteZuege.last().gewinn)
                break;
        }

        return besteZuege;
    }

    public int getFaktorSumme(int index, int[] feld) {
        int summe = 0;
        for (int i = 0; i < index; i++) {
            if ((feld[index] % feld[i]) == 0) {
                summe += feld[i];
            }
        }

        return summe;
    }

    public Zug getBesterZug(TreeSet<Zug> besteZuege, int[] feld) {
        Iterator<Zug> it = besteZuege.iterator();
        int maxScore = Integer.MIN_VALUE;
        Zug besterZug = besteZuege.getFirst();
        while (it.hasNext()) {
            Zug z = it.next();
            int[] gegnerFeld = getNeuesFeld(feld, z);
            TreeSet<Zug> besteZuegeGegner = getBesteZuege(gegnerFeld, 1);
            if (!besteZuegeGegner.isEmpty()) {
                int score = z.gewinn - besteZuegeGegner.getFirst().gewinn;
                if (score > maxScore) {
                    besterZug = z;
                    maxScore = score;
                }
            }
        }

        return besterZug;
    }

    public int[] getNeuesFeld(int[] feld, Zug z) {
        int[] neuesFeld = Arrays.copyOf(feld, feld.length);
        int kuerzen = 0;
        for (int i = z.index; i >= 0; i--) {
            if (z.zahl % neuesFeld[i] == 0) {
                for (int j = i; j < neuesFeld.length - 1; j++) {
                    neuesFeld[j] = neuesFeld[j + 1];
                }
                kuerzen++;
            }
        }
        neuesFeld = Arrays.copyOf(neuesFeld, neuesFeld.length - kuerzen);

        return neuesFeld;
    }

    public String getPlayerName() {
        return "Mark Forster Pro Test"; // Bitte anpassen!
    }

    public long getStudentNumber() {
        return 202011111;  // Ihre Spielernummer
    }

    // Bitte nicht vergessen, ein Hochformat-Bild im JPG-Format
    // als Datei <Matrikelnummer>.jpg mit einzureichen!
}
