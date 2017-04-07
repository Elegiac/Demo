package std.demo.local.subjects;

public class Some {

	public static void main(String[] args) {
//		A a1 = new A();
//		A a2 = new B();
//		B b = new B();
//		C c = new C();
//		D d = new D();
//		System.out.println(a1.show(b));
//		System.out.println(a1.show(c));
//		System.out.println(a1.show(d));
//		System.out.println(a2.show(b));
//		System.out.println(a2.show(c));
//		System.out.println(a2.show(d));
//		System.out.println(b.show(b));
//		System.out.println(b.show(c));
//		System.out.println(b.show(d));
		CC c0 = new CC();
		
		
		CC c1 = new CC();
		//c1.name();
		AA c2 = new AA();
		
		c0.name2(c1);
		c0.name2(c2);
	}

}

class A {
	public String show(D obj) {
		return ("A and D");
	}

	public String show(B obj) {
		return ("A and B");
	}

	public String show(A obj) {
		return ("A and A");
	}
}

class B extends A {
	public String show(B obj) {
		return ("B and B");
	}

	public String show(A obj) {
		return ("B and A");
	}
}

class C extends B {
}

class D extends B {
}

class AA extends BB{
	
}
class BB extends CC{
	public void name() {
		System.err.println("b");
	}
}
class CC{
	public void name() {
		System.err.println("c");
	}
	
	public void name2(CC c) {
		System.err.println("c_c");
	}
	
	public void name2(AA a) {
		System.err.println("c_a");
	}
	
}


