package com.example.restfulwebservices.user;

import com.example.restfulwebservices.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserDaoService service;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id1}")
    public EntityModel<User> getUserById(@PathVariable int id1) {
        User user = service.findOne(id1);
        if(user == null)
            throw new UserNotFoundException("id-"+id1);

//        // HATEOAS
//        EntityModel<User> resource = new EntityModel<User>(user);
//        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllUsers());
//        resource.add(linkTo.withRel("all-users"));
//        return resource;
        return null;
    }

    //CREATED
    @PostMapping("/users")
    public ResponseEntity<Object> saveUser(@Valid @RequestBody User user) {
        User newUser = service.save(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
            buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        if(service.deleteById(id))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            throw new UserNotFoundException("id-"+id);
    }
}
