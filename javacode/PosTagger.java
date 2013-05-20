/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sentimentanalysis;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author ajay
 */
public class PosTagger {
    
    private MaxentTagger tagger;

    public PosTagger ( String pathToModel ) throws IOException, ClassNotFoundException {
        tagger = new MaxentTagger(pathToModel);
    }

    public String getTaggedSentence ( String stringToTag ) {
        String result = "";
        List<List<HasWord>> sentences = tagger.tokenizeText(new StringReader(stringToTag));
        for ( List<HasWord> sentence : sentences ) {
            ArrayList<TaggedWord> tSentence = tagger.tagSentence(sentence);
            result += Sentence.listToString(tSentence, false);
        }
        return result;
    }

    public static void main ( String[] args ) throws IOException, ClassNotFoundException {
        PosTagger posTagger = new PosTagger("/media/Study/Sem 6/Honors Project/stanford-postagger-2011-05-18/models/left3words-wsj-0-18.tagger");
        System.out.println("Result = " + posTagger.getTaggedSentence("This lab is perfectly worst."));
    }

}
