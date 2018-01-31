import edu.princeton.cs.algs4.Picture;


public class SeamCarver {
   private final double BORDER_ENERGY = 1000;
   private Picture pic;
   double energy[][];
// create a seam carver object based on the given picture
   public SeamCarver(Picture picture) {               
       pic = picture;
       energy = new double[pic.width()][pic.height()];
       for(int x = 0; x < pic.width(); x++) {
           for(int y = 0; y < pic.height()-1; y++) {
               energy[x][y] = energy(x,y);
           }
       }
           
           
   }
// current picture       
   public Picture picture() {
       return pic;
   }
// width of current picture
   public     int width() {
       return pic.width();
   }
// height of current picture
   public     int height() {
       return pic.height();
   }
// energy of pixel at column x and row y
   public  double energy(int x, int y) {
       if (( x < 0 ) || (x > pic.width() - 1) ||
           ( y < 0 ) || (y > pic.height() - 1)) {
           throw new java.lang.IllegalArgumentException();
       }
       if ((x == 0) || (x == pic.width()-1) || (y == 0) || (y == pic.height()-1))
           return BORDER_ENERGY;
       double Rx =  pic.get(x - 1,y).getRed() - pic.get(x + 1,y).getRed();
       double Gx =  pic.get(x - 1,y).getGreen() - pic.get(x + 1,y).getGreen();
       double Bx =  pic.get(x - 1,y).getBlue() - pic.get(x + 1,y).getBlue();
       double Ry =  pic.get(x,y - 1).getRed() - pic.get(x,y + 1).getRed();
       double Gy =  pic.get(x,y - 1).getGreen() - pic.get(x,y + 1).getGreen();
       double By =  pic.get(x,y - 1).getBlue() - pic.get(x,y + 1).getBlue();
       
       return Math.sqrt(Rx*Rx + Bx*Bx + Gx*Gx + Ry*Ry + Gy*Gy + By*By);
   }
// sequence of indices for horizontal seam
   public   int[] findHorizontalSeam() {
       return new int[1];
   }
// sequence of indices for vertical seam
   public   int[] findVerticalSeam() {
       int [] vertSeam = new int[pic.height()];
       int [][] potentialSeams = new int[pic.width()][pic.height()];
       double [] seamMinEnergies = new double[pic.width()];
       double [][] totalMinEnergy = new double[pic.width()][pic.height()];
       int [][] prevX = new int[pic.width()][pic.height()];
       for(int x = 0; x < pic.width()-1; x++) {
           
           for(int ix = 0; ix < pic.width(); ix++) {
               for(int y = 0; y < pic.height(); y++) {
                   totalMinEnergy[ix][y] = Double.POSITIVE_INFINITY;
                   prevX[ix][y] = -1;
               }
           }
           
           for(int y = 1; y < pic.height()-1; y++) {
               for (int nextX = x-y; nextX <= x+y; nextX++) {
                   if ((nextX < 0) || (nextX > pic.width()-1)) continue;
                   
                   for(int xk = nextX-1; xk<=nextX+1; xk++) {
                       if ((xk < x-y+1) || (xk > x+y-1)) continue;
                       if (totalMinEnergy[nextX][y] < 
                           energy[nextX][y] + energy[xk][y]) {
                           totalMinEnergy[nextX][y] = 
                               energy[nextX][y] + energy[xk][y];
                           prevX[nextX][y] = xk;
                       }
                   } 
                   
               }
           }
           double minE = Double.POSITIVE_INFINITY;
           int minE_X = -1;
           for(int ix = 0; ix < pic.width(); ix++) {
               if (minE < totalMinEnergy[x][pic.height()-1]) {
                   minE = totalMinEnergy[x][pic.height()-1];
                   minE_X = x;
               }
           }
           seamMinEnergies[x] = minE;
       //get lowest energy path with ending point minE_x and starting point
           // at x
           int ix = minE_X;
           for(int y = pic.height()-1; y>0; y--) {
               potentialSeams[x][y] = ix;
               ix = prevX[ix][y];
           }
               
       }
       return vertSeam;
   }
// remove horizontal seam from current picture
   public    void removeHorizontalSeam(int[] seam) {
   }
// remove vertical seam from current picture
   public    void removeVerticalSeam(int[] seam) {
       
   }
}