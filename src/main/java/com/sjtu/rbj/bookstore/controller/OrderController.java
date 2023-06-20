package com.sjtu.rbj.bookstore.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sjtu.rbj.bookstore.constant.Constants;
import com.sjtu.rbj.bookstore.dto.BookOrdered;
import com.sjtu.rbj.bookstore.dto.OrderInfoDTO;
import com.sjtu.rbj.bookstore.entity.Order;
import com.sjtu.rbj.bookstore.exception.IncompleteRequestBodyException;
import com.sjtu.rbj.bookstore.service.OrderService;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Bojun Ren
 * @date 2023/04/19
 */
@RestController
@RequestMapping(path = "/orders")
@CrossOrigin(Constants.ALLOW_ORIGIN)
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{orderId}")
    public OrderInfoDTO getOrderInfo(@PathVariable Integer orderId) {
        return orderService.getOrderInfoByOrderId(orderId);
    }

    /**
     * Handle the request to update an order, including add or delete some ordered
     * books for the order. The request body should contains {@code uuid} and {@code quantity}.
     */
    @PatchMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOrder(@PathVariable Integer orderId, @RequestBody OrderRequestBody body) {

        if (OrderRequestBody.OP_UPDATE.equals(body.getOp())) {
            // OP: update items
            BookOrdered bookOrdered = body.getBookOrdered();

            if (bookOrdered == null) {
                throw new IncompleteRequestBodyException("Please provide bookOrdered info");
            }
            // Check if the request body is complete
            if (bookOrdered.getUuid() == null || bookOrdered.getQuantity() == null) {
                throw new IncompleteRequestBodyException("Incomplete bookOrdered info");
            }

            Boolean successUpdate = orderService.updateOrder(orderId, bookOrdered.getUuid(),
                    bookOrdered.getQuantity());
            if (!successUpdate) {
                throw new UnsupportedOperationException("Update items failed!");
            }

        } else if (OrderRequestBody.OP_CHECKOUT.equals(body.getOp())) {
            // OP: checkout (ignore other fields)
            orderService.submitOrder(orderId);

        } else {
            // OP: any other op (invalid)
            throw new IncompleteRequestBodyException("Unknown operation");
        }
    }

    @GetMapping
    public List<OrderInfoDTO> getAllOrderByUserId(@RequestParam("userId") Integer userId) {
        List<Order> orderInfoList = orderService.getOrderByUserId(userId);
        List<OrderInfoDTO> orderInfoDTOList = new ArrayList<>();
        for (Order order : orderInfoList) {
            orderInfoDTOList.add(OrderInfoDTO.from(order));
        }
        return orderInfoDTOList;
    }

    @Getter
    @AllArgsConstructor
    static public class OrderRequestBody {
        private String op;
        private BookOrdered bookOrdered;

        public static final String OP_UPDATE = "update items";
        public static final String OP_CHECKOUT = "checkout";
    }
}
