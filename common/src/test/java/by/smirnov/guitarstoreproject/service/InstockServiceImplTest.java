package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.domain.Instock;
import by.smirnov.guitarstoreproject.domain.enums.GoodStatus;
import by.smirnov.guitarstoreproject.repository.InstockRepository;
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

import java.util.List;
import java.util.Optional;

import static by.smirnov.guitarstoreproject.builder.Instocks.anInstock;
import static by.smirnov.guitarstoreproject.constants.TestConstants.TEST_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InstockServiceImplTest {

    @InjectMocks
    private InstockServiceImpl instockService;

    @Mock
    private InstockRepository repository;

    private Instock testInstock;

    @Captor
    ArgumentCaptor<Instock> instockCaptor;

    @BeforeEach
    void init() {
        testInstock = anInstock().build();
    }


    @Test
    @DisplayName("findById should return Instock")
    void checkFindByIdShouldReturnInstock() {
        doReturn(Optional.of(testInstock)).when(repository).findById(TEST_ID);

        Instock actual = instockService.findById(TEST_ID);

        assertThat(actual).isEqualTo(testInstock);
    }

    @Test
    @DisplayName("findAll should return Instock list")
    void checkFindAllShouldReturnInstocksList() {
        Pageable pageable = mock(Pageable.class);
        List<Instock> instocks = List.of(testInstock, testInstock);
        Page<Instock> page = new PageImpl<>(instocks);
        doReturn(page).when(repository).findAll(pageable);

        List<Instock> actual = instockService.findAll(pageable);

        assertThat(instocks).isEqualTo(actual);
    }

    @Test
    @DisplayName("create should return Instock")
    void checkCreateShouldReturnInstock() {
        doReturn(testInstock).when(repository).save(testInstock);

        Instock actual = instockService.create(testInstock);

        assertThat(actual).isEqualTo(testInstock);
    }

    @Test
    @DisplayName("update should change status to Reserved")
    void checkUpdateShouldChangeStatusToReserved() {
        doReturn(Optional.of(testInstock)).when(repository).findById(testInstock.getId());

        instockService.update(testInstock, GoodStatus.RESERVED);

        verify(repository).save(instockCaptor.capture());
        Instock value = instockCaptor.getValue();
        assertThat(value.getGoodStatus()).isEqualByComparingTo(GoodStatus.RESERVED);
    }

    @Test
    @DisplayName("update should return Instock")
    void checkUpdateShouldReturnInstock() {
        doReturn(testInstock).when(repository).save(testInstock);

        Instock actual = instockService.update(testInstock);

        assertThat(actual).isEqualTo(testInstock);
    }

    @Test
    @DisplayName("delete should change status to OutOfStock")
    void checkDeleteShouldChangeStatusToOutOfStock() {
        doReturn(Optional.of(testInstock)).when(repository).findById(testInstock.getId());

        instockService.delete(testInstock.getId());

        verify(repository).save(instockCaptor.capture());
        Instock value = instockCaptor.getValue();
        assertThat(value.getGoodStatus()).isEqualByComparingTo(GoodStatus.OUT_OF_STOCK);
    }

    @Test
    @DisplayName("hardDelete should call repository")
    void checkHardDeleteShouldCallRepository() {
        instockService.hardDelete(TEST_ID);

        verify(repository).deleteById(TEST_ID);
    }
}
