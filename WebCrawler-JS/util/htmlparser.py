# coding:utf8
# filename:htmlparser.py

import re
from bs4 import BeautifulSoup


class ParseHtml(object):
    def __init__(self, html_doc):
        self.html_doc = html_doc
        self.__dict = dict()
        
    def get_result(self):
        soup = BeautifulSoup(self.html_doc, "html.parser")
        self.__dict['title'] = soup.title.get_text()
        articles = soup.find_all("li", id=re.compile("note"))
        article_list = []
        for article in articles:
            article_obj = dict()
            article_obj["title"] = article.find("a", class_="title").get_text()
            article_obj["read"] = article.find("i", class_="ic-list-read").parent.get_text().replace('\n', '')
            article_obj["comments"] = article.find("i", class_="ic-list-comments").parent.get_text().replace('\n', '')
            article_obj["like"] = article.find("i", class_="ic-list-like").parent.get_text().replace('\n', '')
            article_list.append(article_obj)
        self.__dict['article_list'] = article_list
        return self.__dict
