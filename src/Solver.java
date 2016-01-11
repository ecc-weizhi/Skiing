import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A solver to solve Skiing problem. 
 * 
 * The problem is defined as follows:
 * Given a x by y array of cells where each cell has a height value, find the longest path possible
 * for the given map subject to the following constraints:
 * - you can only move to adjacent cells (up, down, left, right only. No diagonals.)
 * - you can only travel to a cell with a lower height than your current cell.
 * - if there are multiple path with same length, pick the path with the largest difference between
 *   first and last cell.
 * 
 * The runtime complexity of this algorithm is O(V) where V is the number of cells in the map.
 * In solve() method, we traverse all possible path from a given starting cell. We flag every
 * cell we visited while traversing by updating the cell's corresponding element in pathMap.
 * We do nothing for cells which are already flagged. Therefore each cell can only be visited once.
 * We called solve() V number of times. Therefore we visited every single cell only once and did
 * a constant amount of work at each cell. Therefore runtime complexity is O(V).
 * 
 * @author Lin Weizhi
 *
 */
public class Solver {
	private int[][] map;
	private int mapHeight;
	private int mapWidth;
	private List<List<LinkedList<Integer>>> pathMap;
	
	private int length;
	private int drop;
	
	
	public Solver(int[][] map){
		this.map = map;
		this.mapHeight = map.length;
		this.mapWidth = map[0].length;
		this.pathMap = new ArrayList<List<LinkedList<Integer>>>();
		for(int i=0; i<mapHeight; i++){
			pathMap.add(new ArrayList<LinkedList<Integer>>());
			for(int j=0; j<mapWidth; j++){
				pathMap.get(i).add(new LinkedList<Integer>());
			}
		}
		
		this.length = -1;
		this.drop = -1;
	}
	
	public int solveLength(){
		if(length == -1){
			// we do not have an answer yet
			for(int i=0; i<map.length; i++){
				for(int j=0; j<map[0].length; j++){
					LinkedList<Integer> temp = solve(i, j);
					if(temp.size() > length){
						length = temp.size();
						drop = temp.getFirst() - temp.getLast();
					}
					else if(temp.size() == length ){
						drop = Math.max(temp.getFirst() - temp.getLast(), drop);
					}
				}
			}
		}
		
		return length;
	}
	
	public int solveDrop(){
		if(drop == -1){
			// we do not have an answer yet
			for(int i=0; i<map.length; i++){
				for(int j=0; j<map[0].length; j++){
					LinkedList<Integer> temp = solve(i, j);
					if(temp.size() > length){
						length = temp.size();
						drop = temp.getFirst() - temp.getLast();
					}
					else if(temp.size() == length ){
						drop = Math.max(temp.getFirst() - temp.getLast(), drop);
					}
				}
			}
		}
		
		return drop;
	}
	
	/**
	 * This method returns the sequence of height of the optimal path from the cell at position i,j. 
	 * Starting from the cell at i,j, we check if it is possible to move to an adjacent cell. 
	 * We then recursively call this method for all possible adjacent cells, pick the optimal sequence and
	 * prefix the sequence with the height of cell at i,j.
	 * 
	 * This method works because for an optimal path (v1, v2, ...., vn), (v2, v3, ...., vn) is also an
	 * optimal path. 
	 * 
	 * @param i The row the cell we are working on is in.
	 * @param j The column the cell we are working on is in.
	 * @return A LinkedList storing the longest path with a steepest drop possible from i,j.
	 */
	private LinkedList<Integer> solve(int i, int j){
		if(pathMap.get(i).get(j) == null || pathMap.get(i).get(j).size() == 0){
			// this is a new cell.
			int cellHeight = map[i][j];
			
			LinkedList<Integer> currentLL = new LinkedList<Integer>();
			LinkedList<Integer> tempLL;
			
			// try travel all 4 direction in sequence: up, right, down, left
			if(i > 0){							// check for traveling up
				if(map[i-1][j] < cellHeight){	// check for cell height
					tempLL = solve(i-1, j);
					if(tempLL.size() > currentLL.size()){
						currentLL = new LinkedList<Integer>(tempLL);
					}
					else if(tempLL.size() == currentLL.size()){
						if(tempLL.getLast() < currentLL.getLast())
							currentLL = new LinkedList<Integer>(tempLL);
					}
				}
			}
			
			if(j < mapWidth-1){					// check for traveling right
				if(map[i][j+1] < cellHeight){	// check for cell height
					tempLL = solve(i, j+1);
					if(tempLL.size() > currentLL.size()){
						currentLL = new LinkedList<Integer>(tempLL);
					}
					else if(tempLL.size() == currentLL.size()){
						if(tempLL.getLast() < currentLL.getLast())
							currentLL = new LinkedList<Integer>(tempLL);
					}
				}
			}
			
			if(i < mapHeight-1){					// check for traveling down
				if(map[i+1][j] < cellHeight){	// check for cell height
					tempLL = solve(i+1, j);
					if(tempLL.size() > currentLL.size()){
						currentLL = new LinkedList<Integer>(tempLL);
					}
					else if(tempLL.size() == currentLL.size()){
						if(tempLL.getLast() < currentLL.getLast())
							currentLL = new LinkedList<Integer>(tempLL);
					}
				}
			}
			
			if(j > 0){							// check for traveling left
				if(map[i][j-1] < cellHeight){	// check for cell height
					tempLL = solve(i, j-1);
					if(tempLL.size() > currentLL.size()){
						currentLL = new LinkedList<Integer>(tempLL);
					}
					else if(tempLL.size() == currentLL.size()){
						if(tempLL.getLast() < currentLL.getLast())
							currentLL = new LinkedList<Integer>(tempLL);
					}
				}
			}
			
			currentLL.addFirst(cellHeight);
			pathMap.get(i).set(j, currentLL);
			
			return currentLL;
			
		}
		else{
			// we did this cell before.
			return new LinkedList(pathMap.get(i).get(j));
		}
	}
	
	/**
	 * For debugging.
	 */
	public void printPathMap(){
		for(List<LinkedList<Integer>> i: pathMap){
			for(LinkedList<Integer> j: i){
				for(Integer k: j){
					System.out.print(k + " ");
				}
				System.out.println();
			}
		}
	}
	
	
	
}
