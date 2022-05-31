package com.example.carvajaltechnicaltest.service;

import com.example.carvajaltechnicaltest.entity.Role;
import com.example.carvajaltechnicaltest.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow();
    }


    @Transactional
    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }
}
