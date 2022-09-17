package sia.tacocloud.data;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Taco {

	@NotNull
	@Size(min=5, max=50, message = "Имя должно содержать 5 и более символов")
    private String name;

	@NotNull
	@Size(min=1, message = "Выберите хотя бы один ингредиент")
    private List<Ingredient> ingredients;

	private Long id;
	
	private Date createdAt = new Date();
}
