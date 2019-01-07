# coding=utf8

from util.htmlparser import ParseHtml
from util.articleget import get_html_doc
import sys


if __name__ == '__main__':
    url = sys.argv[0]
    text = get_html_doc(sys.argv[1])
    parse_html = ParseHtml(text)
    result = parse_html.get_result()
    print("title: %s" % result.get("title"))
    for i in result.get("article_list"):
        print("article: {}".format(i))
