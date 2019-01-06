# coding=utf8
# filename=article.py


class Article(object):

    def __init__(self, title, read, like, comments):
        self.title = title
        self.read = int(read)
        self.like = int(like)
        self.comments = int(comments)

    def to_string(self):
        return "标题：%s \n 阅读：%d ，喜欢：%d ，评论：%d" % (self.title, self.read, self.like, self.comments)
