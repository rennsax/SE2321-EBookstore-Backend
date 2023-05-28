package com.sjtu.rbj.bookstore.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.sjtu.rbj.bookstore.constant.Constants;
import com.sjtu.rbj.bookstore.dto.OrderInfoDTO;
import com.sjtu.rbj.bookstore.entity.Order;
import com.sjtu.rbj.bookstore.service.OrderService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Bojun Ren
 * @date 2023/04/19
 */
@Slf4j
@RestController
@RequestMapping(path = "/order")
@CrossOrigin(Constants.ALLOW_ORIGIN)
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Handle the request for submitting a pending order.
     *
     * The response body {@code params} is expected to contain a field {@code orderId}.
     */
    @PostMapping("/submit")
    public void submitOrder(@RequestBody Map<String, String> params) {
        Integer orderId = Integer.valueOf(params.get("orderId"));
        log.info(orderId.toString());
        orderService.submitOrder(orderId);
        // TODO response with status code
    }

    @GetMapping("/{orderId}")
    public String getOrderInfo(@PathVariable Integer orderId) {
        OrderInfoDTO orderInfo = orderService.getOrderInfoByOrderId(orderId);
        return JSON.toJSONString(orderInfo);
    }

    /**
     * Handle the request to update an order,
     * including add or delete some ordered books for the order.
     *
     * The request body should contains {@code uuid} and {@code quantity}.
     */
    @PatchMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOrder(@PathVariable Integer orderId, @RequestBody Map<String, String> params) {
        UUID uuid = UUID.fromString(params.get("uuid"));
        Integer quantity = Integer.valueOf(params.get("quantity"));
        orderService.updateOrder(orderId, uuid, quantity);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder() {
        // TODO
    }

    /**
     * Handle the request to get the user's all orders.
     */
    @GetMapping
    public String getAllOrderByUserId(@RequestParam("userId") Integer userId) {
        List<Order> orderInfoList = orderService.getOrderByUserId(userId);
        JSONArray jsonArray = new JSONArray();
        for (Order order : orderInfoList) {
            jsonArray.add(OrderInfoDTO.from(order));
        }
        return jsonArray.toString();
    }
}
