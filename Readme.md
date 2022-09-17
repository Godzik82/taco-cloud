############### ИНФО ####################

Данные проект является учебным и создан в рамках прочтения книги "Spring в действии. Издание шестое".
В ходе создания проекта с помощью фрагментов кода, всплывали ошибки не описаные в книге. В связи с этим в проект мною
самостоятельно вносились изменения.

ОБОЗНАЧЕНИЯ В ТЕКСТЕ:
!!! - знак, обзначающий строку в которой выдавалась ошибка

######## ОШИБКИ / ИЗМЕНЕНИЯ #############

ОШИБКА №1. стр. 97

Теперь, завершив работу над JdbcIngredientRepository, мы можем
внедрить его в DesignTacoController и использовать для предостав-
ления списка объектов Ingredient вместо жестко «зашитых» значе-
ний (как было сделано в главе 2). В листинге 3.7 показаны изменения
в DesignTacoController.

@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {
    
    private final IngredientRepository ingredientRepo;
    
    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        Iterable<Ingredient> ingredients = ingredientRepo.findAll();
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
!!!     model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
    }
}

Выдавал ошибку - в filterByType(ingredients, type)) передавалась аргумент ingredients, тип которого
не соответствоал при вызове метода, содержащегося в данном классе: указан List, передавался Iterable.

МОЕ РЕШЕНИЕ:

Переписал указанный метод следующим образом:

private Iterable<Ingredient> filterByType(Iterable<Ingredient> ingredients, Type type) {
        List<Ingredient> ingredientsList = new ArrayList<>();
        ingredients.forEach(ingredientsList::add);
        return  ingredientsList
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

ОШИБКА №2.  стр. 99

листинге 3.9 показан SQL-код, создающий эти таблицы.
Листинг 3.9 Определение схемы базы данных для приложения Taco Cloud

create table if not exists Taco_Order (
id identity,
delivery_Name varchar(50) not null,
delivery_Street varchar(50) not null,
delivery_City varchar(50) not null,
delivery_State varchar(2) not null,
delivery_Zip varchar(10) not null,
cc_number varchar(16) not null,
cc_expiration varchar(5) not null,
cc_cvv varchar(3) not null,
placed_at timestamp not null);

create table if not exists Taco (
id identity,
name varchar(50) not null,
taco_order bigint not null,
taco_order_key bigint not null,
created_at timestamp not null);

create table if not exists Ingredient_Ref (
ingredient varchar(4) not null,
taco bigint not null,
taco_key bigint not null);

create table if not exists Ingredient (
id varchar(4) not null,
name varchar(25) not null,
type varchar(10) not null);

!!! alter table Taco
!!! add foreign key (taco_order) references Taco_Order(id);
!!! alter table Ingredient_Ref
!!! add foreign key (ingredient) references Ingredient(id);

Выдавал ошибку - связные поля не имеют уникальные значения (PRIMARY KEY | UNIQUE)

МОЕ РЕШЕНИЕ:

добавил в поля ограничения

create table if not exists Taco (
id identity UNIQUE,
....

create table if not exists Ingredient (
id varchar(4) not null UNIQUE,
.....

ОШИБКА №3. стр. 100

Переписал предлагаемые автором SQL-запросы в файле data.sql:

delete from Ingredient_Ref;
delete from Taco;
delete from Taco_Order;
delete from Ingredient;

insert into Ingredient (id, name, type)
    values (‘FLTO’, ‘Flour Tortilla’, ‘WRAP’);
insert into Ingredient (id, name, type)
    values (‘COTO’, ‘Corn Tortilla’, ‘WRAP’);
insert into Ingredient (id, name, type)
    values (‘GRBF’, ‘Ground Beef’, ‘PROTEIN’);
insert into Ingredient (id, name, type)
    values (‘CARN’, ‘Carnitas’, ‘PROTEIN’);
insert into Ingredient (id, name, type)
    values (‘TMTO’, ‘Diced Tomatoes’, ‘VEGGIES’);
insert into Ingredient (id, name, type)
    values (‘LETC’, ‘Lettuce’, ‘VEGGIES’);
insert into Ingredient (id, name, type)
    values (‘CHED’, ‘Cheddar’, ‘CHEESE’);
insert into Ingredient (id, name, type)
    values (‘JACK’, ‘Monterrey Jack’, ‘CHEESE’);
insert into Ingredient (id, name, type)
    values (‘SLSA’, ‘Salsa’, ‘SAUCE’);
insert into Ingredient (id, name, type)
    values (‘SRCR’, ‘Sour Cream’, ‘SAUCE’);

МОЕ РЕШЕНИЕ

delete from Ingredient_Ref;
delete from Taco;
delete from Taco_Order;
delete from Ingredient;

insert into Ingredient (id, name, type) values
    ('FLTO', 'Flour Tortilla', 'WRAP'),
    ('COTO', 'Corn Tortilla', 'WRAP'),
    ('GRBF', 'Ground Beef', 'PROTEIN'),
    ('CARN', 'Carnitas', 'PROTEIN'),
    ('TMTO', 'Diced Tomatoes', 'VEGGIES'),
    ('LETC', 'Lettuce', 'VEGGIES'),
    ('CHED', 'Cheddar', 'CHEESE'),
    ('JACK', 'Monterrey Jack', 'CHEESE'),
    ('SLSA', 'Salsa', 'SAUCE'),
    ('SRCR', 'Sour Cream', 'SAUCE');


ОШИБКА №4 стр.105

Выдавал ошибку - в private void saveIngredientRefs(long tacoId, List<IngredientRef> ingredientRefs) передавался аргумент
List<IngredientRef> ingredientRefs, в связи с этим в методе private long saveTaco этого же класса появлялась ошибка при вызове
метода private void saveIngredientRefs, в который передавлся аргумент taco.getIngredients(), возвращающий тип List<Ingredient>,
а не List<IngredientRef>

private void saveIngredientRefs(
!!! long tacoId, List<IngredientRef> ingredientRefs) {
    int key = 0;
    for (IngredientRef ingredientRef : ingredientRefs) {
        jdbcOperations.update(
            "insert into Ingredient_Ref (ingredient, taco, taco_key) "
            + "values (?, ?, ?)",
            ingredientRef.getIngredient(), tacoId, key++);
    }
}

МОЕ РЕШЕНИЕ:

переписал метод private void saveIngredientRefs

private void saveIngredientRefs(long tacoId, List<Ingredient> ingredients) {
        int key = 0;

        for (Ingredient ingredient : ingredients) {
            jdbcOperations.update(
                "insert into Ingredient_Ref (ingredient, taco, taco_key) "
                + "values (?, ?, ?)",
                ingredient.getName(),
                tacoId,
                key++);
        }
    }
