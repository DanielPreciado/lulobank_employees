# language : en
Feature: obtener empleados de la compañia

  Se debe obtener la lista de empleados adscritos
  a la compañia, de tal manera que se pueda consultar
  la lista de empleados de la compañia

  Scenario: encontrar todos los empleados de la compañia
    Given que se quiere consultar la informacion de los empleados
    When se solicita la informacion de dichos trabajadores
    Then se obtiene la informacion de los 24 empleados
