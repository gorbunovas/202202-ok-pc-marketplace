package ru.gorbunovas.mappers.exceptions

class UnknownRequestClass(clazz: Class<*>): RuntimeException("Class $clazz cannot be mapped to MkplContext")
