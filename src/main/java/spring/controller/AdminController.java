package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.exception.NoSuchUserException;
import spring.exception.UsernameAlreadyExistException;
import spring.model.Usr;
import spring.service.UsrService;

import java.util.List;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    @Autowired
    private UsrService service;


    @GetMapping("/users")
    public List<Usr> getUsers() {
        return service.getUsers();
    }

    @GetMapping("/users/{id}")
    public Usr getUser(@PathVariable int id) {
        Usr user = service.getUserById(id);
        if (user == null) {
            throw new NoSuchUserException("There is no user with ID = " + id + " in Database");
        }

        return user;
    }

    @PostMapping("/users")
    public Usr addUser(@RequestBody Usr user) {
        try {
            service.addUser(user);
        } catch (UsernameAlreadyExistException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    @PutMapping("/users")
    public Usr updateUser(@RequestBody Usr user) {
        try {
            service.addUser(user);
        } catch (UsernameAlreadyExistException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    @DeleteMapping("/users/{id}")
    public String deleteUsr(@PathVariable int id) {
        Usr user = service.getUserById(id);

        if (user == null) {
            throw new NoSuchUserException("There is no user with ID = " + id + " in Database");
        }

        service.removeUser(id);

        return "User with ID = " + id + " was deleted.";
    }

}
