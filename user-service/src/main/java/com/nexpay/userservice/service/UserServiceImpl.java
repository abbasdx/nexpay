package com.nexpay.userservice.service;

import com.nexpay.userservice.client.WalletClient;
import com.nexpay.userservice.dto.WalletRequest;
import com.nexpay.userservice.entity.User;
import com.nexpay.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    private final WalletClient walletClient;

    public UserServiceImpl(UserRepository userRepository, WalletClient walletClient) {
        this.userRepository=userRepository;
        this.walletClient = walletClient;
    }

    @Override
    public User createUser(User user) {
        User savedUser = userRepository.save(user);

        try {
            WalletRequest request = new WalletRequest();
            request.setUserId(user.getId());
            request.setCurrency("INR");
            walletClient.createWallet(request);
        }catch (Exception e){
            userRepository.deleteById(user.getId()); // rollback
            throw new RuntimeException("Wallet could not be created, user rolled back", e);

        }

        return savedUser;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
