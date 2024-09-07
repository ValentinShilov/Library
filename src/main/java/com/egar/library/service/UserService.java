package com.egar.library.service;

import com.egar.library.entity.Comment;
import com.egar.library.entity.User;
import com.egar.library.model.UserDTO;
import com.egar.library.repos.CommentRepository;
import com.egar.library.repos.UserRepository;

import java.util.List;

import com.egar.library.util.NotFoundException;
import com.egar.library.util.ReferencedWarning;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class UserService implements CRUDService<UserDTO> {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<UserDTO> findAll() {
        log.info("Fetching all users ");
        List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    @Override
    public UserDTO getById(Long id) {
        log.info("Fetching user with id: {}", id);
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow();
    }

    @Override
    public void create(UserDTO userDTO) {
        log.info("Creating new user: {}", userDTO);
        User user = new User();
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    @Override
    public void update(Long id, UserDTO userDTO) {
        log.info("Updating user with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow();
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        log.info("Delete user with id {}", id);
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(User user, UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        return userDTO;
    }

    private User mapToEntity(UserDTO userDTO, User user) {
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return user;
    }
    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Comment userComment = (Comment) commentRepository;
        if (userComment != null) {
            referencedWarning.setKey("user.comment.user.referenced");
            referencedWarning.addParam(userComment.getId());
            return referencedWarning;
        }
        return null;
    }

}
