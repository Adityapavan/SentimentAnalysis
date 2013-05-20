/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sentimentanalysis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ajay
 */
public class Analyzer {

    public static int num = 0;
    public static SWN3 sentiment;
    public static PosTagger posTagger;
    public static EmotionalSentiment emotionalSentiment;
    public static Pattern patternWord = Pattern.compile("[A-Za-z0-9&']+");
    public static Pattern patternNumber = Pattern.compile("($)?([0-9])+(\\.([0-9])+)?(%)?");

    public Analyzer ( String pathToSWN, String pathToModel ) throws FileNotFoundException, IOException, ClassNotFoundException {
        sentiment = new SWN3(pathToSWN);
        posTagger = new PosTagger(pathToModel);
        emotionalSentiment = new EmotionalSentiment();
    }

    public String cleanText ( String text ) {
        String str = "";
        String[] words = text.split(" ");
        for ( String word : words ) {
            if ( ( word.equals("RT") ) || ( word.charAt(0) == '@' ) || ( word.length() > 4 && word.substring(0, 4).equals("www.") ) || (word.length() > 7 && word.substring(0, 7).equalsIgnoreCase("http://") ) ) {
                continue;
            }
            word = word.replaceAll("#", "");
            word = word.replaceAll("&quot;", "");
            word = word.replaceAll("&amp;", "&");
            word = word.replaceAll("=&gt;", "");
            word = word.replaceAll("=&lt;", "");

            if ( !word.equals("") ) {
                String temp = "";
                Matcher m = patternNumber.matcher(word);
                int x = 0;
                while ( m.find() ) {
                    str += m.group(2) + " ";
                    temp += word.substring(x, m.start());
                    x = m.end();
                }
                temp += word.substring(x);
                word = temp;
                word = word.replace('.', ' ');
                if ( !word.equals("") ) {
                    temp = "";
                    m = patternWord.matcher(word);
                    x = 0;
                    while ( m.find() ) {
                        str += m.group() + " ";
                        temp += word.substring(x, m.start());
                        x = m.end();
                    }
                    temp += word.substring(x);
                    word = temp;
                }
            }
            
        }
//        System.out.println(str);
        return str;
    }

    public static Double complexSum ( Double value1, Double value2 ) {
        if ( ( value1 >= 0.0 && value2 >= 0.0 ) || ( value1 <= 0.0 && value2 <= 0.0 ) ) {
            return value1 + value2;
        } else if ( ( value1 >= 0.0 && value2 <= 0.0 ) || ( value1 <= 0.0 && value2 >= 0.0 ) ) {
            return value2 - value1;
        }
        return 0.0;
    }

    public static Double getScore ( String posTaggedSentence ) {
        num = 0;
        Double score = 0.0;
        Double value1 = 0.0;
        Double value2 = 0.0;
        
        String[] wordsAndTags = posTaggedSentence.split(" ");
        int size = wordsAndTags.length;
        String[] words = new String[size];
        String[] tags = new String[size];
        for ( int i = 0 ; i < size ; i++ ) {
            int y = wordsAndTags[i].indexOf('/');
            tags[i] = wordsAndTags[i].substring(y+1);
            words[i] = wordsAndTags[i].substring(0,y);
//            System.out.println(tags[i]);
        }

        for ( int i = 0 ; i < size ; i++ ) {
            value1 = 0.0;
            value2 = 0.0;
            char i1 = tags[i].charAt(0);
            char i2 = tags[i].charAt(1);
            char j1 = ' ';
            char j2 = ' ';
            if ( i < size - 1 ) {
                j1 = tags[i+1].charAt(0);
                j2 = tags[i+1].charAt(1);
            }
            if ( i1 == 'R' && i2 == 'B' && j1 == 'J' && j2 == 'J' ) {
                value1 = sentiment.extract(words[i], "r");
                value2 = sentiment.extract(words[i+1], "a");
                i++;
            } else if ( i1 == 'R' && i2 == 'B' && j1 == 'V' && j2 == 'B' ) {
                value1 = sentiment.extract(words[i], "r");
                value2 = sentiment.extract(words[i+1], "v");                
            } else if ( i1 == 'V' && i2 == 'B' && j1 == 'R' && j2 == 'B' ) {
                value1 = sentiment.extract(words[i+1], "r");
                value2 = sentiment.extract(words[i], "v");
            } else if ( i1 == 'J' && i2 == 'J' && j1 == 'N' && j2 == 'N' ) {
                value1 = sentiment.extract(words[i], "a");
                value2 = sentiment.extract(words[i+1], "n");
            } else if ( i1 == 'J' && i2 == 'J' ) {
                value1 = sentiment.extract(words[i], "a");
            }
            if ( value1 != 0.0 ) {
                num++;
            }
            if ( value2 != 0.0 ) {
                num++;
            }
            score += complexSum(value1, value2);
        }
//        System.out.println(score);
        return score;
    }

    public int getTense ( String posTaggedSentence ) {
        int count = 0;
        String[] wordsAndTags = posTaggedSentence.split(" ");
        int size = wordsAndTags.length;
        for ( int i = 0 ; i < size ; i++ ) {
            String temp = wordsAndTags[i].substring(wordsAndTags[i].indexOf('/')+1);
            if ( temp.length() == 3 && temp.equals("VBD") ) {
                count++;
            }
        }
        return count;
    }

    public Sentiment[] getSentiment ( String text ) {
//        System.out.println(text);
        text = cleanText(text);
//        System.out.println(text);
        Sentiment[] result = new Sentiment[text.split("\\.").length];

        int i = 0;
        int count = 0;
        int tense = 0;
        Double score = 0.0;
        Double value = 0.0;
        String[] texts = text.split("\\.");
        for ( String sentence : texts ) {

            String[] words = sentence.split(" ");
            for ( String word : words ) {
                value = emotionalSentiment.checkEmotionalSentiment(word);
                score += value;
                if ( value != 0.0 ) {
                    count++;
                }
            }

            String posTaggedSentence = posTagger.getTaggedSentence(sentence);
//            System.out.println(posTaggedSentence);
            value = getScore(posTaggedSentence);
//            System.out.println(value);
            tense = getTense(posTaggedSentence);         
//            System.out.println(num);
//            System.out.println(tense);

            
            if ( tense > 0 ) {
                result[i] = new Sentiment(score, num, "past");
            } else {
                result[i] = new Sentiment(score, num, "not-past");
            }
        }

        return result;
    }

    public static void main ( String[] args ) throws FileNotFoundException, IOException, ClassNotFoundException {
        Analyzer a = new Analyzer("/home/ajay/NetBeansProjects/SentimentAnalysis/data/SentiWordNet_3.0.txt","/home/ajay/NetBeansProjects/SentimentAnalysis/data/left3words-wsj-0-18.tagger");
//        Double v = a.emotionalSentiment.checkEmotionalSentiment("this");
//        System.out.println(v);
//        a.getSentiment("The pass was thrown briskly.");
//        a.getSentiment("They are fumbling excessively today.");
//        a.getSentiment("He frantically maneuvered around the end tackle.");
//        a.getSentiment("The referee aggressively called a foul .");
//        a.getSentiment(": Capital One's survey of small businesses found that 30% are planning to increase their staffs in the next six... http://fb.me/wctzGzaB");
//        a.getSentiment(": Capital One Venture Card Review â€“ A Good Miles Card - The Balance Transfers Blog (blog) http://ping.fm/zHQet http://bit.ly/aW2pPY");
//        a.getSentiment("?$1.50 pocket change? $1.50+heart=a new book for a child in need. Books for kids+your help=positive change www.heartofamerica.org/capitalone");

    }

}
