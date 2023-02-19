package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.domain.Guitar;
import by.smirnov.guitarstoreproject.domain.Instock;
import by.smirnov.guitarstoreproject.domain.Order;
import by.smirnov.guitarstoreproject.domain.User;
import by.smirnov.guitarstoreproject.domain.enums.GoodStatus;
import by.smirnov.guitarstoreproject.domain.enums.OrderStatus;
import by.smirnov.guitarstoreproject.domain.enums.Placement;
import by.smirnov.guitarstoreproject.repository.OrderRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    private static final long ID = 1L;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository repository;

    @Mock InstockServiceImpl instockService;

    final Instock testInstock = mock(Instock.class);

    final Order testOrder = Order.builder()
            .id(ID)
            .customer(mock(User.class))
            .deliveryAddress("test")
            .instock(testInstock)
            .orderStatus(OrderStatus.CREATED)
            .build();

    @Captor
    ArgumentCaptor<Order> orderCaptor;

    @Test
    void checkFindByIdShouldReturnOrder() {
        final Order order = mock(Order.class);
        when(repository.findById(ID)).thenReturn(Optional.ofNullable(order));

        final Order actual = orderService.findById(ID);

        assertThat(actual).isEqualTo(order);
    }

    @Test
    void checkFindAllShouldReturnOrdersList() {
        final Pageable pageable = mock(Pageable.class);
        List<Order> orders = new ArrayList<>();
        orders.add(mock(Order.class));
        orders.add(mock(Order.class));
        Page<Order> page = new PageImpl<>(orders);
        doReturn(page).when(repository).findAll(pageable);

        List<Order> actual = orderService.findAll(pageable);

        assertThat(orders).isEqualTo(actual);
    }

    @Test
    void checkCreateShouldReturnOrder() {
        final Order order = mock(Order.class);
        when(repository.save(order)).thenReturn(order);

        final Order actual = orderService.update(order);

        assertThat(actual).isEqualTo(order);
    }

    @Test
    void checkUpdateShouldReturnOrder() {
        final Order order = mock(Order.class);
        when(repository.save(order)).thenReturn(order);

        final Order actual = orderService.update(order);

        assertThat(actual).isEqualTo(order);
    }

    @Test
    void checkCompleteOrderShouldPassArgumentCompletedOrder() {
        long id = testOrder.getId();
        doReturn(Optional.of(testOrder)).when(repository).findById(id);
        orderService.completeOrder(id);

        verify(repository).save(orderCaptor.capture());
        Order value = orderCaptor.getValue();
        assertThat(value.getOrderStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    @Test
    void checkCancelOrderShouldPassArgumentCanceledOrder() {
        long id = testOrder.getId();
        doReturn(Optional.of(testOrder)).when(repository).findById(id);
        doReturn(testInstock).when(instockService).update(testInstock, GoodStatus.AVAILABLE);
        orderService.cancelOrder(id);

        verify(repository).save(orderCaptor.capture());
        Order value = orderCaptor.getValue();
        assertThat(value.getOrderStatus()).isEqualTo(OrderStatus.CANCELLED);
    }

    @Test
    void checkSuspendOrderShouldPassArgumentSuspendedOrder() {
        long id = testOrder.getId();
        doReturn(Optional.of(testOrder)).when(repository).findById(id);
        orderService.suspendOrder(id);

        verify(repository).save(orderCaptor.capture());
        Order value = orderCaptor.getValue();
        assertThat(value.getOrderStatus()).isEqualTo(OrderStatus.SUSPENDED);
    }

    @Test
    void checkResumeOrderShouldPassArgumentCreatedOrder() {
        long id = testOrder.getId();
        testOrder.setOrderStatus(OrderStatus.SUSPENDED);
        doReturn(Optional.of(testOrder)).when(repository).findById(id);
        orderService.resumeOrder(id);

        verify(repository).save(orderCaptor.capture());
        Order value = orderCaptor.getValue();
        assertThat(value.getOrderStatus()).isEqualTo(OrderStatus.CREATED);
    }

    @Test
    void checkHardDeleteShouldCallRepository() {
        orderService.hardDelete(ID);

        verify(repository).deleteById(ID);
    }
}
