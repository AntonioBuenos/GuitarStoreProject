package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.domain.Order;
import by.smirnov.guitarstoreproject.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    void findByIdShouldCallRepository() {
        final Order order = mock(Order.class);
        when(repository.findById(ID)).thenReturn(Optional.ofNullable(order));

        final Order actual = orderService.findById(ID);

        assertThat(actual).isEqualTo(order);
    }

    @Test
    void findAll() {
    }

    @Test
    void createShouldCallRepository() {
        final Order order = mock(Order.class);
        when(repository.save(order)).thenReturn(order);

        final Order actual = orderService.update(order);

        assertThat(actual).isEqualTo(order);
    }

    @Test
    void updateShouldCallRepository() {
        final Order order = mock(Order.class);
        when(repository.save(order)).thenReturn(order);

        final Order actual = orderService.update(order);

        assertThat(actual).isEqualTo(order);
    }

    @Test
    void completeOrder() {
    }

    @Test
    void cancelOrder() {
    }

    @Test
    void suspendOrder() {
    }

    @Test
    void resumeOrder() {
    }

    @Test
    void hardDelete() {
        orderService.hardDelete(ID);

        verify(repository).deleteById(ID);
    }
}
