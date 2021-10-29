package com.example.userservice.service;

import com.example.userservice.VO.Order;
import com.example.userservice.VO.ReponseTemplateVO;
import com.example.userservice.entity.User;
import com.example.userservice.repositories.UserRepository;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Retry(name ="basic")
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
}
