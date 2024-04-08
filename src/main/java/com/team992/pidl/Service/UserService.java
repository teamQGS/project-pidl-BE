package com.team992.pidl.Service;

import com.team992.pidl.DTOS.UserDTO;
import com.team992.pidl.Model.Entities.UserEntity;
import com.team992.pidl.Model.Entities.ContactEntity;
import com.team992.pidl.Model.Repositories.UserRepository;
import com.team992.pidl.Model.Repositories.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;



import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ContactRepository contactRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, ContactRepository contactRepository) {
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
    }

    public String createUser(UserDTO userDTO){
        UserEntity entity = new UserEntity();
        entity.setUsername(userDTO.getUsername());
        entity.setPassword(userDTO.getPassword());
        entity.setContact(createOrGetContact(userDTO.getEmail()));
        userRepository.save(entity);
        String id = entity.getId();
        logger.info("User was created with ID: {}", id);
        return id;
    }

    public UserDTO deleteUser(String id){
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No user found with id: " + id));
        UserDTO dto = toUserDTO(userEntity);
        contactRepository.delete(userEntity.getContact());
        userRepository.deleteById(id);
        logger.info("User with id: {} was deleted.", id);
        return dto;
    }

    public UserDTO getUser(String id){
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No user found with id: " + id));
        return toUserDTO(userEntity);
    }

    public UserDTO updateUser(String id, UserDTO userDTO){
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No user found with id: " + id));
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(userDTO.getPassword());
        ContactEntity contactEntity = userEntity.getContact();
        if (!contactEntity.getEmail().equals(userDTO.getEmail())) {
            contactEntity.setEmail(userDTO.getEmail());
            contactRepository.save(contactEntity);
        }
        userRepository.save(userEntity);
        return toUserDTO(userEntity);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::toUserDTO).collect(Collectors.toList());
    }

    private UserDTO toUserDTO(UserEntity userEntity) {
        UserDTO dto = new UserDTO();
        dto.setId(userEntity.getId());
        dto.setUsername(userEntity.getUsername());
        dto.setEmail(userEntity.getContact().getEmail());
        dto.setPassword(userEntity.getPassword());
        return dto;
    }

    private ContactEntity createOrGetContact(String email) {
        return contactRepository.findByEmail(email).orElseGet(() -> {
            ContactEntity newContact = new ContactEntity();
            newContact.setEmail(email);
            return contactRepository.save(newContact);
        });
    }
}