insert into shape_category (shape_category_name, formula) values ('Rectangle', 'width * length');
insert into shape_category (shape_category_name, formula) values  ('Square', 'size * size');
insert into shape_category (shape_category_name, formula) values  ('Circle', 'radius * radius * 3.14');
insert into shape_category (shape_category_name, formula) values  ('Oval', 'majorradius * minorradius * 3.14');

insert into dimension (shape_category_name, dimension) values ('Rectangle', 'width');
insert into dimension (shape_category_name, dimension) values ('Rectangle', 'length');
insert into dimension (shape_category_name, dimension) values ('Square', 'size');
insert into dimension (shape_category_name, dimension) values ('Circle', 'radius');
insert into dimension (shape_category_name, dimension) values ('Oval', 'majorradius');
insert into dimension (shape_category_name, dimension) values ('Oval', 'minorradius');

insert into shape (shape_id, shape_name, shape_category_name) values (1, 'my rectangle', 'Rectangle');

insert into size(shape_id, name, size) values (1, 'width', 10);
insert into size(shape_id, name, size) values (1, 'length', 10);