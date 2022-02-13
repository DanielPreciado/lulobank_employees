# language : en
Feature: obtener un empleado

  Se debe obtener un  empleado dado su id
  de tal manera que se pueda consultar la informacion
  basica para dicho empleado de la compañia

  Scenario Outline: encontrar un empleado dado su id
    Given que se quiere consultar la informacion del empleado 10
    When se solicita la informacion de dicho trabajador
    Then se obtiene la siguiente informacion
      | id   | employee_name   | employee_age   | employee_salary   |
      | <id> | <employee_name> | <employee_age> | <employee_salary> |
    Examples:
      | id | employee_name | employee_age | employee_salary |
      | 10  | Sonya Frost  | 23           | 103600          |
