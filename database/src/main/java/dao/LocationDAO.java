package dao;

import entities.Location;

import java.util.List;

public interface LocationDAO {
    void save(Location location);
    Location get(long id);
    List<Location> getAll();
    void update(Location location);
    void delete(long id);
}
