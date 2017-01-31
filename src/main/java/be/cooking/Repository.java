package be.cooking;

import java.util.ArrayList;
import java.util.List;

public class Repository<T> {

    private List<T> list = new ArrayList<>();


    public void save(T object) {
        list.add(object);
    }

    public List<T> getList() {
        return list;
    }
}
