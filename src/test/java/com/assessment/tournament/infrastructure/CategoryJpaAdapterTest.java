package com.assessment.tournament.infrastructure;

import com.assessment.tournament.domain.model.Category;
import com.assessment.tournament.infrastructure.exception.DataNotFoundException;
import com.assessment.tournament.infrastructure.output.jpa.adapter.CategoryJpaAdapter;
import com.assessment.tournament.infrastructure.output.jpa.entity.CategoryEntity;
import com.assessment.tournament.infrastructure.output.jpa.mapper.CategoryEntityMapper;
import com.assessment.tournament.infrastructure.output.jpa.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryJpaAdapterTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryEntityMapper categoryEntityMapper;
    @InjectMocks
    private CategoryJpaAdapter categoryJpaAdapter;

    @Test
    void should_throwDataNotFoundException_when_categoryNotFound(){
        //Arrange
        Long nonExistentCategoryId = 5L;
        when(categoryRepository.findById(nonExistentCategoryId)).thenReturn(Optional.empty());

        //Act and Assert
        assertThrows(DataNotFoundException.class, () -> categoryJpaAdapter.findById(nonExistentCategoryId));
    }

    @Test
    void should_findCategoryById(){
        //Arrange
        Long categoryId = 1L;
        Category expectedCategory = new Category(1L, "Gold",10);
        CategoryEntity categoryEntity = new CategoryEntity(1L, "Gold",10);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));
        when(categoryEntityMapper.toModel(categoryEntity)).thenReturn(expectedCategory);

        //Act
        Category actualCategory = categoryJpaAdapter.findById(categoryId);

        //Assert
        assertEquals(expectedCategory, actualCategory);

    }
}
