public class OOP_Task5 {
    public static class Converter<S, T> {
        private final Class<T> targetClass;

        public Converter(Class<T> targetClass){
            this.targetClass = targetClass;
        }

        @SuppressWarnings("unchecked")
        public T convert(S value) {
            if (value == null) return null;
            if (targetClass.isInstance(value)) {return (T) value; }
            if (targetClass == String.class) {return (T) value.toString(); }
            if (value instanceof Number) {
                Number num = (Number) value;
                if (targetClass == Integer.class) return (T) Integer.valueOf(num.intValue());
                if (targetClass == Double.class)  return (T) Double.valueOf(num.doubleValue());
                if (targetClass == Long.class)    return (T) Long.valueOf(num.longValue());
                if (targetClass == Float.class)   return (T) Float.valueOf(num.floatValue());
            }
            if (value instanceof String){
                String s = (String) value;
                try {
                    if (targetClass == Integer.class) return (T) Integer.valueOf(s.trim());
                    if (targetClass == Double.class)  return (T) Double.valueOf(s.trim());
                    if (targetClass == Boolean.class) return (T) Boolean.valueOf(s.trim());
                    if (targetClass == Boolean.class) return (T) Boolean.valueOf(s.trim());
                } catch (NumberFormatException e){
                    throw new IllegalArgumentException("НЕЛЬЗЯ АЙ АЙ АЙ");
                }
            }
            throw new IllegalArgumentException("НЕЛЬЗЯ АЙ АЙ АЙ");
        }
    }
    public static void main(String[] args){
        Converter<Object, Integer> toInt = new Converter<>(Integer.class);
        Converter<Object, Double> toDouble = new Converter<>(Double.class);
        Converter<Object, String> toString = new Converter<>(String.class);

        System.out.println(toInt.convert(42));
        System.out.println(toInt.convert("123"));
        System.out.println(toInt.convert(3.99));
        System.out.println(toDouble.convert("3.14"));
        System.out.println(toString.convert(42));
    }
}