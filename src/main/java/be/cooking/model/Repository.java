package be.cooking.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Repository<T> {

    private final List<T> list = new ArrayList<>();

    public void save(T object) {
        list.add(object);
    }

    public List<T> getList() {
        return Collections.unmodifiableList(list);
    }
}
