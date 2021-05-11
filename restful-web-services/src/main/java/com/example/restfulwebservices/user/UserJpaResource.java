package com.example.restfulwebservices.user;

import com.example.restfulwebservices.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserJpaResource {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/jpa/users")
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @GetMapping("/jpa/users/{id1}")
    public User getUserById(@PathVariable int id1) {
        Optional<User> user = repository.findById(id1);
        if(!user.isPresent())
            throw new UserNotFoundException("id-"+id1);
        return user.get();
    }

    //CREATED
    @PostMapping("/jpa/users")
    public ResponseEntity<Object> saveUser(@Valid @RequestBody User user) {
        User newUser = repository.save(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
            buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/jpa/users/{id}")
    public void delete(@PathVariable int id) {
        repository.deleteById(id);
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrieveAllPosts(@PathVariable int id){
        Optional<User> user = repository.findById(id);
        if(!user.isPresent())
            throw new UserNotFoundException("id-"+id);
        return user.get().getPosts();
    }

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Object> saveUser(@PathVariable int id, @RequestBody Post post) {
        Optional<User> userOpt = repository.findById(id);
        if(!userOpt.isPresent())
            throw new UserNotFoundException("id-"+id);
        User user = userOpt.get();
        post.setUser(user);
        postRepository.save(post);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
            buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
