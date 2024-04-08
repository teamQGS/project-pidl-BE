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
        logger.info("Creating user with username: {}", userDTO.getUsername());
        UserEntity entity = new UserEntity();
        entity.setUsername(userDTO.getUsername());
        entity.setPassword(userDTO.getPassword());
        entity.setContact(createOrGetContact(userDTO.getEmail()));
        userRepository.save(entity);
        String id = entity.getId();
        logger.info("User was created with ID: {}", id);
        return id;
    }

    public UserDTO deleteUserByEmail(String email){
        logger.info("Deleting user with Email: {}", email);
        UserEntity userEntity = userRepository.findByContact_Email(email).orElseThrow(() -> new NoSuchElementException("No user found with email: " + email));
        UserDTO dto = toUserDTO(userEntity);
        contactRepository.delete(userEntity.getContact());
        logger.info("Deleted contact with email: {}", userEntity.getContact().getEmail());
        userRepository.delete(userEntity);
        logger.info("User with Email: {} was deleted.", email);
        return dto;
    }

    // Old method to delete user by id, kept for reference
    // public UserDTO deleteUser(String id){
    //     logger.info("Deleting user with ID: {}", id);
    //     UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No user found with id: " + id));
    //     UserDTO dto = toUserDTO(userEntity);
    //     contactRepository.delete(userEntity.getContact());
    //     logger.info("Deleted contact with email: {}", userEntity.getContact().getEmail());
    //     userRepository.deleteById(id);
    //     logger.info("User with id: {} was deleted.", id);
    //     return dto;
    // }

    public UserDTO getUserByEmail(String email){
        logger.info("Retrieving user with Email: {}", email);
        UserEntity userEntity = userRepository.findByContact_Email(email).orElseThrow(() -> new NoSuchElementException("No user found with email: " + email));
        return toUserDTO(userEntity);
    }

    // Old method to get user by id, kept for reference
    // public UserDTO getUser(String id){
    //     logger.info("Retrieving user with ID: {}", id);
    //     UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No user found with id: " + id));
    //     return toUserDTO(userEntity);
    // }

    public UserDTO updateUserByEmail(String email, UserDTO userDTO){
        logger.info("Updating user with Email: {}", email);
        UserEntity userEntity = userRepository.findByContact_Email(email).orElseThrow(() -> new NoSuchElementException("No user found with email: " + email));
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(userDTO.getPassword());
        ContactEntity contactEntity = userEntity.getContact();
        if (!contactEntity.getEmail().equals(userDTO.getEmail())) {
            logger.info("Updating contact email from {} to {}", contactEntity.getEmail(), userDTO.getEmail());
            contactEntity.setEmail(userDTO.getEmail());
            contactRepository.save(contactEntity);
        }
        userRepository.save(userEntity);
        logger.info("User with Email: {} was updated.", email);
        return toUserDTO(userEntity);
    }

    // Old method to update user by id, kept for reference
    // public UserDTO updateUser(String id, UserDTO userDTO){
    //     logger.info("Updating user with ID: {}", id);
    //     UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No user found with id: " + id));
    //     userEntity.setUsername(userDTO.getUsername());
    //     userEntity.setPassword(userDTO.getPassword());
    //     ContactEntity contactEntity = userEntity.getContact();
    //     if (!contactEntity.getEmail().equals(userDTO.getEmail())) {
    //         logger.info("Updating contact email from {} to {}", contactEntity.getEmail(), userDTO.getEmail());
    //         contactEntity.setEmail(userDTO.getEmail());
    //         contactRepository.save(contactEntity);
    //     }
    //     userRepository.save(userEntity);
    //     logger.info("User with ID: {} was updated.", id);
    //     return toUserDTO(userEntity);
    // }

    public List<UserDTO> getAllUsers() {
        logger.info("Retrieving all users");
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
        logger.info("Creating or retrieving contact with email: {}", email);
        return contactRepository.findByEmail(email).orElseGet(() -> {
            ContactEntity newContact = new ContactEntity();
            newContact.setEmail(email);
            logger.info("Created new contact with email: {}", email);
            return contactRepository.save(newContact);
        });
    }
}