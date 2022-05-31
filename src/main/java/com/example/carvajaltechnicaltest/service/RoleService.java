package com.example.carvajaltechnicaltest.service;

import com.example.carvajaltechnicaltest.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface RoleService {
    Role findById(Long id);

   Role save(Role role);

    void deleteById(Long id);
}
