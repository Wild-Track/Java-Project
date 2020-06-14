package Utils;

import java.util.function.IntFunction;
import java.util.stream.StreamSupport;
import java.util.stream.Stream;

public class IterableUtils {

    public static <T> T[] toArray(Iterable<T> iterable, IntFunction<T[]> generator) {
        return StreamSupport.stream(iterable.spliterator(), false).toArray(generator);
    }

    public static <T> Stream<T> toStream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

}