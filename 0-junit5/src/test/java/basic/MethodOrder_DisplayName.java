package basic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * <ol>
 *     <li>0</li>
 *     <li>01</li>
 *     <li>1</li>
 *     <li>10</li>
 *     <li>11</li>
 * </ol>
 */
@TestMethodOrder(MethodOrderer.DisplayName.class)
class MethodOrder_DisplayName {

    @DisplayName("0 test")
    @Test
    void test0() {
        System.out.println("test1");
    }

    @DisplayName("1 test")
    @Test
    void test1() {
        System.out.println("test1");
    }

    @Test
    @DisplayName("01 test")
    void test01() {
        System.out.println("test1");
    }

    @Test
    @DisplayName("10 test")
    void test10() {
        System.out.println("test1");
    }

    @Test
    @DisplayName("11 test")
    void test11() {
        System.out.println("test1");
    }

}
