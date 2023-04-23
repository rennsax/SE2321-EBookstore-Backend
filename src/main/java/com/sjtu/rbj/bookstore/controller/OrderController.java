package com.sjtu.rbj.bookstore.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONObject;
import com.sjtu.rbj.bookstore.constant.Constant;
import com.sjtu.rbj.bookstore.data.BookOrdered;
import com.sjtu.rbj.bookstore.entity.OrderItem;
import com.sjtu.rbj.bookstore.repository.OrderItemRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Bojun Ren
 * @date 2023/04/19
 */
@Slf4j
@RestController
@RequestMapping("/order")
@CrossOrigin(Constant.ALLOW_ORIGIN)
public class OrderController {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @PostMapping("/submit")
    public String submitOrder(@RequestBody Map<String, String> params) {
        Integer orderId = Integer.valueOf(params.get("orderId"));
        log.info("Current order id: " + Integer.toString(orderId));
        List<OrderItem> orderItemList = orderItemRepository.findByOrderId(orderId);
        log.info(orderItemList.toString());
        JSONObject res = new JSONObject();
        res.put("flag", true);
        return res.toString();
    }

    // TODO delete this method
    @GetMapping("/current")
    public String getOrderInfo() {
        List<OrderItem> orderItemList = orderItemRepository.findByOrderId(1);
        int size = orderItemList.size();
        BookOrdered[] res = new BookOrdered[size];
        for (int i = 0; i < size; ++i) {
            res[i] = new BookOrdered(orderItemList.get(i).getItemId(), orderItemList.get(i).getQuantity());
        }
        return JSONObject.toJSONString(res);
    }

}
