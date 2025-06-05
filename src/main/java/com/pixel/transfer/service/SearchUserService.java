package com.pixel.transfer.service;

import com.pixel.transfer.dto.UserDto;
import com.pixel.transfer.entity.EmailData;
import com.pixel.transfer.entity.PhoneData;
import com.pixel.transfer.entity.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SearchUserService {

    private final UserService userService;

    @Cacheable(value = "usersSearch", key = "{#name, #email, #phone, #dateOfBirth, #pageable}")
    public Page<UserDto> searchUser(String name, String email, String phone, String dateOfBirth, Pageable pageable) {
        LocalDate dob = null;
        if (dateOfBirth != null) {
            dob = LocalDate.parse(dateOfBirth);
        }
        Specification<User> spec = buildSpec(name, email, phone, dob);

        return userService.searchUsers(spec, pageable);
    }

    private Specification<User> buildSpec(String name, String email, String phone, LocalDate dateOfBirth) {
        Specification<User> spec = Specification.where(null);

        if (dateOfBirth != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThan(root.get("dateOfBirth"), dateOfBirth));
        }
        if (name != null && !name.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.like(root.get("name"), name + "%"));
        }
        if (email != null && !email.isBlank()) {
            spec = spec.and((root, query, cb) -> {
                Join<User, EmailData> join = root.join("emails", JoinType.INNER);
                return cb.equal(join.get("email"), email);
            });
        }
        if (phone != null && !phone.isBlank()) {
            spec = spec.and((root, query, cb) -> {
                Join<User, PhoneData> join = root.join("phones", JoinType.INNER);
                return cb.equal(join.get("phone"), phone);
            });
        }

        return spec;
    }
}
