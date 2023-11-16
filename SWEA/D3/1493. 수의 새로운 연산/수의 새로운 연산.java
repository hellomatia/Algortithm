
import java.io.*;
import java.util.*;

class Point {
	int x;
	int y;
	
	Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

public class Solution {
	
	private static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
	private static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	private static int T, p, q;
	private static int[][] map;
	private static int count;
	private static Point[] points; 

	public static void main(String args[]) throws Exception {
		solution();
	}
	
	private static void solution() throws IOException {
		initT();
		initMap();
		for (int testCase = 1; testCase <= T; testCase++) {
			initPandQ();
			printResult(testCase, calculateResult());
		}
		bw.flush();
		bw.close();
	}
	
	private static void initT() throws IOException {
		T = Integer.parseInt(bf.readLine());
	}
	
	private static void initMap() {
		map = new int[300][300];
		points = new Point[90_000];
		count = 1;
		for (int row = 299; row >= 0; row--) {
			initColByRow(row);
		}
	}
	
	private static void initColByRow(int row) {
		int col = 0;
		while (row < 300 && col < 300) {
			map[row][col] = count;
			points[count] = new Point(300 - row, col + 1);
			row ++;
			col ++;
			count++;
		}
	}
	
	private static void initPandQ() throws IOException {
		StringTokenizer st = new StringTokenizer(bf.readLine());
		p = Integer.parseInt(st.nextToken());
		q = Integer.parseInt(st.nextToken());
	}
	
	private static int calculateResult() {
		int row = points[p].x + points[q].x;
		int col = points[p].y + points[q].y;
		
		return map[300 - row][col - 1];
	}
	
	
	private static void printResult(int testCase, int result) throws IOException {
		bw.write("#"+testCase + " " + result);
		bw.write("\n");
	}
}
