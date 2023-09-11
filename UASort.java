/*  Date:           06/29/2020
 *  Filename:       UASort.java
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class UASort {
	
    static final int INITIAL_SIZE = 1;		

    public int[] A;
    public String[] B;

    
    public UASort() {
       this(INITIAL_SIZE); 
    }

    
    public UASort(int size) {

    }
	
	public static void main(String[] args) throws IOException {
		
		String console;
		UASort sort = new UASort();
		int size = 100;
		
		int[] A = new int[size];
		String[] B = new String[size];
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while((console = br.readLine()) != null) {
			String[] tokens = console.split(" ");
			
		    String input = tokens[0];
		    String output = tokens[1];
		    String data = tokens[2];
		    String order = tokens[3];
		    
		    if (data == "numeric" ) {
		    	if(order == null) {
		    		System.out.println("Error. No order selected.");	
		    	} else {
		    		sort.nLoadData(A, input);
		    		int r = sort.numSize(A);
			    	sort.numqs(A, 0, r - 1, order);
			    	sort.nSaveData(A, output);
		    	}
		    } else if(data == "text") {
		    	if(order == null) {
		    		System.out.println("Error. No order selected.");
		    	} else {
		    		sort.tLoadData(B, input);
		    		int r = sort.numSize(A);
			    	sort.textqs(B, 0, r - 1, order);
			    	sort.tSaveData(B, output);
		    	}
		    	
		    } else {
		    	System.out.println("Error: No data entered.");
		    }
		}
	}
	
    public void nLoadData(int[] A, String filename) {
    	String line;
    	int i = 0;
    	
    	try {
	    	BufferedReader br = new BufferedReader(new FileReader(filename));
	    	
	    	while((line = br.readLine()) != null) {
                A[i] = Integer.parseInt(line);
                i++;    
            }
	    	
	    	br.close();
	    	
    	} catch(Exception e) {
    		System.out.println("Error: " + e);
    	}
    }
    
    public void tLoadData(String[] B, String filename) {
    	String line;
    	int i = 0;
    	
    	try {
	    	BufferedReader br = new BufferedReader(new FileReader(filename));
	    	
	    	while((line = br.readLine()) != null) {
                B[i] = line;
                i++;    
	    	}
	    	
	    	br.close();
	    	
    	} catch(Exception e) {
    		System.out.println("Error: " + e);
    	}
    }
    
    public void nSaveData(int[] A, String filename) {
    	int size = numSize(A);
    	
    	for(int i = 0; i < size; i++) {
    		System.out.println(A[i]);
    	}
    	
    	try {
    		BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
    		
    		for(int i = 0; i < size; i++) {
    			bw.write(A[i]);
    		}
    		
    		bw.close();
    	} catch(Exception e) {
    		System.out.println("Error: " + e);
    	}
    	
    }
    
    public void tSaveData(String[] B, String filename) {
    	int size = textSize(B);
    	
    	for(int i = 0; i < size; i++) {
    		System.out.println(B[i]);
    	}
    	
    }
	
	public int numSelectPivot(int[] A, int p, int r, String order) {
		int mid = r;
		
		if(r < 3) {
			mid = r;
		} else {
			mid = (p + r) / 2;
			if(A[p] < A[mid] && A[mid] < A[r]) {
				numSwap(A, mid, r);
			}
			if(A[mid] < A[p] && A[p] > A[r]) {
				numSwap(A, p, r);
			}
		}
		if (order == "ascending" ) {
			return nPartition(A, p, r);
		} else {
			return nPartitionD(A, p, r);
		}
    	
	}
	
	public int textSelectPivot(String[] B, int p, int r, String order) {
		int mid = 0;
		
		if(r < 3) {
			mid = r;
		} else {
			mid = (p + r) / 2;
			String last = B[r];
			String first = B[p];
			String med = B[mid];
			
			if(first.charAt(0) < med.charAt(0) && med.charAt(0) < last.charAt(0)) {
				textSwap(B, mid, r);
			}
			if(med.charAt(0) < first.charAt(0) && first.charAt(0) > last.charAt(0)) {
				textSwap(B, mid, p);
			}
		}
    	
		if (order == "ascending" ) {
			return tPartition(B, p, r);
		} else {
			return tPartitionD(B, p, r);
		}
	}
	
	public void numqs(int[] A, int p, int r, String order) {
			if(p < r) {
				int q = numSelectPivot(A, p, r, order);
				numqs(A, p, q - 1, order);
				numqs(A, q + 1, r, order);
			}
	}
	
	public int nPartition(int[] A, int p, int r) {
		int x = A[r];
		int i = p - 1;
		
		for(int j = p; j < r - 1; j++) {
			if(A[j] <= x) {
				i = i + 1;
				numSwap(A, i, j);
			}
		}
		
		int temp = A[i + 1];
		A[i + 1] = A[r];
		A[r] = temp;
		
		return i + 1;
	}
	
	public int nPartitionD(int[] A, int p, int r) {
		int x = A[r];
		int i = p - 1;
		
		for(int j = p; j < r - 1; j++) {
			if(A[j] >= x) {
				i = i + 1;
				numSwap(A, i, j);
			}
		}
		
		int temp = A[i + 1];
		A[i + 1] = A[r];
		A[r] = temp;
		
		return i + 1;
	}
	
	public void textqs(String[] B, int p, int r, String order) {
		if(p < r) {
			int q = textSelectPivot(B, p, r, order);
			textqs(B, p, q - 1, order);
			textqs(B, q + 1, r, order);
		}
	}
	
	public int tPartition(String[] B, int p, int r) {
		String x = B[r];
		int i = p - 1;
		
		for(int j = p; j < r - 1; j++) {
			String current = B[j];
			if(current.charAt(0) <= x.charAt(0)) {
				i = i + 1;
				textSwap(B, i, j);
			}
		}
		
		String temp = B[i + 1];
		B[i + 1] = B[r];
		B[r] = temp;
		
		return i + 1;
	}
	
	public int tPartitionD(String[] B, int p, int r) {
		String x = B[r];
		int i = p - 1;
		
		for(int j = p; j < r - 1; j++) {
			String current = B[j];
			if(current.charAt(0) >= x.charAt(0)) {
				i = i + 1;
				textSwap(B, i, j);
			}
		}
		
		String temp = B[i + 1];
		B[i + 1] = B[r];
		B[r] = temp;
		
		return i + 1;
	}
	
	public int numSize(int[] A) {
		int count = 0;
    	int i = 0;
    	
    	while(A[i] != 0 && i < A.length) {
    		count++;
    		i++;
    	}
    	return count;
	}
	
	public int textSize(String[] B) {
		int count = 0;
    	int i = 0;
    	
    	while(B[i] != null && i < B.length) {
    		count++;
    		i++;
    	}
    	return count;
	}
	
	public void numSwap(int[] A, int i, int j) {
		int temp = A[i]; 
    	A[i] = A[j];
    	A[j] = temp;
	}
	
	public void textSwap(String[] B, int i, int j) {
		String temp = B[i]; 
    	B[i] = B[j];
    	B[j] = temp;
	}

}
