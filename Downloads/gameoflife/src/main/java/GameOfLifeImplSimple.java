import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Created by gulnur on 17.12.17.
 */
public class GameOfLifeImplSimple implements GameOfLife {
    private int N;
    private int M;
    private byte[][] grid;


    @Override
    public List<String> play(String inputFile) throws FileNotFoundException {

        grid = readFile(inputFile);

        for(int i=0; i<M; i++){
            int[][] sum = calculateSum();
            updateGrid(sum);
        }

        return toString(grid);
    }

    /*
       Метод calculateSum() считает сумму окрестности клеток.
     */
    private int[][] calculateSum(){
        int[][] res = new int[N][N];

        for(int i=1; i<N+1; i++){
            for(int j=1; j<N+1; j++){
                res[i-1][j-1] = grid[i][j-1] + grid[i][j+1] + grid[i-1][j] + grid[i+1][j] + grid[i-1][j-1]
                        + grid[i-1][j+1] + grid[i+1][j-1] + grid[i+1][j+1];

            }
        }

        return res;
    }

    /*
    Метод updateGrid на основе посчитанной суммы обновляет матрицу.
    */

    private void updateGrid(int[][] sum){
        for(int i=1; i<N+1; i++){
            for(int j=1; j<N+1; j++){
                if (sum[i-1][j-1]==3 || sum[i-1][j-1]==2 && grid[i][j]==1){
                    grid[i][j] = 1;
                }
                else{
                    grid[i][j] = 0;
                }
            }
        }
        grid[0][0] = grid[N][N];
        grid[0][N + 1] = grid[N][1];
        grid[N + 1][0] = grid[1][N];
        grid[N + 1][N + 1] = grid[1][1];

        for (int i = 1; i < N+1; i++) {
            grid[i][0] = grid[i][N];
            grid[i][N+1] = grid[i][1];
            grid[0][i] = grid[N][i];
            grid[N+1][i] = grid[1][i];
        }

    }

    /*
    Метод toString() переводит матрицу числовых значений в строковые.
     */
    private List<String> toString(byte[][] matrix) {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            String str = "";
            for (int j = 0; j < N; j++) {
                str = str + Byte.toString(matrix[i+1][j+1]);
            }
            result.add(str);
        }

        return result;
    }

    /*
    Метод readFile считывает с файла данные о начальном положении и инициализирует матрицу.
    */

    private byte[][] readFile(String fileName) throws FileNotFoundException {
        Scanner input = null;

        try {
            input = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (input != null) {

            N = input.nextInt();
            M = input.nextInt();
            //System.out.println(N);
            //System.out.println(M);

            grid = new byte[N+2][N+2];

            String str = "";
            for (int i = 0; i < N; i++) {
                str = input.next();
                String[] chars = str.split("");
                for (int j = 0; j < N; j++) {
                    grid[i+1][j+1] = Byte.valueOf(chars[j]);
                }
            }

            grid[0][0] = grid[N][N];
            grid[0][N + 1] = grid[N][1];
            grid[N + 1][0] = grid[1][N];
            grid[N + 1][N + 1] = grid[1][1];

            for (int i = 1; i < N+1; i++) {
                grid[i][0] = grid[i][N];
                grid[i][N+1] = grid[i][1];
                grid[0][i] = grid[N][i];
                grid[N+1][i] = grid[1][i];
            }

        }
        input.close();
        return grid;
    }
}



