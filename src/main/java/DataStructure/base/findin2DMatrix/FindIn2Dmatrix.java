package DataStructure.base.findin2DMatrix;

public class FindIn2Dmatrix {

    public boolean Find(int target, int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return false;
        int rows = matrix.length, cols = matrix[0].length;
        int r = 0, c = cols - 1; // 从右上角开始
        while (r <= rows - 1 && c >= 0) {
            if (target == matrix[r][c])
                return true;
            else if (target > matrix[r][c])
                r++;
            else
                c--;
        }
        return false;
    }

    public static void main(String[] args) {
        int[][] matrix = {{1,2,5},{3,5,8},{3,6,9}};
        int am = 7;
        FindIn2Dmatrix finder=new FindIn2Dmatrix();
        System.out.println(finder.Find(am,matrix));
    }

}
