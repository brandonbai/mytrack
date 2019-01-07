# coding=utf8

from util.htmlparser import ParseHtml
from util.articleget import get_html_doc
from storage.article import Article
import sys
import datetime


if __name__ == '__main__':
    url = sys.argv[0]
    text = get_html_doc(sys.argv[1])
    parse_html = ParseHtml(text)
    result = parse_html.get_result()
    for item in result.get("article_list"):
        dao = Article()
        dao.name = item["title"]
        dao.read = item["read"]
        dao.like = item["like"]
        dao.comment = item["comments"]
        dao.create_time = datetime.date.today()
        dao.status = True
        dao.save()

