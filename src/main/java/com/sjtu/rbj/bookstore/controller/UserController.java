package com.sjtu.rbj.bookstore.controller;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.sjtu.rbj.bookstore.annotation.Administer;
import com.sjtu.rbj.bookstore.constant.Constants;
import com.sjtu.rbj.bookstore.constant.UserType;
import com.sjtu.rbj.bookstore.dto.UserForAdminDTO;
import com.sjtu.rbj.bookstore.dto.UserInfoDTO;
import com.sjtu.rbj.bookstore.entity.User;
import com.sjtu.rbj.bookstore.service.UserService;

import lombok.extern.slf4j.Slf4j;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such user!")
class NoSuchUserException extends NoSuchElementException {
}

/**
 * @author Bojun Ren
 * @data 2023/04/23
 */
@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin(Constants.ALLOW_ORIGIN)
public class UserController {

    @Administer
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        JSONArray userArray = new JSONArray();
        for (User user : userList) {
            UserForAdminDTO userInfo = modelMapper.map(user, UserForAdminDTO.class);
            userInfo.setAccount(user.getUserAccount().getAccount());
            userInfo.setPasswd(user.getUserAccount().getPasswd());
            userArray.add(userInfo);
        }
        return ResponseEntity.ok().body(userArray);
    }

    @Administer
    @PatchMapping("/ban/{id}")
    public ResponseEntity<?> banUser(@PathVariable("id") Integer id) {
        try {
            if (!userService.changeState(id, UserType.FORBIDDEN)) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
        } catch (UnsupportedOperationException ex) {
            log.info(ex.toString());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (NoSuchElementException ex) {
            log.info(ex.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.noContent().build();
    }

    @Administer
    @PatchMapping("/unlock/{id}")
    public ResponseEntity<?> unlockUser(@PathVariable("id") Integer id) {
        try {
            if (!userService.changeState(id, UserType.NORMAL)) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
        } catch (UnsupportedOperationException ex) {
            log.info(ex.toString());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (NoSuchElementException ex) {
            log.info(ex.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/passwd/{id}")
    public ResponseEntity<?> changePasswd(@PathVariable Integer id, @RequestBody Map<String, String> params) {
        String newPasswd = params.get("passwd");
        if (newPasswd == null) {
            return ResponseEntity.badRequest().build();
        }
        if (!userService.changePasswdById(id, newPasswd)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Response for uri like "/user/by?account=..".
     */
    @GetMapping("/by")
    public String getUserInfo(@RequestParam(value = "account", defaultValue = "") String account) {
        try {
            UserInfoDTO userInfo = userService.getUserInfoByAccount(account);
            return JSON.toJSONString(userInfo);
        } catch (Exception e) {
            throw new NoSuchUserException();
        }
    }

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;
}
