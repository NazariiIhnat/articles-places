package dao;

import entities.Code;

public interface CodeDAO {
    void save(Code code);
    Code get(String code);
    void delete(String code);
}
