package dao;

import entities.Container;

import java.util.List;

public interface ContainerDAO {
    void save(Container container);
    Container get(long id);
    List<Container> getAll();
    void update(Container container);
    void delete(long id);
}
