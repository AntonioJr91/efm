package afsj.efm.category.application.mappers;

import afsj.efm.category.application.dtos.CategoryRequest;
import afsj.efm.category.application.dtos.CategoryResponse;
import afsj.efm.category.domain.entities.Category;

import java.util.List;

public final class CategoryMapper {
   public static CategoryResponse toDto(Category category) {
      return new CategoryResponse(category.getId(), category.getName());
   }

   public static Category toEntity(CategoryRequest dto) {
      return new Category(dto.name());
   }

   public static List<CategoryResponse> toDtoList(List<Category> listCategories) {
      return listCategories.stream().map(CategoryMapper::toDto).toList();
   }
}
