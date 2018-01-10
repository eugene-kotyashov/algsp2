import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import java.util.ArrayList;
import java.util.TreeMap;

public class WordNet {
    
    private Digraph dg;
    

   // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms)
   {
       if ((synsets == null) || (hypernyms == null))
           throw new java.lang.IllegalArgumentException();
       //parse synsets file
       In synsetsIn = new In(synsets);
       ArrayList<String[]> syns = new ArrayList<String[]>();
       while (!synsetsIn.isEmpty()) {
           String[] tmp = synsetsIn.readLine().split(",");
           syns.add(tmp[1].split(" "));
       }
       /*
       for(String[] ss : syns) {
           for(String s : ss) {
               StdOut.print(s+" ");
           }
           StdOut.println();
       }
       */
       //parse hypernyms file
       In hypsIn = new In(hypernyms);
       TreeMap<Integer, int[]> hyps = new TreeMap<Integer,int[]>();
       while (! hypsIn.isEmpty()) {
           String tmp = hypsIn.readLine();
           String [] tmps = tmp.split(",");
           int[] synInts = new int[tmps.length-1];
           for(int i=1; i<tmps.length; i++)
               synInts[i-1] = Integer.parseInt(tmps[i]);
           hyps.put(new Integer(Integer.parseInt(tmps[0])), synInts);
       }                  
       /*    
       for (Integer key : hyps.keySet()) {
           StdOut.print(key+ ": ");
           for(int arr : hyps.get(key)) {
               StdOut.print(arr+" ");
           }
           StdOut.println();
       }
*/
       dg = new Digraph(syns.size());
       for (Integer key : hyps.keySet()) {
           for(int arr : hyps.get(key)) {
               dg.addEdge(key, arr);
           }
       }
       
       StdOut.println(dg.V() + " vertecis "+dg.E() + " edges");       
       
   }

   // returns all WordNet nouns
   public Iterable<String> nouns() {
       return new ArrayList<String>();
   }

   // is the word a WordNet noun?
   public boolean isNoun(String word) {
       if (word == null)
           throw new java.lang.IllegalArgumentException();
       return false;
   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB) {
       return 0;
   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB) {
       return null;
   }

   // do unit testing of this class
   public static void main(String[] args) {
       //StdOut.println(args[0]);
       //StdOut.println(args[1]);
       WordNet wn = new WordNet(args[0], args[1]);
   }
}