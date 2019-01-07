# coding=utf-8
from peewee import *
from storage.base import BaseModel


class Article(BaseModel):
    name = CharField()
    read = IntegerField()
    like = IntegerField()
    comment = IntegerField()
    create_time = DateTimeField()
    status = BooleanField()

