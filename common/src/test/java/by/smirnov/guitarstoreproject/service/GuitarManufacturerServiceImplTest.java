package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.domain.GuitarManufacturer;
import by.smirnov.guitarstoreproject.repository.GuitarManufacturerRepository;
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

import static by.smirnov.guitarstoreproject.builder.Brands.aBrand;
import static by.smirnov.guitarstoreproject.constants.TestConstants.TEST_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GuitarManufacturerServiceImplTest {

    @Mock
    private GuitarManufacturerRepository repository;

    @InjectMocks
    private GuitarManufacturerServiceImpl service;

    @Captor
    ArgumentCaptor<GuitarManufacturer> genreCaptor;

    @Captor
    ArgumentCaptor<Long> idCaptor;

    private GuitarManufacturer testBrand;

    @BeforeEach
    void init(){
        testBrand = aBrand().build();
    }

    @Test
    @DisplayName("findById should return GuitarManufacturer")
    void checkFindByIdShouldReturnGuitarManufacturer() {
        when(repository.findById(TEST_ID)).thenReturn(Optional.of(testBrand));

        GuitarManufacturer actual = service.findById(TEST_ID);

        assertThat(actual).isEqualTo(testBrand);
    }

    @Test
    @DisplayName("findAll should return list of GuitarManufacturers")
    void checkFindAllShouldReturnGuitarManufacturerList() {
        Pageable pageable = mock(Pageable.class);
        List<GuitarManufacturer> brands = List.of(testBrand, testBrand);
        Page<GuitarManufacturer> page = new PageImpl<>(brands);
        doReturn(page).when(repository).findAll(pageable);

        List<GuitarManufacturer> actual = service.findAll(pageable);

        assertThat(brands).isEqualTo(actual);
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void hardDelete() {
    }
}
