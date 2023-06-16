package com.sjtu.rbj.bookstore.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sjtu.rbj.bookstore.dao.UserDao;
import com.sjtu.rbj.bookstore.dto.UserInfoDTO;
import com.sjtu.rbj.bookstore.entity.Order;
import com.sjtu.rbj.bookstore.entity.OrderState;
import com.sjtu.rbj.bookstore.entity.User;
import com.sjtu.rbj.bookstore.entity.UserType;
import com.sjtu.rbj.bookstore.entity.User.UserAccount;
import com.sjtu.rbj.bookstore.service.UserService;

/**
 * @author Bojun Ren
 * @date 2023/04/18
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public Boolean enableLogin(String account, String passwd) {
        Optional<User> maybeUser = userDao.findByAccountAndPasswd(account, passwd);
        return maybeUser.isPresent();
    }

    @Override
    public UserInfoDTO getUserInfoByAccount(String account) {
        Optional<User> maybeUser = userDao.findByAccount(account);
        User user = maybeUser.orElseThrow(() -> new NoSuchElementException("No such account!"));
        List<Order> orderList = user.getOrderList();

        Integer orderId = null;

        for (Order order : orderList) {
            if (OrderState.PENDING == order.getState()) {
                orderId = order.getId();
                break;
            }
        }
        if (orderId == null) {
            Order orderPending = new Order();
            orderPending.setState(OrderState.PENDING);
            user.addOrder(orderPending);
            userDao.flush();
            orderId = orderPending.getId();
        }
        UserInfoDTO userInfo = modelMapper.map(user, UserInfoDTO.class);
        userInfo.setOrderId(orderId);
        return userInfo;
    }

    @Override
    public Optional<UserType> login(String account, String passwd) {
        Optional<User> maybeUser = userDao.findByAccountAndPasswd(account, passwd);
        if (!maybeUser.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(maybeUser.get().getUserType());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean changeState(Integer id, UserType state) {
        Optional<User> maybeUser = userDao.findById(id);
        User user = maybeUser.orElseThrow(() -> new NoSuchElementException("No such user!"));
        if (user.getUserType() == UserType.SUPER) {
            throw new UnsupportedOperationException("Can't change the state of a super user!");
        }
        if (user.getUserType() == state) {
            return false;
        }
        user.setUserType(state);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean changePasswdByAccount(String account, String newPasswd) {
        Optional<User> maybeUser = userDao.findByAccount(account);
        User user = maybeUser.orElseThrow(() -> new NoSuchElementException("No such account!"));
        return changePasswdByUser(user, newPasswd);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean changePasswdById(Integer id, String newPasswd) {
        Optional<User> maybeUser = userDao.findById(id);
        User user = maybeUser.orElseThrow(() -> new NoSuchElementException("No such account!"));
        return changePasswdByUser(user, newPasswd);
    }

    private Boolean changePasswdByUser(User user, String newPasswd) {
        UserAccount userAccount = user.getUserAccount();
        /** Reject same passwd */
        if (userAccount.getPasswd().equals(newPasswd)) {
            return false;
        }
        userAccount.setPasswd(newPasswd);
        return true;
    }
}
