# coding=utf-8


class MysqlConf:
    data = {
        "host": "127.0.0.1",
        "port": 3306,
        "db": "js",
        "user": "root",
        "pass": "root"
    }

    def get_conf(self, key):
        return self.data[key]
