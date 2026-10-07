import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static com.google.common.truth.Truth.assertThat;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.List;

public class ArrayDeque61BTest {

    @Nested
    class AddOperation {

        @Test
        public void testAddFirst() {
            // flags: add from empty, add from non-empty
            Deque61B<Integer> list = new ArrayDeque61B<>();
            list.addFirst(3);
            assertThat(list.toList()).containsExactly(3);
            list.addFirst(2);
            assertThat(list.toList()).containsExactly(2, 3).inOrder();
            list.addFirst(1);
            assertThat(list.toList()).containsExactly(1, 2, 3).inOrder();
        }

        @Test
        public void testAddLast() {
            // flags: add from empty, add from non-empty
            Deque61B<String> list = new ArrayDeque61B<>();
            list.addLast("front");
            assertThat(list.toList()).containsExactly("front");
            list.addLast("middle");
            assertThat(list.toList()).containsExactly("front", "middle").inOrder();
            list.addLast("end");
            assertThat(list.toList()).containsExactly("front", "middle", "end").inOrder();
        }

    }

    @Test
    public void testToList() {
        // flags: tolist-from-empty, tolist-from-nonempty
        Deque61B<String> list = new ArrayDeque61B<>();
        assertThat(list.toList()).isEmpty();

        list.addFirst("middle");
        assertThat(list.toList()).containsExactly("middle");
        list.addLast("end");
        assertThat(list.toList()).containsExactly("middle", "end").inOrder();
        list.addFirst("front");
        assertThat(list.toList()).containsExactly("front", "middle", "end").inOrder();
/*
        list.removeLast();
        assertThat(list.toList()).containsExactly("front", "middle").inOrder();
        list.removeFirst();
        assertThat(list.toList()).containsExactly("middle").inOrder();
        list.addLast("new-end");
        assertThat(list.toList()).containsExactly("middle", "new-end");
        list.removeFirst();
        assertThat(list.toList()).containsExactly("new-end");
        list.removeLast();
        assertThat(list.toList()).isEmpty();
*/
    }

    @Test
    public void testResize() {
        Deque61B<Integer> list = new ArrayDeque61B<>();

        for (int i = 0; i < 64; i++) {
            list.addFirst(i * i);
        }
        List<Integer> expected = new java.util.ArrayList<>(
                IntStream.range(0, 64).map(i -> (63 - i) * (63 - i))
                .boxed()
                .toList());
        assertThat(list.toList()).isEqualTo(expected);
        assertThat(list.size()).isEqualTo(64);

        for (int i = 64; i < 301; i++) {
            list.addLast(i * 2);
        }
        expected.addAll(
                IntStream.range(64, 301)
                        .map(i -> i * 2)
                        .boxed().toList());
        assertThat(list.toList()).isEqualTo(expected);
        assertThat(list.size()).isEqualTo(301);

        assertThat(list.get(0)).isEqualTo(expected.get(0));
        assertThat(list.getRecursive(33)).isEqualTo(expected.get(33));
        assertThat(list.get(88)).isEqualTo(expected.get(88));
        assertThat(list.getRecursive(256)).isEqualTo(expected.get(256));
        assertThat(list.toList()).isEqualTo(expected);

        assertThat(list.removeFirst()).isEqualTo(expected.get(0));
        assertThat(list.removeLast()).isEqualTo(expected.get(300));
        assertThat(list.size()).isEqualTo(299);

    }

    @Nested
    class RemoveOperation {

