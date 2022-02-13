# language : en
Feature: eliminar un empleado

  Se debe eliminar un  empleado dado su id
  de tal manera que se elimine de manera
  exitosa dicho empleado de la compañia

  Scenario: eliminar un empleado dado su id
    Given que se quiere eliminar el empleado 2
    When se solicita la eliminacion de dicho empleado
    Then se borran sus registros con exito

