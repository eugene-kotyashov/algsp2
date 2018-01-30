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
       return new int[1];
   }
// remove horizontal seam from current picture
   public    void removeHorizontalSeam(int[] seam) {
   }
// remove vertical seam from current picture
   public    void removeVerticalSeam(int[] seam) {
   }
}