        @Test
        public void testRemoveFirst() {
            Deque61B<String> list = new ArrayDeque61B<>();
            assertThat(list.removeFirst()).isNull();
            assertThat(list.toList()).isEmpty();

            list.addFirst("second");
            list.addFirst("first");
            list.addLast("third");
            assertThat(list.removeFirst()).isEqualTo("first");
            assertThat(list.toList()).containsExactly("second", "third").inOrder();
            list.addLast("fourth");
            assertThat(list.removeFirst()).isEqualTo("second");
            assertThat(list.toList()).containsExactly("third", "fourth").inOrder();
            assertThat(list.removeFirst()).isEqualTo("third");
            assertThat(list.toList()).containsExactly("fourth").inOrder();
            assertThat(list.removeFirst()).isEqualTo("fourth");
            assertThat(list.toList()).isEmpty();
            assertThat(list.removeFirst()).isNull();
            assertThat(list.toList()).isEmpty();
        }

        @Test
        public void testRemoveLast() {
            Deque61B<String> list = new ArrayDeque61B<>();
            assertThat(list.removeLast()).isNull();
            assertThat(list.toList()).isEmpty();

            list.addFirst("second");
            list.addFirst("first");
            list.addLast("third");
            assertThat(list.removeLast()).isEqualTo("third");
            assertThat(list.toList()).containsExactly("first", "second").inOrder();
            list.addFirst("zeroth");
            assertThat(list.removeLast()).isEqualTo("second");
            assertThat(list.toList()).containsExactly("zeroth", "first").inOrder();
            assertThat(list.removeLast()).isEqualTo("first");
            assertThat(list.toList()).containsExactly("zeroth").inOrder();
            assertThat(list.removeLast()).isEqualTo("zeroth");
            assertThat(list.toList()).isEmpty();
            assertThat(list.removeLast()).isNull();
            assertThat(list.toList()).isEmpty();
        }
    }

    @Nested
    class GetOperation {

        @Test
        public void testGet() {
            Deque61B<String> list = new ArrayDeque61B<>();

            list.addFirst("second");
            list.addFirst("first");
            list.addLast("third");
            assertThat(list.get(0)).isEqualTo("first");
            assertThat(list.get(1)).isEqualTo("second");
            assertThat(list.get(2)).isEqualTo("third");
            assertThat(list.toList()).containsExactly("first", "second", "third").inOrder();

            assertThat(list.get(-1)).isNull();
            assertThat(list.get(-63)).isNull();
            assertThat(list.get(3)).isNull();
            assertThat(list.get(88)).isNull();
            assertThat(list.toList()).containsExactly("first", "second", "third").inOrder();
        }

        @Test
        public void testGetRecursive() {
            Deque61B<String> list = new ArrayDeque61B<>();

            list.addFirst("second");
            list.addFirst("first");
            list.addLast("third");
            assertThat(list.getRecursive(0)).isEqualTo("first");
            assertThat(list.getRecursive(1)).isEqualTo("second");
            assertThat(list.getRecursive(2)).isEqualTo("third");
            assertThat(list.toList()).containsExactly("first", "second", "third").inOrder();

            assertThat(list.getRecursive(-1)).isNull();
            assertThat(list.getRecursive(-63)).isNull();
            assertThat(list.getRecursive(3)).isNull();
            assertThat(list.getRecursive(88)).isNull();
            assertThat(list.toList()).containsExactly("first", "second", "third").inOrder();
        }
    }

    @Test
    public void testIsEmpty() {
        Deque61B<String> list = new ArrayDeque61B<>();
        assertThat(list.isEmpty()).isTrue();

        list.removeFirst();
        assertThat(list.isEmpty()).isTrue();
        list.addLast("two");
        assertThat(list.isEmpty()).isFalse();
        list.removeFirst();
        assertThat(list.isEmpty()).isTrue();
        list.addFirst("one");
        assertThat(list.isEmpty()).isFalse();
        list.addFirst("zero");
        assertThat(list.isEmpty()).isFalse();
        list.addLast("two");
        assertThat(list.isEmpty()).isFalse();
        list.removeFirst();
        assertThat(list.isEmpty()).isFalse();
        list.removeLast();
        assertThat(list.isEmpty()).isFalse();
        list.removeLast();
        assertThat(list.isEmpty()).isTrue();
        list.removeLast();
        assertThat(list.isEmpty()).isTrue();
    }

