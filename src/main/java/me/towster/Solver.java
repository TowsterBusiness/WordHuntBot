package me.towster;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solver {

    // Not used but should be formatted like this
    char[][] wordHuntTest = {
            {'o', 'o', 'h', 'i'},
            {'l', 'p', 'd', 'm'},
            {'r', 'f', 'n', 'c'},
            {'e', 'e', 'c', 'r'}
    };
    public static List<List<int[]>> solve(char[][] wordHunt) {
        //oohilpdmrfnceecr - Word Hunt Test

        List<String> wordList = new ArrayList<String>();
        try {
            File myObj = new File("./src/main/resources/words.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                wordList.add(data.toLowerCase());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        LetterNode head = new LetterNode('#');
        for (String word : wordList) {
            if (word.length() < 3) continue;
            head.addWord(word);
        }

        List<List<int[]>> goodLocs = new ArrayList<>();

        for (int starterX = 0; starterX < 4; starterX++) {
            for (int starterY = 0; starterY < 4; starterY++) {
                int pointerX = starterX, pointerY = starterY;
                int tempPX = 0, tempPY = 0;


                List<Integer> directionsWent = new ArrayList<>();

                List<Character> wentPlaces = new ArrayList<>();
                wentPlaces.add(wordHunt[starterY][starterX]);

                List<int[]> wentLoc = new ArrayList<>();
                wentLoc.add(new int[]{starterX, starterY});
                int dirChecked = -1;

                while (true) {
                    dirChecked++;
                    tempPX = pointerX;
                    tempPY = pointerY;

                    if (dirChecked == 0) {
                        tempPX += 1;
                    } else if (dirChecked == 1) {
                        tempPX += 1;
                        tempPY -= 1;
                    } else if (dirChecked == 2) {
                        tempPY += 1;
                    } else if (dirChecked == 3) {
                        tempPX -= 1;
                        tempPY -= 1;
                    } else if (dirChecked == 4) {
                        tempPX -= 1;
                    } else if (dirChecked == 5) {
                        tempPX -= 1;
                        tempPY += 1;
                    } else if (dirChecked == 6) {
                        tempPY -= 1;
                    } else if (dirChecked == 7) {
                        tempPX += 1;
                        tempPY += 1;
                    } else if (dirChecked == 8) {

                        wentPlaces.remove(wentPlaces.size() - 1);

                        if (wentPlaces.isEmpty()) {
                            break;
                        }

                        pointerX = wentLoc.get(wentLoc.size() - 2)[0];
                        pointerY = wentLoc.get(wentLoc.size() - 2)[1];
                        wentLoc.remove(wentLoc.size() - 1);
                        dirChecked = directionsWent.get(directionsWent.size() - 1);
                        directionsWent.remove(directionsWent.size() - 1);
                        continue;
                    }

                    if (tempPX >= 4 || tempPX < 0 || tempPY >= 4 || tempPY < 0) continue;

                    boolean isContinue = false;
                    for (int[] loc : wentLoc) {
                        if (loc[0] == tempPX && loc[1] == tempPY) {
                            isContinue = true;
                            break;
                        }
                    }
                    if (isContinue) {
                        continue;
                    }

                    StringBuilder stringBuilder = new StringBuilder();
                    for (Character c : wentPlaces) {
                        stringBuilder.append(c);
                    }

                    stringBuilder.append(wordHunt[tempPY][tempPX]);
                    String s = stringBuilder.toString();
                    System.out.println(s);

                    if (head.contains(s)) {
                        wentLoc.add(new int[]{tempPX, tempPY});
                        wentPlaces.add(wordHunt[tempPY][tempPX]);
                        directionsWent.add(dirChecked);
                        pointerX = tempPX;
                        pointerY = tempPY;
                        dirChecked = -1;
                        if (head.containsWord(s)) {
                            System.out.println("WORD");
                            goodLocs.add(new ArrayList<>(wentLoc));
                        }
                    }
                }
            }
        }
        List<String> goodWords = new ArrayList<>();
        for (List<int[]> locs : goodLocs) {
            StringBuilder sb = new StringBuilder();
            for (int[] loc : locs) {
                sb.append(wordHunt[loc[1]][loc[0]]);
            }
            goodWords.add(sb.toString());

        }

        System.out.println(goodWords);

        return goodLocs;
    }




}