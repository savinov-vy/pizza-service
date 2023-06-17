delete
from Ingredient_Ref;
delete
from Pizza;
delete
from Pizza_Order;
delete
from Ingredient;

insert into Ingredient (id, name, type)
values ('FLTO', 'Flour Tortilla', 'CHEESE'),
       ('CHED', 'Cheddar', 'CHEESE'),
       ('COTO', 'Corn Tortilla', 'SAUSAGE'),
       ('GRBF', 'Ground Beef', 'THICK_CRUST'),
       ('CARN', 'Carnitas', 'THIN_CRUST'),
       ('LETC', 'Lettuce', 'TOMATO'),
       ('JACK', 'Monterrey Jack', 'VEGGIES'),
       ('SLSA', 'Salsa', 'TOMATO');