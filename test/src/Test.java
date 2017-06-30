
public class Test {

	int min1 = 0, 
		min2 = 0, 
		pos1 = 0, 
		pos2 = 0;
	
	public void setValue(int num, int pos, int length){
		System.out.println(pos);
		if( pos == 0 || pos == length-1 || Math.abs(pos - pos1) == 1 ){
			return;
		}
		if( num < min1 ){
			min2 = min1;
			min1 = num;
			pos2 = pos1;
			pos1 = pos;
		}else if( num > min1 && num < min2 ){
			min2 = num;
			pos2 = pos;
		}
	}
	
	public int breakchain(int[] A){
		int length = A.length, i = 0;
		min1 = A[1]; 
		pos1 = 1;
		min2 = A[length - 2]; 
		pos2 = length - 2;
		if( length % 2 == 1){
			i = 1;
		}
		for(; i < A.length / 2; i++ ){
			if( i == ( A.length / 2 - 1 ) ){
				if( A[i] < A[length - 1 - i] ){
					setValue(A[i], i, length);
				}else{
					setValue(A[length- 1 - i], length - 1 - i, length);
				}
			}else{
				setValue(A[i], i, length);
				setValue(A[length - 1 - i], length - 1 - i, length);
			}
		}
		System.out.println(pos1 + "  - " + pos2);
		System.out.println(min1 + "  - " + min2);
		return min1 + min2;
	}
	
	
	public static void main(String[] args) {
		Test test = new  Test();
		int[] a = {4,54,21,3,62,7,8,55,73};
		System.out.println(test.breakchain(a));
	}
	
}
