package basic;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Order는 0부터
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MethodOrder_OrderAnnotation {

    @Order(1)
    @Test
    void test1() {
        System.out.println("test1");
    }

    @Order(0)
    @Test
    void test2() {
        System.out.println("test1");
    }

}
