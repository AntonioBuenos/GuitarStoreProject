package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.domain.Guitar;
import by.smirnov.guitarstoreproject.repository.GuitarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.smirnov.guitarstoreproject.builder.Guitars.aGuitar;
import static by.smirnov.guitarstoreproject.constants.CommonConstants.AVG_BY_INSTOCK;
import static by.smirnov.guitarstoreproject.constants.CommonConstants.AVG_BY_PRICELIST;
import static by.smirnov.guitarstoreproject.constants.TestConstants.TEST_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GuitarServiceImplTest {

    @Mock
    private GuitarRepository repository;

    @InjectMocks
    private GuitarServiceImpl service;

    @Captor
    ArgumentCaptor<Guitar> guitarCaptor;

    @Captor
    ArgumentCaptor<Long> idCaptor;

    private Guitar testGuitar;

    @BeforeEach
    void init(){
        testGuitar = aGuitar().build();
    }

    @Test
    @DisplayName("findById should return Guitar")
    void checkFindByIdShouldReturnGuitar() {
        doReturn(Optional.of(testGuitar)).when(repository).findById(TEST_ID);

        Guitar actual = service.findById(TEST_ID);

        assertThat(actual).isEqualTo(testGuitar);
    }

    @Test
    @DisplayName("findAll should return list of Guitars")
    void checkFindAllShouldReturnGuitarList() {
        Pageable pageable = mock(Pageable.class);
        List<Guitar> brands = List.of(testGuitar, testGuitar);
        Page<Guitar> page = new PageImpl<>(brands);
        doReturn(page).when(repository).findByIsDeleted(pageable, false);

        List<Guitar> actual = service.findAll(pageable);

        assertThat(brands).isEqualTo(actual);
    }

    @Test
    @DisplayName("create should return Guitar")
    void checkCreateShouldReturnGuitar() {
        doReturn(testGuitar).when(repository).save(testGuitar);

        Guitar actual = service.create(testGuitar);

        assertThat(actual).isEqualTo(testGuitar);
    }

    @Test
    @DisplayName("update should return Guitar")
    void checkUpdateShouldReturnGuitar() {
        doReturn(testGuitar).when(repository).save(testGuitar);

        Guitar actual = service.update(testGuitar);

        assertThat(actual).isEqualTo(testGuitar);
    }

    @Test
    @DisplayName("delete should pass argument true as isDeleted")
    void checkDeleteShouldPassArgumentIsDeletedTrue() {
        doReturn(Optional.of(testGuitar)).when(repository).findById(testGuitar.getId());
        service.delete(testGuitar.getId());

        verify(repository).save(guitarCaptor.capture());
        Guitar value = guitarCaptor.getValue();
        assertTrue(value.getIsDeleted());
    }

    @Test
    @DisplayName("hardDelete should pass id argument")
    void checkHardDeleteShouldCallRepository() {
        service.hardDelete(TEST_ID);

        verify(repository).deleteById(idCaptor.capture());
        Long value = idCaptor.getValue();
        assertThat(value).isEqualTo(TEST_ID);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1099.9911111, 1199.9899999",
            "1099.994, 1199.985",
            "1099.99499999, 1199.990"
    }, delimiter = ',')
    void showAverageGuitarPrices() {
        Map<String, String> expected = Map.of(AVG_BY_PRICELIST, "1099,99 $", AVG_BY_INSTOCK, "1199,99 $");
        doReturn(1099.9911111).when(repository).findAvgListPrice();
        doReturn(1199.9899999).when(repository).findAvgInstockPrice();

        Map<String, String> actual = service.showAverageGuitarPrices();

        assertThat(actual).isEqualTo(expected);
    }
}
