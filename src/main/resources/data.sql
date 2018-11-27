delete from Shaurma_Order_Shaurmas;
delete from Shaurma_Ingredients;
delete from Shaurma;
delete from Shaurma_Order;
delete from Ingredient;

insert into Ingredient (id, name, type) values 
  ('RGPT', 'Regular Pita', 'WRAP'),
  ('CHPT', 'Cheesy Pita', 'WRAP'),
  ('CHKN', 'Chicken', 'PROTEIN'),
  ('PORK', 'Pork', 'PROTEIN'),
  ('TMT', 'Tomatoes', 'VEGGIES'),
  ('CBG', 'Cabbage', 'VEGGIES'),
  ('CHED', 'Cheddar', 'CHEESE'),
  ('JACK', 'Monterry Jack', 'CHEESE'),
  ('SLSA', 'Salsa', 'SAUCE'),
  ('SRCR', 'Sour Cream', 'SAUCE');