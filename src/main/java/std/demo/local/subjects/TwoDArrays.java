package std.demo.local.subjects;

import java.util.Random;

public class TwoDArrays {

	public static void main(String[] args) {
		//二维数组大小
		int size = 10;
		
		int[][] arrys = new int[size][size];

		Random r = new Random();
		//赋值 begin
		for (int c = 0; c < size; c++) {
			for (int d = 0; d < size; d++) {
				arrys[c][d] = r.nextInt(8) + 1;
			}
		}
		//赋值 end
		
		//循环显示 begin
		System.out.println("变化前");
		for (int c = 0; c < size; c++) {
			for (int d = 0; d < size; d++) {
				if ((d + 1) % size != 0)
					System.out.print(arrys[c][d] + " ");
				else
					System.out.println(arrys[c][d]);
			}
		}
		//循环显示 end
		
		//改右上角为0 begin
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i <= j)
					arrys[i][j] = 0;
			}
		}
		//改右上角为0 end
		
		//循环显示 begin
		System.out.println("变化后");
		for (int c = 0; c < size; c++) {
			for (int d = 0; d < size; d++) {
				if ((d + 1) % size != 0)
					System.out.print(arrys[c][d] + " ");
				else
					System.out.println(arrys[c][d]);
			}
		}
		//循环显示 end
	}

}
