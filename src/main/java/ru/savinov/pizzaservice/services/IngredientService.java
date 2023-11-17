package ru.savinov.pizzaservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.savinov.pizzaservice.controllers.dto.IngredientDto;
import ru.savinov.pizzaservice.entities.Ingredient;
import ru.savinov.pizzaservice.mapper.IngredientDtoMapper;
import ru.savinov.pizzaservice.repositories.IngredientRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepo;
    private final IngredientDtoMapper ingredientDtoMapper;

    public Iterable<IngredientDto> filterByType(List<IngredientDto> ingredients, Ingredient.Type type) {
        return ingredients
                .stream()
                .filter(ingredient -> ingredient.getType().equals(type))
                .collect(Collectors.toList());
    }

    public List<IngredientDto> findAll() {
        return ingredientRepo.findAll().stream()
                .map(ingredientDtoMapper::map)
                .collect(Collectors.toList());
    }

    public Optional<IngredientDto> findById(String id) {
        return ingredientRepo.findById(id)
                .map(ingredientDtoMapper::map);
    }

    @Transactional
    public Optional<IngredientDto> create(IngredientDto ingredientDto) {
        return Optional.of(ingredientDto)
                .map(ingredientDtoMapper::map)
                .map(ingredientRepo::saveAndFlush)
                .map(ingredientDtoMapper::map);
    }

    @Transactional
    public Optional<IngredientDto> update(String id, IngredientDto ingredientDto) {
        return ingredientRepo.findById(id)
                .map(ingredient -> ingredientDtoMapper.map(ingredientDto, ingredient))
                .map(ingredientRepo::saveAndFlush)
                .map(ingredientDtoMapper::map);
    }

    @Transactional
    public boolean delete(String id) {
        return ingredientRepo.findById(id)
                .map(ingredient -> {
                    ingredientRepo.delete(ingredient);
                    ingredientRepo.flush();
                    return true;
                })
                .orElse(false);
    }

}