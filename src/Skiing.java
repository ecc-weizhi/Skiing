import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class Skiing {

	public static void main(String[] args) {
		int[][] map = readMapFile(FileSystems.getDefault().getPath("map.txt"));
		Solver mySolver = new Solver(map);
		
		System.out.println("Length: " + mySolver.solveLength() + " Drop: " + mySolver.solveDrop());
	}
	
	/**
	 * Read map file input
	 * 
	 * @param path File path for map file.
	 * @return A 2d array representation of map.
	 */
	private static int[][] readMapFile(Path path){
		int[][] map = null;
	 
		Charset charset = Charset.forName("US-ASCII");
		try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
			// read first line, find out map height and width.
			String line = reader.readLine();
			String[] temp = line.split(" ");
			int width = Integer.valueOf(temp[0]);
			int height = Integer.valueOf(temp[1]);
			map = new int[height][width];
			
			for(int i=0; i<height; i++){
				line = reader.readLine();
				temp = line.split(" ");
				for(int j=0; j<width; j++){
					map[i][j] = Integer.valueOf(temp[j]);
				} 
			}
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
		
		return map;
	}
}
