package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.domain.Instock;
import by.smirnov.guitarstoreproject.domain.Order;
import by.smirnov.guitarstoreproject.domain.enums.GoodStatus;
import by.smirnov.guitarstoreproject.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.smirnov.guitarstoreproject.builder.Instocks.anInstock;
import static by.smirnov.guitarstoreproject.builder.Orders.anOrder;
import static by.smirnov.guitarstoreproject.constants.TestConstants.TEST_ID;
import static by.smirnov.guitarstoreproject.domain.enums.OrderStatus.CANCELLED;
import static by.smirnov.guitarstoreproject.domain.enums.OrderStatus.COMPLETED;
import static by.smirnov.guitarstoreproject.domain.enums.OrderStatus.CREATED;
import static by.smirnov.guitarstoreproject.domain.enums.OrderStatus.SUSPENDED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository repository;

    @Mock
    InstockServiceImpl instockService;

    private Instock testInstock;

    private Order testOrder;

    @Captor
    ArgumentCaptor<Order> orderCaptor;

    @BeforeEach
    void init() {
        testInstock = anInstock().build();
        testOrder = anOrder().instock(testInstock).build();
    }

    @Test
    @DisplayName("findById should return Order")
    void checkFindByIdShouldReturnOrder() {
        doReturn(Optional.of(testOrder)).when(repository).findById(TEST_ID);

        Order actual = orderService.findById(TEST_ID);

        assertThat(actual).isEqualTo(testOrder);
    }

    @Test
    @DisplayName("findAll should return Order list")
    void checkFindAllShouldReturnOrdersList() {
        Pageable pageable = mock(Pageable.class);
        List<Order> orders = new ArrayList<>();
        orders.add(mock(Order.class));
        orders.add(mock(Order.class));
        Page<Order> page = new PageImpl<>(orders);
        doReturn(page).when(repository).findAll(pageable);

        List<Order> actual = orderService.findAll(pageable);

        assertThat(orders).isEqualTo(actual);
    }

    @Test
    @DisplayName("create should return Order")
    void checkCreateShouldReturnOrder() {
        doReturn(testOrder).when(repository).save(testOrder);

        Order actual = orderService.create(testOrder);

        assertThat(actual).isEqualTo(testOrder);
    }

    @Test
    @DisplayName("update should return Order")
    void checkUpdateShouldReturnOrder() {
        doReturn(testOrder).when(repository).save(testOrder);

        Order actual = orderService.update(testOrder);

        assertThat(actual).isEqualTo(testOrder);
    }

    @Test
    @DisplayName("completeOrder should pass argument COMPLETED")
    void checkCompleteOrderShouldPassArgumentCompletedOrder() {
        long id = testOrder.getId();
        doReturn(Optional.of(testOrder)).when(repository).findById(id);

        orderService.completeOrder(id);

        verify(repository).save(orderCaptor.capture());
        Order value = orderCaptor.getValue();
        assertThat(value.getOrderStatus()).isEqualTo(COMPLETED);
    }

    @Test
    @DisplayName("cancelOrder should pass argument CANCELED")
    void checkCancelOrderShouldPassArgumentCanceledOrder() {
        long id = testOrder.getId();
        doReturn(Optional.of(testOrder)).when(repository).findById(id);
        doReturn(testInstock).when(instockService).update(testInstock, GoodStatus.AVAILABLE);

        orderService.cancelOrder(id);

        verify(repository).save(orderCaptor.capture());
        Order value = orderCaptor.getValue();
        assertThat(value.getOrderStatus()).isEqualTo(CANCELLED);
    }

    @Test
    @DisplayName("suspendOrder should pass argument SUSPENDED")
    void checkSuspendOrderShouldPassArgumentSuspendedOrder() {
        long id = testOrder.getId();
        doReturn(Optional.of(testOrder)).when(repository).findById(id);

        orderService.suspendOrder(id);

        verify(repository).save(orderCaptor.capture());
        Order value = orderCaptor.getValue();
        assertThat(value.getOrderStatus()).isEqualTo(SUSPENDED);
    }

    @Test
    @DisplayName("resumeOrder should pass argument CREATED")
    void checkResumeOrderShouldPassArgumentCreatedOrder() {
        long id = testOrder.getId();
        testOrder.setOrderStatus(SUSPENDED);
        doReturn(Optional.of(testOrder)).when(repository).findById(id);

        orderService.resumeOrder(id);

        verify(repository).save(orderCaptor.capture());
        Order value = orderCaptor.getValue();
        assertThat(value.getOrderStatus()).isEqualTo(CREATED);
    }

    @Test
    @DisplayName("hardDelete should call repository")
    void checkHardDeleteShouldCallRepository() {
        orderService.hardDelete(TEST_ID);

        verify(repository).deleteById(TEST_ID);
    }
}
