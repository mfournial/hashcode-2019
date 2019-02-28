import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        //dol("a");
//        dol("b");
//        dol("c");
//        dol("d");
        dol("e");
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
            int tags_num = Integer.parseInt(l[1]);
            Set<Integer> tags = new HashSet<>(tags_num);
            for (int j = 2; j < 2 + tags_num; j++) {
                tags.add(l[j].hashCode());
            }
            all.add(new Photo(tags, i));
            if (l[0].equals("H")) {
                all.get(i).horizontal = true;
            } else {
                all.get(i).horizontal = false;
            }
        }

        List<Photo> slides = new ArrayList<>();

        Collections.shuffle(all);
        Photo current=  all.get(0);
        int k = 0;
        while(!current.horizontal) {
            current = all.get(k);
            k++;
        }
        if (current.horizontal) {
            System.out.println("hhoresra");
        } else {
            System.out.println("vertical");
        }
        all.remove(current);
        while (!all.isEmpty()) {
            if(all.size() % 100 == 0) {
                System.out.println("Progress");
            }
            Photo best = all.get(0);
            int bestScore =  best.calculateScore(current);
            for (int i = 1; i < Math.min(10, all.size()); i++) {
                if (all.get(i).calculateScore(current) > bestScore) {
                    best = all.get(i);
                    bestScore =all.get(i).calculateScore(current);
                }
            }
            all.remove(best);

            if(!best.horizontal) {
                Photo bestV = new Photo(new HashSet<>(), 9999999, 1);
                int bestScoreV = -999;
                for (int i = 0; i < Math.min(10, all.size()); i++) {
                    if(!all.get(i).horizontal) {
                            if (all.get(i).calculateScore(current) >= bestScoreV) {
                            bestV = all.get(i);
                            bestScoreV = all.get(i).calculateScore(current);
                        }
                    }
                }
                if(bestV.id == 9999999) {
                    System.out.println(all.size());
                    System.out.println("woops");
                }
                best.tags.addAll(bestV.tags);
//                best.horizontal = true;
                best.id2 = bestV.id;
                all.remove(bestV);
            }
            slides.add(best);
            current = best;

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
