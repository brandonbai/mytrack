# coding=utf-8
from peewee import *
from config.mysql import MysqlConf

conf = MysqlConf()

db = MySQLDatabase(host=conf.get_conf("host"),
                   port=conf.get_conf("port"),
                   user=conf.get_conf("user"),
                   passwd=conf.get_conf("pass"),
                   database=conf.get_conf("db"),
                   charset="utf8")


def close_all():
    if not db.is_closed():
        db.close()


class BaseModel(Model):
    class Meta:
        database = db
