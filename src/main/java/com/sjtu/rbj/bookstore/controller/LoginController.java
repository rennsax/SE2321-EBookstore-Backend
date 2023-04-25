package com.sjtu.rbj.bookstore.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sjtu.rbj.bookstore.constant.Constant;
import com.sjtu.rbj.bookstore.service.UserService;

import lombok.extern.slf4j.Slf4j;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Wrong account or password!")
class LoginErrorException extends RuntimeException {
}

/**
 * @author Bojun Ren
 * @date 2023/04/08
 */
@Slf4j
@RestController
@CrossOrigin(Constant.ALLOW_ORIGIN)
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public void getUser(@RequestBody Map<String, String> params, HttpServletRequest request) {
        String account = params.get(Constant.ACCOUNT);
        String passwd = params.get(Constant.PASSWORD);
        log.info(account);
        log.info(passwd);
        if (account == null || passwd == null || !userService.enableLogin(account, passwd)) {
            throw new LoginErrorException();
        }
    }

}
