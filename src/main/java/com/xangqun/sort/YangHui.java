package com.xangqun.sort;

public class YangHui {

	public static void  sort(int count){

		int[][] yangHui = new int[count][count];

		for(int i=0;i<count;i++){
			yangHui[i][i] = 1;
			yangHui[i][0] = 1;
		}
		for(int i=2; i<count; i++) {
			for(int j=1; j<i; j++) {
				yangHui[i][j] = yangHui[i-1][j-1] + yangHui[i-1][j];
			}
		}
		for(int i=0; i<count; i++) {
			for(int k=0; k<2*(count-i)-1; k++) {
				System.out.print("*");
			}
		    for(int j=0; j<=i; j++) {
			    System.out.print(yangHui[i][j] + "   ");
			}
		    System.out.println();
		}
	}

	public static void main(String[] args) {
		sort(10);
	}
}
