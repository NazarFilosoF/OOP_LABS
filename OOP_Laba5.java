import java.util.ArrayList;
import java.util.List;

public class OOP_Laba5 {
    public class Pair<A, B> {
        private A first;
        private B string;
	public Pair(A first, B second){
		this.first = first;
		this.second = second;
	}
	public A getfirst() {return first; }
	public B getsecond() {return second; }
    }
    public static void main(String[] args){
        
    }
}

// Converter<S, T> — преобразователь из типа S в тип T.