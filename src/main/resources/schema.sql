create table if not exists pizza_order
(
    id              identity,
    delivery_name   varchar(50) not null,
    delivery_street varchar(50) not null,
    delivery_city   varchar(50) not null,
    delivery_state  varchar(2)  not null,
    delivery_zip    varchar(10) not null,
    cc_number       varchar(16) not null,
    cc_expiration   varchar(5)  not null,
    cc_cvv          varchar(3)  not null,
    placed_at       timestamp   not null
);

create table if not exists pizza
(
    id              identity,
    name            varchar(50) not null,
    created_at      timestamp   not null,
    pizza_order_key bigint      not null,
    pizza_order     bigint      not null
);

create table if not exists ingredient_ref
(
    ingredient varchar(4) not null,
    pizza      bigint     not null,
    pizza_key  bigint     not null
);

create table if not exists ingredient
(
    id   varchar(4)  primary key,
    name varchar(25) not null,
    type varchar(25) not null
);


alter table pizza
    add constraint fk_pizza_on_pizza_order foreign key (pizza_order) references pizza_order (id);
alter table ingredient_ref
    add constraint fk_ingredient_ref_on_ingredient foreign key (ingredient) references ingredient (id);
alter table ingredient_ref
    add constraint fk_ingredient_ref_on_pizza foreign key (pizza) references pizza (id);