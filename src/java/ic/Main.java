package ic;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
//        dol("a");
//        dol("b");
        dol("b");
        /*
        dol("c");
        dol("e");
        */
    }

    public static void dol(String input) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(input));

        String l1= reader.readLine();
        Integer images = Integer.parseInt(l1);
        List<Shit> vpics = new ArrayList<>(images);
        List<Photo> all = new ArrayList<>(images);

        for (int i = 0; i < images; i++) {
            String line = reader.readLine();
            String[] l = line.split(" ");
            int tags_num= Integer.parseInt(l[1]);
            Set<Integer> tags = new HashSet<>(tags_num);
            for (int j = 2; j < 2 + tags_num; j++) {
                tags.add(l[j].hashCode());
            }
            if (l[0].equals("H")) {
                all.add(new Photo(tags, i));
            } else {
                vpics.add(new Shit(tags, i));
            }
        }


        if (0 != vpics.size() % 2) {
            System.exit(-1);
        }

        Shit currentS;
        int bing = vpics.size();
        while (!vpics.isEmpty()) {
            currentS = vpics.get(vpics.size() -1 );
            vpics.remove(currentS);
            if(all.size() % 1000 == 0) {
                System.out.println((double) (100 - 100 * (double) all.size() / (double) bing));
            }
            Shit best = vpics.get(0);
            int bestScore =  best.cmp(currentS);
            for (int i = 1; i < Math.min(vpics.size(), 7000); i++) {
                if (vpics.get(i).cmp(currentS) > bestScore) {
                    best = vpics.get(i);
                    bestScore = vpics.get(i).cmp(currentS);
                }
            }
            vpics.remove(best);
            currentS.tags.addAll(best.tags);
            all.add(new Photo(currentS.tags, currentS.id, best.id));

        }

        Collections.shuffle(all);

        List<Photo> slides = new ArrayList<>();

        Photo current=  all.get(0);
        all.remove(current);
        int start = all.size();
        while (!all.isEmpty()) {
            if(all.size() % 100 == 0) {
                System.out.println((double) (100 - 100 * (double) all.size() / (double) start));
            }
            Photo best = all.get(0);
            int bestScore =  best.calculateScore(current);
            for (int i = 1; i < Math.min(all.size(), 3500); i++) {
                if (all.get(i).calculateScore(current) > bestScore) {
                    best = all.get(i);
                    bestScore =all.get(i).calculateScore(current);
                }
            }
            slides.add(current);
            current = best;
            all.remove(current);
        }
        slides.add(current);

        FileWriter fileWriter = new FileWriter(input + "-out.txt");
        fileWriter.write(new Integer(slides.size()).toString());
        for (Photo a : slides) {
            fileWriter.write(a.output());
        }
        fileWriter.flush();

        fileWriter.close();
    }
}
