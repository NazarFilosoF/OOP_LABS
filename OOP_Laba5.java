import java.text.CompactNumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OOP_Laba5 {
    public static class Pair<A, B> {
        protected A first;
        protected B second;
	public Pair(A first, B second){
		this.first = first;
		this.second = second;
	}
	public A getFirst() {return first; }
	public B getSecond() {return second; }
    }
    public static <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    public static class ComparablePair<A extends Comparable<A>> extends Pair<A, A> {
        public ComparablePair(A first, A second) {
            super(first, second);
        }
        public A min() {
            return first.compareTo(second) <= 0 ? first : second;
        }
        public A max() {
            return first.compareTo(second) > 0 ? first : second;
        }
    }
    public static class Repository<T> {
        private Map<Long, T> storage = new HashMap<>();
        private long nextId = 1;

        public long save(T entity) {
            long id = nextId++;
            storage.put(id, entity);
            return id;
        }

        public T findById(long id) {
            return storage.get(id);
        }

        public boolean delete(long id) {
            return storage.remove(id) != null;
        }

        public List<T> findAll() {
            return new ArrayList<>(storage.values());
        }
    }
    public static void main(String[] args){
        Pair<String, Integer> pair1 = new Pair<>("GGWP", 7);
        ComparablePair<Integer> pair2 = new ComparablePair<>(10, 7);
        System.out.println(pair1.getFirst());
        System.out.println(pair2.min());
        System.out.println(pair2.max());
        Repository<Integer> Spisok = new Repository<>();
        Spisok.save(10);
        Spisok.save(3);
        Spisok.save(27);
        System.out.println(Spisok.findAll());
    }
}