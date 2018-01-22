/******************************************************************************
 *  Name:    Eugene Kotyashov
 *  NetID:   euk
 *  Precept: P01
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description: class searching word that have less in common with all other
 *  words specified in input file
 *
******************************************************************************/
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class Outcast {
// constructor takes a WordNet object 
    
    private WordNet wn;
    
    public Outcast(WordNet wordnet) {
        wn = wordnet;
    }
        
// given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns)   {
        for (String s : nouns) {
            if (!wn.isNoun(s)) {
                throw new java.lang.IllegalArgumentException(
            s+ " is not a wordnet noun!");
            }
        }
        int [] dist = new int[nouns.length];
        for (int i = 0; i < nouns.length; i++) {
            dist[i] = 0;
            for (int j = 0; j < nouns.length; j++) {
                if (i == j) continue;
                dist[i] += wn.distance(nouns[i], nouns[j]);
            }
        }
//        for(int d : dist)
//            StdOut.println(d);
        int max = 0;
        int maxIndex = -1;
        for (int i = 0; i < dist.length; i++) {
            if (max < dist[i]) {
                max = dist[i];
                maxIndex = i;
            }
        }
        return nouns[maxIndex];
    }
// see test client below
    public static void main(String[] args)  {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }

    }
}