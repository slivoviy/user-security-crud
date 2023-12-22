package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.exception.UsernameAlreadyExistException;
import spring.model.Role;
import spring.repository.RoleRepository;
import spring.repository.UsrRepository;
import spring.model.Usr;

import java.util.List;

@Service
public class UsrServiceImp implements UsrService, UserDetailsService {

    @Autowired
    private UsrRepository usrRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Lazy
    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    @Override
    public void addUser(Usr user) throws UsernameAlreadyExistException {
        if (usernameIsInvalid(user)) {
            throw new UsernameAlreadyExistException();
        }
        encodePassword(user);
        usrRepository.saveAndFlush(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Usr> getUsers() {
        return usrRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Usr getUserById(long id) {
        return usrRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void removeUser(long id) {
        usrRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public boolean usernameIsInvalid(Usr user) {
        if (user.getId() == null) {
            return usrRepository.exists(Example.of(new Usr(null, null, user.getEmail(), null, null)));
        }

        Usr oldUser = getUserById(user.getId());
        if (user.getUsername().equals(oldUser.getUsername())) {
            return false;
        } else {
            return usrRepository.exists(Example.of(new Usr(null, null, user.getEmail(), null, null)));
        }
    }

    private void encodePassword(Usr user) {
        user.setPassword(encoder.encode(user.getPassword()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usr user = usrRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
}
