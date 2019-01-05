#coding:utf8
#filename:htmlparser.py
import re
from bs4 import BeautifulSoup

class ParseHtml(object):
    def __init__(self, html_doc):
        self.html_doc = html_doc
        self.__dict = dict()
        
    def getResult(self):
        soup = BeautifulSoup(self.html_doc, "html.parser", from_encoding="utf8")
        self.__dict['title'] = soup.title.get_text()
        articles = soup.find_all("li",id=re.compile("note"))
        articleList = []
        for article in articles:
            articleObj = dict()
            articleObj["title"] = article.find("a", class_="title").get_text()
            articleObj["read"] = article.find("i", class_="ic-list-read").parent.get_text()
            articleObj["comments"] = article.find("i", class_="ic-list-comments").parent.get_text()
            articleObj["like"] = article.find("i", class_="ic-list-like").parent.get_text()
            articleList.append(articleObj)
        self.__dict['articleList'] = articleList;
        return self.__dict
