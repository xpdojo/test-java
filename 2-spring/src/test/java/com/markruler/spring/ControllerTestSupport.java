package com.markruler.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.markruler.spring.api.controller.order.OrderController;
import com.markruler.spring.api.controller.product.ProductController;
import com.markruler.spring.api.service.order.OrderService;
import com.markruler.spring.api.service.product.ProductService;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
        OrderController.class,
        ProductController.class
})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockitoBean
    protected OrderService orderService;

    @MockitoBean
    protected ProductService productService;

}
