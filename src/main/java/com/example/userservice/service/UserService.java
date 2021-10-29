package com.example.userservice.service;

import com.example.userservice.VO.Order;
import com.example.userservice.VO.ReponseTemplateVO;
import com.example.userservice.authen.UserPrincipal;
import com.example.userservice.entity.User;
import com.example.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;

    public User saveUser(User user) {
        return userRepository.save(user);
    }
    public ReponseTemplateVO getUserWithOrder(Long studentId) {
        ReponseTemplateVO vo = new ReponseTemplateVO();
        User user = userRepository.findById(studentId).get();
        vo.setUser(user);
        Order order =
                restTemplate.getForObject("http://localhost:9001/order/"
                                + user.getOrderId(),
                        Order.class);

        vo.setOrder(order);

        return vo;
    }

    public User createUser(User user) {
        return userRepository.saveAndFlush(user);
    }


    public UserPrincipal findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        UserPrincipal userPrincipal = new UserPrincipal();

        if (null != user) {
            Set<String> authorities = new HashSet<>();
            if (null != user.getRoles())

                user.getRoles().forEach(r -> {
                    authorities.add(r.getRoleKey());
                    r.getPermissions().forEach(
                            p -> authorities.add(p.getPermissionKey()));
                });

            userPrincipal.setUserId(user.getId());
            userPrincipal.setUsername(user.getName());
            userPrincipal.setPassword(user.getName());
            userPrincipal.setAuthorities(authorities);

        }

        return userPrincipal;

    }
}
