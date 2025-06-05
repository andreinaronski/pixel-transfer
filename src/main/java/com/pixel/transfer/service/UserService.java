package com.pixel.transfer.service;

import com.pixel.transfer.dto.UserDto;
import com.pixel.transfer.entity.User;
import com.pixel.transfer.exception.UserNotFoundException;
import com.pixel.transfer.mapper.UserMapper;
import com.pixel.transfer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Cacheable(cacheNames = "users", key = "#id")
    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public Page<UserDto> searchUsers(Specification<User> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable).map(userMapper::toDto);
    }
}
