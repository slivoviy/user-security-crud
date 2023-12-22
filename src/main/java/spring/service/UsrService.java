package spring.service;


import spring.exception.UsernameAlreadyExistException;
import spring.model.Role;
import spring.model.Usr;

import java.util.List;

public interface UsrService {
    void addUser(Usr user) throws UsernameAlreadyExistException;

    List<Usr> getUsers();

    Usr getUserById(long id);

    void removeUser(long id);

    Role getRoleByName(String name);
    List<Role> getAllRoles();
}