    @Test
    public void testSize() {
        Deque61B<String> list = new ArrayDeque61B<>();
        assertThat(list.size()).isEqualTo(0);

        list.removeFirst();
        assertThat(list.size()).isEqualTo(0);
        list.addLast("two");
        assertThat(list.size()).isEqualTo(1);
        list.removeFirst();
        assertThat(list.size()).isEqualTo(0);
        list.addFirst("one");
        assertThat(list.size()).isEqualTo(1);
        list.addFirst("zero");
        assertThat(list.size()).isEqualTo(2);
        list.addLast("two");
        assertThat(list.size()).isEqualTo(3);
        list.removeFirst();
        assertThat(list.size()).isEqualTo(2);
        list.removeLast();
        assertThat(list.size()).isEqualTo(1);
        list.removeLast();
        assertThat(list.size()).isEqualTo(0);
        list.removeLast();
        assertThat(list.size()).isEqualTo(0);
    }

    @Test
    public void testIterator() {
        Deque61B<Integer> list = new ArrayDeque61B<>();
        Iterator<Integer> it1 = list.iterator();
        assertThat(it1.hasNext()).isFalse();

        for (int i = 1; i < 32; i++) {
            list.addLast(i * i);
        }
        Iterator<Integer> it2 = list.iterator();
        assertThat(it2.hasNext()).isTrue();
        assertThat(it2.next()).isEqualTo(1);
        for (int i = 2; i < 32; i ++) {
            assertThat(it2.next()).isEqualTo(i * i);
        }
        assertThat(it2.hasNext()).isFalse();

        List<Integer> actual = new ArrayList<>();
        for (int k: list) {
            actual.add(k);
        }
        assertThat(actual).isEqualTo(list.toList());
    }

    @Test
    public void testEquals() {
        Deque61B<Integer> list = new ArrayDeque61B<>();
        Deque61B<Integer> object = new ArrayDeque61B<>();
        assertThat(list).isEqualTo(object);
        Deque61B<String> object2 = new ArrayDeque61B<>();
        assertThat(list).isEqualTo(object2);

        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        object.addLast(1);
        object.addLast(2);
        assertThat(list).isNotEqualTo(object);
        object.addLast(3);
        assertThat(list).isEqualTo(object);
        list.removeFirst();
        assertThat(list).isNotEqualTo(object);
        object.removeFirst();
        assertThat(list).isEqualTo(object);

        object2.addLast("one");
        assertThat(list).isNotEqualTo(object2);
        object2.addLast("two");
        assertThat(list).isNotEqualTo(object2);
    }

    @Test
    public void testToString() {
        Deque61B<String> listS = new ArrayDeque61B<>();
        assertThat(listS.toString()).isEqualTo("[]");

        listS.addLast("HelloWorld!");
        assertThat(listS.toString()).isEqualTo("[HelloWorld!]");
        listS.addLast("Good Morning.");
        assertThat(listS.toString()).isEqualTo("[HelloWorld!, Good Morning.]");
        listS.addFirst("Whatever");
        assertThat(listS.toString()).isEqualTo("[Whatever, HelloWorld!, Good Morning.]");
        listS.removeLast();
        assertThat(listS.toString()).isEqualTo("[Whatever, HelloWorld!]");
        listS.removeLast();
        assertThat(listS.toString()).isEqualTo("[Whatever]");
        listS.removeFirst();
        assertThat(listS.toString()).isEqualTo("[]");

        Deque61B<Integer> listI = new ArrayDeque61B<>();
        assertThat(listI.toString()).isEqualTo("[]");

        listI.addFirst(1);
        assertThat(listI.toString()).isEqualTo("[1]");
        listI.addLast(2);
        assertThat(listI.toString()).isEqualTo("[1, 2]");
    }

}
