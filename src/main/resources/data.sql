delete
from ingredient_ref;
delete
from pizza;
delete
from pizza_order;
delete
from ingredient;

insert into ingredient (id, name, type)
values ('FLTO', 'Flour Tortilla', 'CHEESE'),
       ('CHED', 'Cheddar', 'CHEESE'),
       ('COTO', 'Corn Tortilla', 'SAUSAGE'),
       ('GRBF', 'Ground Beef', 'THICK_CRUST'),
       ('CARN', 'Carnitas', 'THIN_CRUST'),
       ('LETC', 'Lettuce', 'TOMATO'),
       ('JACK', 'Monterrey Jack', 'VEGGIES'),
       ('SLSA', 'Salsa', 'TOMATO');