package sia.tacocloud.repositories;

import java.util.Optional;

import sia.tacocloud.data.Ingredient;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();

    Optional<Ingredient> findById(String id);

    Ingredient save(Ingredient ingredient);
}
