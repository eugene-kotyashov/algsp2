import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class SAP {
    
    private Digraph dg;
    
    
    private class BreadthFirstDirectedPaths {
        private static final int INFINITY = Integer.MAX_VALUE;
        private boolean[] marked;  // marked[v] = is there an s->v path?
        private int[] edgeTo;   // edgeTo[v] = last edge on shortest s->v path
        private int[] distTo;      // distTo[v] = length of shortest s->v path
        
        /**
         * Computes the shortest path from {@code s} and every other 
         * vertex in graph {@code G}.
         * @param G the digraph
         * @param s the source vertex
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public BreadthFirstDirectedPaths(Digraph G, int s) {
            marked = new boolean[G.V()];
            distTo = new int[G.V()];
            edgeTo = new int[G.V()];
            for (int v = 0; v < G.V(); v++)
                distTo[v] = INFINITY;
            validateVertex(s);
            bfs(G, s);
        }
        
        /**
         * Computes the shortest path from any one of the source vertices
         * in {@code sources}
         * to every other vertex in graph {@code G}.
         * @param G the digraph
         * @param sources the source vertices
         * @throws IllegalArgumentException unless each vertex {@code v} in
         *         {@code sources} satisfies {@code 0 <= v < V}
         */
        public BreadthFirstDirectedPaths(Digraph G, Iterable<Integer> sources) {
            marked = new boolean[G.V()];
            distTo = new int[G.V()];
            edgeTo = new int[G.V()];
            for (int v = 0; v < G.V(); v++)
                distTo[v] = INFINITY;
            validateVertices(sources);
            bfs(G, sources);
        }
        
        // BFS from single source
        private void bfs(Digraph G, int s) {
            Queue<Integer> q = new Queue<Integer>();
            marked[s] = true;
            distTo[s] = 0;
            q.enqueue(s);
            while (!q.isEmpty()) {
                int v = q.dequeue();
                for (int w : G.adj(v)) {
                    if (!marked[w]) {
                        edgeTo[w] = v;
                        distTo[w] = distTo[v] + 1;
                        marked[w] = true;
                        q.enqueue(w);
                    }
                }
            }
        }
        
        // BFS from multiple sources
        private void bfs(Digraph G, Iterable<Integer> sources) {
            Queue<Integer> q = new Queue<Integer>();
            for (int s : sources) {
                marked[s] = true;
                distTo[s] = 0;
                q.enqueue(s);
            }
            while (!q.isEmpty()) {
                int v = q.dequeue();
                for (int w : G.adj(v)) {
                    if (!marked[w]) {
                        edgeTo[w] = v;
                        distTo[w] = distTo[v] + 1;
                        marked[w] = true;
                        q.enqueue(w);
                    }
                }
            }
        }
        
        /**
         * Is there a directed path from the source {@code s} (or sources) 
         * to vertex {@code v}?
         * @param v the vertex
         * @return {@code true} if there is a directed path, {@code false} 
         * otherwise
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public boolean hasPathTo(int v) {
            validateVertex(v);
            return marked[v];
        }
        
        /**
         * Returns the number of edges in a shortest path from the source 
         * {@code s}
         * (or sources) to vertex {@code v}?
         * @param v the vertex
         * @return the number of edges in a shortest path
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public int distTo(int v) {
            validateVertex(v);
            return distTo[v];
        }
        
        /**
         * Returns a shortest path from {@code s} (or sources) to {@code v}, or
         * {@code null} if no such path.
         * @param v the vertex
         * @return the sequence of vertices on a shortest path, as an Iterable
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public Iterable<Integer> pathTo(int v) {
            validateVertex(v);
            
            if (!hasPathTo(v)) return null;
            Stack<Integer> path = new Stack<Integer>();
            int x;
            for (x = v; distTo[x] != 0; x = edgeTo[x])
                path.push(x);
            path.push(x);
            return path;
        }
        
        // throw an IllegalArgumentException unless {@code 0 <= v < V}
        private void validateVertex(int v) {
            int V = marked.length;
            if (v < 0 || v >= V)
                throw new IllegalArgumentException
                ("vertex " + v + " is not between 0 and " + (V-1));
        }
        
        // throw an IllegalArgumentException unless {@code 0 <= v < V}
        private void validateVertices(Iterable<Integer> vertices) {
            if (vertices == null) {
                throw new IllegalArgumentException("argument is null");
            }
            int V = marked.length;
            for (int v : vertices) {
                if (v < 0 || v >= V) {
                    throw new IllegalArgumentException
                        ("vertex " + v + " is not between 0 and " + (V-1));
                }
            }
        }
    }

     
            

   // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) 
            throw new java.lang.IllegalArgumentException();
        dg = new Digraph(G.V());
        for (int iv = 0; iv < G.V(); iv++) {
            for (int v : G.adj(iv)) {
                dg.addEdge(iv,v);
            }
        }
    }

   // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if ( (v < 0)||(v > dg.V()-1) ||  (w < 0) || (w > dg.V()-1) )
            throw new java.lang.IllegalArgumentException();
        BreadthFirstDirectedPaths bfv = 
            new BreadthFirstDirectedPaths(dg, v);
        BreadthFirstDirectedPaths bfw = 
            new BreadthFirstDirectedPaths(dg, w);
        int len = Integer.MAX_VALUE;
        for(int vx = 0; vx < dg.V(); vx++) {
            if ( bfv.hasPathTo(vx) && bfw.hasPathTo(vx) ) {
                int tmp = bfv.distTo(vx)+ bfw.distTo(vx);
                if ( len > tmp) len = tmp;
            }
        }
        if (len < Integer.MAX_VALUE)        
            return len;
        return -1;
    }

   // a common ancestor of v and w that participates in 
    //a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w) {
       if ( (v < 0)||(v > dg.V()-1) ||  (w < 0) || (w > dg.V()-1) )
            throw new java.lang.IllegalArgumentException();
       BreadthFirstDirectedPaths bfv = 
            new BreadthFirstDirectedPaths(dg, v);
       BreadthFirstDirectedPaths bfw = 
            new BreadthFirstDirectedPaths(dg, w);
       int len = Integer.MAX_VALUE;
       int anc = -1;
       for(int vx = 0; vx < dg.V(); vx++) {
           if ( bfv.hasPathTo(vx) && bfw.hasPathTo(vx) ) {
               int tmp = bfv.distTo(vx)+ bfw.distTo(vx);
               if ( len > tmp) {
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
       for(int vx = 0; vx < dg.V(); vx++) {
           if ( bfv.hasPathTo(vx) && bfw.hasPathTo(vx) ) {
               int tmp = bfv.distTo(vx)+ bfw.distTo(vx);
               if ( len > tmp) len = tmp;
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
       for(int vx = 0; vx < dg.V(); vx++) {
           if ( bfv.hasPathTo(vx) && bfw.hasPathTo(vx) ) {
               int tmp = bfv.distTo(vx)+ bfw.distTo(vx);
               if ( len > tmp) {
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
