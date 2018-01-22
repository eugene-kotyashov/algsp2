/******************************************************************************
 *  Name:    Eugene Kotyashov
 *  NetID:   euk
 *  Precept: P01
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description: class searching smalles ancestral path in a given graph
 *  using breadth first search algorithm
******************************************************************************/
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class SAP {
    
    private Digraph dg;    
            

   // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) 
            throw new java.lang.IllegalArgumentException();
        dg = new Digraph(G.V());
        for (int iv = 0; iv < G.V(); iv++) {
            for (int v : G.adj(iv)) {
                dg.addEdge(iv, v);
            }
        }
    }

   // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if ((v < 0) || (v > dg.V()-1) ||  (w < 0) || (w > dg.V()-1))
            throw new java.lang.IllegalArgumentException();
        BreadthFirstDirectedPaths bfv = 
            new BreadthFirstDirectedPaths(dg, v);
        BreadthFirstDirectedPaths bfw = 
            new BreadthFirstDirectedPaths(dg, w);
        int len = Integer.MAX_VALUE;
        for (int vx = 0; vx < dg.V(); vx++) {
            if (bfv.hasPathTo(vx) && bfw.hasPathTo(vx)) {
                int tmp = bfv.distTo(vx)+ bfw.distTo(vx);
                if (len > tmp) len = tmp;
            }
        }
        if (len < Integer.MAX_VALUE)        
            return len;
        return -1;
    }

   // a common ancestor of v and w that participates in 
    //a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w) {
       if ((v < 0) || (v > dg.V()-1) ||  (w < 0) || (w > dg.V()-1))
            throw new java.lang.IllegalArgumentException();
       BreadthFirstDirectedPaths bfv = 
            new BreadthFirstDirectedPaths(dg, v);
       BreadthFirstDirectedPaths bfw = 
            new BreadthFirstDirectedPaths(dg, w);
       int len = Integer.MAX_VALUE;
       int anc = -1;
       for (int vx = 0; vx < dg.V(); vx++) {
           if (bfv.hasPathTo(vx) && bfw.hasPathTo(vx)) {
               int tmp = bfv.distTo(vx)+ bfw.distTo(vx);
               if (len > tmp) {
                   len = tmp;
                   anc = vx;
               }
           }
       }
       return anc;
   }

   // length of shortest ancestral path between any vertex in v and any vertex 
   //in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w) {
       BreadthFirstDirectedPaths bfv = 
            new BreadthFirstDirectedPaths(dg, v);
       BreadthFirstDirectedPaths bfw = 
           new BreadthFirstDirectedPaths(dg, w);
       int len = Integer.MAX_VALUE;
       for (int vx = 0; vx < dg.V(); vx++) {
           if (bfv.hasPathTo(vx) && bfw.hasPathTo(vx)) {
               int tmp = bfv.distTo(vx)+ bfw.distTo(vx);
               if (len > tmp) len = tmp;
           }
       }
       if (len < Integer.MAX_VALUE)        
           return len;
       return -1;
   }

   // a common ancestor that participates in shortest ancestral path;
   //-1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
       BreadthFirstDirectedPaths bfv = 
            new BreadthFirstDirectedPaths(dg, v);
       BreadthFirstDirectedPaths bfw = 
            new BreadthFirstDirectedPaths(dg, w);
       int len = Integer.MAX_VALUE;
       int anc = -1;
       for (int vx = 0; vx < dg.V(); vx++) {
           if (bfv.hasPathTo(vx) && bfw.hasPathTo(vx)) {
               int tmp = bfv.distTo(vx)+ bfw.distTo(vx);
               if (len > tmp) {
                   len = tmp;
                   anc = vx;
               }
           }
       }
       return anc;
   }

   // do unit testing of this class
   public static void main(String[] args) {
       In in = new In(args[0]);
       Digraph G = new Digraph(in);
       SAP sap = new SAP(G);
       while (!StdIn.isEmpty()) {
           int v = StdIn.readInt();
           int w = StdIn.readInt();
           int length   = sap.length(v, w);
           int ancestor = sap.ancestor(v, w);
           StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
       }
   }

}
