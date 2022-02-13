# language : en
Feature: registrar un nuevo empleado

  Se debe crear un nuevo empleado con la informacion
  basica necesaria para que sea registrado en db como
  nuevo empleado de la compañia

  name, salary, age son requisitos para el registro:
  {ejemplo} para reistrar un nuevo empleado se debe proveer su nombre, salario y edad

  Scenario Outline: Registrar un nuevo empleado para la empresa
    Given  un nuevo empleado con los siguientes datos:
      | id   | employee_name   | employee_age   | employee_salary   |
      | <id> | <employee_name> | <employee_age> | <employee_salary> |
    When el usuario es registrado atraves del sistema
    Then el usuario debe quedar registrado como un nuevo empleado
    Examples:
      | id | employee_name | employee_age | employee_salary |
      | 1  | Test          | 23           | 1223            |
