package com.evgenia.udalova.test.security;

import com.evgenia.udalova.test.entity.ClientEntity;
import com.evgenia.udalova.test.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ClientRepository clientRepository;

    @Autowired
    public UserDetailsServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String clientName) throws UsernameNotFoundException {
        ClientEntity user = clientRepository.findByClientName(clientName);
        return SecurityUser.fromUser(user);
    }
}
