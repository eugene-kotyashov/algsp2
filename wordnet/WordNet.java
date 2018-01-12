import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

public class WordNet {
    
    private Digraph dg;
    private TreeSet<String> nounSet;
    
    private class DFS {
        private boolean marked[];
        private boolean onStack[];
        private boolean foundCycle;
        public DFS (Digraph dg) {
            marked = new boolean[dg.V()];
            onStack = new boolean[dg.V()];
            foundCycle = false;
            for (int v = 0; v<dg.V(); v++) {
                if (!marked[v] && !foundCycle) {
                    dfs(dg, v);
                }
            }               
        }
        public boolean hasCycle() {
            return foundCycle;
        }
        
        private void dfs(Digraph dg, int v) {
            marked[v] = true;
            onStack[v] = true;
            for(int w : dg.adj(v)) {
                if (foundCycle) 
                    return; 
                if (!marked[w]) 
                    dfs(dg,w);
                else
                if (onStack[w])
                    foundCycle = true;
            }
            onStack[v]=false;
        }
    }
    

   // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms)
   {
       if ((synsets == null) || (hypernyms == null))
           throw new java.lang.IllegalArgumentException();
       //parse synsets file
       In synsetsIn = new In(synsets);
       ArrayList<String[]> syns = new ArrayList<String[]>();
       nounSet = new TreeSet<String>();
       while (!synsetsIn.isEmpty()) {
           String[] tmp = synsetsIn.readLine().split(",");
           String[] tmpSyns = tmp[1].split(" ");
           syns.add(tmpSyns);
           for (String s : tmpSyns)
               nounSet.add(s);
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
       ////
       StdOut.println(dg.V() + " vertices "+dg.E() + " edges");
       for(String s : nounSet)
           StdOut.println(s);
       
       StdOut.println("Digraph is ");
       StdOut.println(dg);
       //find number of roots
       int rootCount = 0;
       for (int v = 0; v<dg.V(); v++) {
           int outOrder = 0;
           for( int w : dg.adj(v) )
               outOrder++;
           if (outOrder == 0)
               rootCount++;           
       }
       if (rootCount != 1) {
           throw new java.lang.IllegalArgumentException(rootCount+ 
                                                        " roots found");
       }
       //check whether this graph hava cycles
       DFS dfs = new DFS(dg);               
       if (dfs.hasCycle()) {
           throw new java.lang.IllegalArgumentException("cycle found");
       }
       
       
   }

   // returns all WordNet nouns
   public Iterable<String> nouns() {
       return new TreeSet<String>(nounSet);
   }

   // is the word a WordNet noun?
   public boolean isNoun(String word) {
       if (word == null)
           throw new java.lang.IllegalArgumentException();
       return nounSet.contains(word);
   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB) {
       if ((nounA == null) || (nounB == null) || 
           !isNoun(nounA) || !isNoun(nounB))
           throw new java.lang.IllegalArgumentException();
       return 0;
   }

   // a synset (second field of synsets.txt) that is the common 
   //ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB) {
       if ((nounA == null) || (nounB == null) || 
           !isNoun(nounA) || !isNoun(nounB))
           throw new java.lang.IllegalArgumentException();
       //SAP sap = new SAP(
       return null;
   }

   // do unit testing of this class
   public static void main(String[] args) {
       //StdOut.println(args[0]);
       //StdOut.println(args[1]);
       WordNet wn = new WordNet(args[0], args[1]);
   }
}