import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

    @Nested
    class AddOperations {
        @Test
        public void testAddFirst() {
            Deque61B<String> lld = new LinkedListDeque61B<>();

            lld.addFirst("back");
            assertThat(lld.toList()).containsExactly("back");
            lld.addFirst("middle");
            assertThat(lld.toList()).containsExactly("middle", "back").inOrder();
            lld.addFirst("front");
            assertThat(lld.toList()).containsExactly("front", "middle", "back").inOrder();
        }
        @Test
        public void testAddLast() {
            Deque61B<String> lld = new LinkedListDeque61B<>();

            lld.addLast("front");
            assertThat(lld.toList()).containsExactly("front");
            lld.addLast("middle");
            assertThat(lld.toList()).containsExactly("front", "middle").inOrder();
            lld.addLast("back");
            assertThat(lld.toList()).containsExactly("front", "middle", "back").inOrder();
        }
    }

    @Test
    public void testToList() {
        Deque61B<String> lld = new LinkedListDeque61B<>();
        assertThat(lld.toList()).isEmpty();

        lld.addLast("latter");
        assertThat(lld.toList()).containsExactly("latter");
        lld.addFirst("former");
        assertThat(lld.toList()).containsExactly("former", "latter").inOrder();

        // test if it creates a copy instead of a pointer to itself
        // by clearing the returned list and check the state of original deque.
        List<String> result = lld.toList();
        result.clear();
        result.add("whatever");
        assertThat(lld.toList()).containsExactly("former", "latter").inOrder();
    }

    @Test
    public void testAddAfterRemove() {
        Deque61B<String> lld = new LinkedListDeque61B<>();

        lld.addFirst("former");
        lld.addLast("latter");
        lld.removeFirst();
        lld.removeLast();
        assertThat(lld.toList()).isEmpty();
        lld.addFirst("test");
        assertThat(lld.toList()).containsExactly("test");

        lld.removeFirst();
        assertThat(lld.toList()).isEmpty();
        lld.addLast("test");
        assertThat(lld.toList()).containsExactly("test");
    }

    @Nested
    class RemoveOperations {
        @Test
        public void testRemoveFirst() {
            Deque61B<String> lld = new LinkedListDeque61B<>();

            lld.addFirst("front");
            lld.addLast("middle");
            lld.addLast("back");
            assertThat(lld.toList()).containsExactly("front", "middle", "back").inOrder();

            assertThat(lld.removeFirst()).isEqualTo("front");
            assertThat(lld.toList()).containsExactly("middle", "back").inOrder();
            assertThat(lld.removeFirst()).isEqualTo("middle");
            assertThat(lld.toList()).containsExactly("back").inOrder();
            assertThat(lld.removeFirst()).isEqualTo("back");
            assertThat(lld.toList()).isEmpty();
            assertThat(lld.removeFirst()).isNull();
            assertThat(lld.toList()).isEmpty();
            assertThat(lld.removeFirst()).isNull();
            assertThat(lld.toList()).isEmpty();
        }
        @Test
        public void testRemoveLast() {
            Deque61B<String> lld = new LinkedListDeque61B<>();

            lld.addFirst("front");
            lld.addLast("middle");
            lld.addLast("back");
            assertThat(lld.toList()).containsExactly("front", "middle", "back").inOrder();

            assertThat(lld.removeLast()).isEqualTo("back");
            assertThat(lld.toList()).containsExactly("front", "middle").inOrder();
            assertThat(lld.removeLast()).isEqualTo("middle");
            assertThat(lld.toList()).containsExactly("front").inOrder();
            assertThat(lld.removeLast()).isEqualTo("front");
            assertThat(lld.toList()).isEmpty();
            assertThat(lld.removeLast()).isNull();
            assertThat(lld.toList()).isEmpty();
            assertThat(lld.removeLast()).isNull();
            assertThat(lld.toList()).isEmpty();
        }
    }

    @Nested
    class GetOperations {
        @Test
        public void testGetFirst() {
            Deque61B<String> lld = new LinkedListDeque61B<>();
            assertThat(lld.getFirst()).isNull();

            lld.addLast("latter");
            assertThat(lld.getFirst()).isEqualTo("latter");
            lld.addFirst("former");
            assertThat(lld.getFirst()).isEqualTo("former");
            lld.removeFirst();
            assertThat(lld.getFirst()).isEqualTo("latter");
        }
        @Test
        public void testGetLast() {
            Deque61B<String> lld = new LinkedListDeque61B<>();
            assertThat(lld.getLast()).isNull();

            lld.addFirst("former");
            assertThat(lld.getLast()).isEqualTo("former");
            lld.addLast("latter");
            assertThat(lld.getLast()).isEqualTo("latter");
            lld.removeLast();
            assertThat(lld.getLast()).isEqualTo("former");
        }
        @Test
        public void testGet() {
            Deque61B<String> lld = new LinkedListDeque61B<>();
            assertThat(lld.get(0)).isNull();
            assertThat(lld.get(-1)).isNull();
            assertThat(lld.get(1)).isNull();

            lld.addLast("front");
            lld.addLast("middle");
            lld.addLast("back");
            assertThat(lld.get(0)).isEqualTo("front");
            assertThat(lld.get(1)).isEqualTo("middle");
            assertThat(lld.get(2)).isEqualTo("back");
            assertThat(lld.toList()).containsExactly("front", "middle", "back").inOrder();
            assertThat(lld.get(-1)).isNull();
            assertThat(lld.get(-10)).isNull();
            assertThat(lld.get(3)).isNull();
            assertThat(lld.get(301)).isNull();
            assertThat(lld.toList()).containsExactly("front", "middle", "back").inOrder();
        }
        @Test
        public void testGetRecursive() {
            Deque61B<String> lld = new LinkedListDeque61B<>();
            assertThat(lld.getRecursive(0)).isNull();
            assertThat(lld.getRecursive(-1)).isNull();
            assertThat(lld.getRecursive(1)).isNull();

            lld.addLast("front");
            lld.addLast("middle");
            lld.addLast("back");
            assertThat(lld.getRecursive(0)).isEqualTo("front");
            assertThat(lld.getRecursive(1)).isEqualTo("middle");
            assertThat(lld.getRecursive(2)).isEqualTo("back");
            assertThat(lld.toList()).containsExactly("front", "middle", "back").inOrder();
            assertThat(lld.getRecursive(-1)).isNull();
            assertThat(lld.getRecursive(-10)).isNull();
            assertThat(lld.getRecursive(3)).isNull();
            assertThat(lld.getRecursive(301)).isNull();
            assertThat(lld.toList()).containsExactly("front", "middle", "back").inOrder();
        }
    }

    @Test
    public void testSize() {
        Deque61B<String> lld = new LinkedListDeque61B<>();
        assertThat(lld.size()).isEqualTo(0);

        lld.addFirst("first");
        lld.addLast("second");
        lld.addLast("third");
        assertThat(lld.size()).isEqualTo(3);

        lld.removeFirst();
        assertThat(lld.size()).isEqualTo(2);
        lld.removeLast();
        assertThat(lld.size()).isEqualTo(1);
        lld.removeFirst();
        assertThat(lld.size()).isEqualTo(0);
        lld.removeLast();
        assertThat(lld.size()).isEqualTo(0);
    }

    @Test
    public void testIsEmpty() {
        Deque61B<String> lld = new LinkedListDeque61B<>();
        assertThat(lld.isEmpty()).isTrue();

        lld.addFirst("one");
        assertThat(lld.isEmpty()).isFalse();
        lld.addLast("two");
        assertThat(lld.isEmpty()).isFalse();
        lld.removeFirst();
        assertThat(lld.isEmpty()).isFalse();
        lld.removeLast();
        assertThat(lld.isEmpty()).isTrue();
        lld.removeFirst();
        assertThat(lld.isEmpty()).isTrue();
    }

}