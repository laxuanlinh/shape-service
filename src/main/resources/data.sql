insert into shape_category (shape_category_name, formula, rules) values ('Rectangle', 'width * length','width > 0 && length > 0');
insert into shape_category (shape_category_name, formula, rules) values  ('Square', 'size * size', 'size > 0');
insert into shape_category (shape_category_name, formula, rules) values  ('Circle', 'radius * radius * 3.14', 'radius > 0');
insert into shape_category (shape_category_name, formula, rules) values  ('Oval', 'majorradius * minorradius * 3.14','majorradius > 0 && minorradius > 0');

insert into dimension (shape_category_name, dimension) values ('Rectangle', 'width');
insert into dimension (shape_category_name, dimension) values ('Rectangle', 'length');
insert into dimension (shape_category_name, dimension) values ('Square', 'size');
insert into dimension (shape_category_name, dimension) values ('Circle', 'radius');
insert into dimension (shape_category_name, dimension) values ('Oval', 'majorradius');
insert into dimension (shape_category_name, dimension) values ('Oval', 'minorradius');

insert into users(username, password, enabled) values ('laxuanlinh', '$2a$10$HnfRIDTI7y.hlPisMfXeFuGHkqLaD/95bS4ICCROdFqO4KkzRK3yi', true);
insert into authorities(id, username, authority) values (1,'laxuanlinh', 'ROLE_ADMIN');

insert into users(username, password, enabled) values ('laxuanlinh2', '$2a$10$HnfRIDTI7y.hlPisMfXeFuGHkqLaD/95bS4ICCROdFqO4KkzRK3yi', true);
insert into authorities(id, username, authority) values (2,'laxuanlinh2', 'ROLE_USER');

insert into shape (shape_id, shape_name, shape_category_name, username, enabled) values (1, 'my rectangle', 'Rectangle','laxuanlinh', true  );


insert into size(shape_id, name, size) values (1, 'width', 10);
insert into size(shape_id, name, size) values (1, 'length', 10);

insert into conditions_other_categories(shape_category_name, conditions, other_category_name) values ('Rectangle', 'width == length', 'Square');