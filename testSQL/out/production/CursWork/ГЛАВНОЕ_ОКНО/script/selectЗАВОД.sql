select `Завод`.`Номер`, `Категорія відходу`.`Категорія відходу`, `Радіонуклід`.`Назва`, `Завод`.`Кількість`, `Завод`.`Дата виготовлення` from `Завод`
inner join `Категорія відходу`
on `Завод`.`Категорія відходу` = `Категорія відходу`.`Номер`
inner join `Радіонуклід`
on `Завод`.`Нуклід` = `Радіонуклід`.`Номер`
order by `Завод`.`Номер`