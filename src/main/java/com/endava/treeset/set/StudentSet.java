package com.endava.treeset.set;

import com.endava.treeset.model.Student;
import java.util.*;

public class StudentSet implements Set<Student> {

    public static final int MAX_ARRAY_LENGTH = Integer.MAX_VALUE - 8;
    private transient NavigableMap<Student, Object> map;

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public Iterator<Student> iterator() {
        return map.navigableKeySet().iterator();
    }

    @Override
    public Object[] toArray() {
        Object[] r = new Object[size()];
        Iterator<Student> it = iterator();
        for (int i = 0; i < r.length; i++) {
            if (!it.hasNext()) // fewer elements than expected
                return Arrays.copyOf(r, i);
            r[i] = it.next();
        }
        return it.hasNext() ? finishToArray(r, it) : r;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    //        @Override
    public Object[] toArray(Student[] ts) {
        // Estimate size of array; be prepared to see more or fewer elements
        int size = size();
        Student[] r = ts.length >= size ? ts :
                (Student[])java.lang.reflect.Array
                        .newInstance(ts.getClass().getComponentType(), size);
        Iterator<Student> it = iterator();

        for (int i = 0; i < r.length; i++) {
            if (! it.hasNext()) { // fewer elements than expected
                if (ts == r) {
                    r[i] = null; // null-terminate
                } else if (ts.length < i) {
                    return Arrays.copyOf(r, i);
                } else {
                    System.arraycopy(r, 0, ts, 0, i);
                    if (ts.length > i) {
                        ts[i] = null;
                    }
                }
                return ts;
            }
            r[i] = (Student) it.next();
        }
        // more elements than expected
        return it.hasNext() ? finishToArray(r, it) : r;
    }

    @Override
    public boolean add(Student student) {
        Object add = map.put(student, student.age());
        return true;
    }

    public Comparator<? super Student> comparator() {
        return map.comparator();
    }

    @Override
    public boolean remove(Object o) {
        map.remove(o);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends Student> collection) {
        collection.forEach(this::add);
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        for (Object o : collection) {
            retainAll((Collection<Student>) o);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        collection.forEach(item -> map.remove(item));
        return true;
    }

    @Override
    public void clear() {
        map.clear();
    }

    private static <T> T[] finishToArray(T[] r, Iterator<?> it) {
        int len = r.length;
        int i = len;
        while (it.hasNext()) {
            if (i == len) {
                len = newLength(len,
                        1,             /* minimum growth */
                        (len >> 1) + 1 /* preferred growth */);
                r = Arrays.copyOf(r, len);
            }
            r[i++] = (T) it.next();
        }
        // trim if overallocated
        return (i == len) ? r : Arrays.copyOf(r, i);
    }

    public static int newLength(int oldLength, int minGrowth, int prefGrowth) {
        // assert oldLength >= 0
        // assert minGrowth > 0

        int newLength = Math.max(minGrowth, prefGrowth) + oldLength;
        if (newLength - MAX_ARRAY_LENGTH <= 0) {
            return newLength;
        }
        return hugeLength(oldLength, minGrowth);
    }

    private static int hugeLength(int oldLength, int minGrowth) {
        int minLength = oldLength + minGrowth;
        if (minLength < 0) { // overflow
            throw new OutOfMemoryError("Required array length too large");
        }
        if (minLength <= MAX_ARRAY_LENGTH) {
            return MAX_ARRAY_LENGTH;
        }
        return Integer.MAX_VALUE;
    }
}
