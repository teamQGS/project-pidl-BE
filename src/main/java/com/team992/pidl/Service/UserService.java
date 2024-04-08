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
        entity.setContact(createOrGetContact(userDTO.getEmail())); // Create or get the contact entity associated with the user's email
        userRepository.save(entity); // Save the user entity to the repository
        logger.info("User was created with ID: {}", entity.getId()); // Log the creation of the user
        return entity.getId(); // Return the ID of the created user
    }

    public UserDTO deleteUser(String id){
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No user found with id: " + id)); // Find the user entity by ID or throw an exception if not found
    
        UserDTO dto = new UserDTO();
        dto.setId(userEntity.getId());
        dto.setUsername(userEntity.getUsername());
        dto.setEmail(userEntity.getContact().getEmail());
        dto.setPassword(userEntity.getPassword());
    
        userRepository.deleteById(id); // Delete the user entity from the repository
        logger.info("User with id: {} was deleted.", id); // Log the deletion of the user
    
        return dto; // Return the DTO of the deleted user
    }

    public UserDTO getUser(String id){
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No user found with id: " + id));
        return toUserDTO(userEntity);
    }

    public UserDTO updateUser(String id, UserDTO userDTO){
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No user found with id: " + id));
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setContact(createOrGetContact(userDTO.getEmail()));
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
        return contactRepository.findByEmail(email).orElseGet(() -> { // Find the contact entity by email or create a new one if not found
            ContactEntity newContact = new ContactEntity();
            newContact.setEmail(email);
            return contactRepository.save(newContact); // Save the new contact entity to the repository
        });
    }
}