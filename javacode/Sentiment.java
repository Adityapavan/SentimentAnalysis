/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sentimentanalysis;

/**
 *
 * @author ajay
 */
public class Sentiment {

    public Double score = 0.0;
    public int numSentimentWords = 0;
    public String tense = ""; //past or other

    public Sentiment ( Double score, int num, String tense ) {
        this.score = score;
        this.numSentimentWords = num;
        this.tense = tense;
    }

}
