insert into shape_category (id, shape_category_name) values (1, 'Rectangle');
insert into shape_category (id, shape_category_name) values  (2, 'Square');
insert into shape_category (id, shape_category_name) values  (3, 'Circle');
insert into shape_category (id, shape_category_name) values  (4, 'Oval');

insert into dimension (shape_category_id, dimension) values (1, 'size');
insert into dimension (shape_category_id, dimension) values (2, 'width');
insert into dimension (shape_category_id, dimension) values (2, 'length');
insert into dimension (shape_category_id, dimension) values (3, 'radius');
insert into dimension (shape_category_id, dimension) values (4, 'major radius');
insert into dimension (shape_category_id, dimension) values (4, 'minor radius');