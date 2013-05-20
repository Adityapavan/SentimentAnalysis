/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sentimentanalysis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

/**
 *
 * @author ajay
 */
public class SWN3 {
    
    private HashMap<String,Double> _dict;
    
    public SWN3 ( String pathToSWN ) throws FileNotFoundException, IOException {
        _dict = new HashMap<String,Double>();
        
        HashMap<String,Vector<Double>> _temp = new HashMap<String,Vector<Double>>();
        
        BufferedReader bufferedReader = new BufferedReader ( new FileReader(pathToSWN) );
        String line = "";
        
        while ( ( line = bufferedReader.readLine() ) != null ) {
            if(line.charAt(0) == '#' || line.charAt(0) == '\t'){
                    continue;
            }
            String[] data = line.split("\t");
            Double score = Double.parseDouble(data[2]) - Double.parseDouble(data[3]);
            String[] words = data[4].split(" ");
            for (String w : words) {
                String[] w_n = w.split("#");
                w_n[0] += "#" + data[0];
                int index = Integer.parseInt(w_n[1]) - 1;
                if (_temp.containsKey(w_n[0])) {
                    Vector<Double> v = _temp.get(w_n[0]);
                    if (index > v.size()) {
                        for (int i = v.size(); i < index; i++) {
                            v.add(0.0);
                        }
                    }
                    v.add(index, score);
                    _temp.put(w_n[0], v);
                } else {
                    Vector<Double> v = new Vector<Double>();
                    for (int i = 0; i < index; i++) {
                        v.add(0.0);
                    }
                    v.add(index, score);
                    _temp.put(w_n[0], v);
                }
            }
        }
        Set<String> temp = _temp.keySet();
        for (Iterator<String> iterator = temp.iterator(); iterator.hasNext();) {
            String word = (String) iterator.next();
            Vector<Double> v = _temp.get(word);
            double score = 0.0;
            double sum = 0.0;
            for (int i = 0; i < v.size(); i++) {
                score += ((double) 1 / (double) (i + 1)) * v.get(i);
            }
            for (int i = 1; i <= v.size(); i++) {
                sum += (double) 1 / (double) i;
            }
            score /= sum;
            _dict.put(word, score);
        }
    }
    
    /**
     * Returns the sentimental orientation of the word.
     * Uses Sentiwordnet-3.0.0 to see the synsets and calculate the score.
     * More formally returns the {@link return} value
     * if word is found or {@code null} is returned.
     * 
     * @param word the word for which the sentiment is needed to be calculated
     * @param pos the part of speech of the word {NN:"n", VB:"v", JJ:"a", RB:"r"}
     * @return returns p: positive , n: negative , 0: neutral , null: word not found
     */
    
    public Double extract ( String word, String pos ) {
        String temp = word + "#" + pos;
        if ( _dict.containsKey(temp) ) {
            return _dict.get(temp);
        }
        return -2.0;
    }

}
