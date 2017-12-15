package ai;

public class ChezzAI {

	static String hallo = "Hallo";
	public static void main(String[] args) {
		System.out.println(hallo);
		System.out.println(fib(10));
	}
	
	private static int fib(int x) {
		if(x <= 0) {
			return 0;
		}else if(x < 2) {
			return 1;
		} else {
			return fib(x-1)+fib(x-2);
		}
	}

}
