package com.assessment.tournament.domain;

import com.assessment.tournament.domain.model.Category;
import com.assessment.tournament.domain.spi.CategoryPersistencePort;
import com.assessment.tournament.domain.usecase.CategoryUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryUseCaseTest {
    @Mock
    private CategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    @Test
    void shouldReturnCategoryWhenIdExists() {
        // Arrange
        Long categoryId = 1L;
        Category expectedCategory = new Category(categoryId, "Gold", 10);

        when(categoryPersistencePort.findById(categoryId)).thenReturn(expectedCategory);

        // Act
        Category result = categoryUseCase.findById(categoryId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedCategory, result);
        verify(categoryPersistencePort).findById(categoryId);
    }
}