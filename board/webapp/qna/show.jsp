<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="kr">
<head>
    <%@ include file="/include/header.jspf" %>
</head>
<body>
<%@ include file="/include/navigation.jspf" %>

<div class="container" id="main">
    <div class="col-md-12 col-sm-12 col-lg-12">
        <div class="panel panel-default">
          <header class="qna-header">
              <h2 class="qna-title"><c:out value="${board.title}"/></h2>
          </header>
          <div class="content-main">
              <article class="article">
                  <div class="article-header">
                      <div class="article-header-thumb">
                          <img src="https://graph.facebook.com/v2.3/100000059371774/picture" class="article-author-thumb" alt="">
                      </div>
                      <div class="article-header-text">
                          <a href="/users/92/kimmunsu" class="article-author-name"><c:out value="${board.writer}"/></a>
                          <a href="/questions/413" class="article-header-time" title="퍼머링크">
                              <c:out value="${board.createdDate}"/>
                              <i class="icon-link"></i>
                          </a>
                      </div>
                  </div>
                  <div class="article-doc">
                      <c:out value="${board.contents}"/>
                  </div>
                  <div class="article-util">
                      <ul class="article-util-list">
                          <li>
                              <a class="link-modify-article" href="/questions/<c:out value="${board.questionId}"/>/form">수정</a>
                          </li>
                          <li>
                              <form class="form-delete" action="/questions/423" method="POST">
                                  <input type="hidden" name="_method" value="DELETE">
                                  <button class="link-delete-articleww" type="submit">삭제</button>
                              </form>
                          </li>
                          <li>
                              <a class="link-modify-article" href="/">목록</a>
                          </li>
                      </ul>
                  </div>
              </article>

              <div class="qna-comment">
                  <div class="qna-comment-slipp">
                      <p class="qna-comment-count"><strong><c:out value="${board.countOfAnswer}"/></strong>개의 의견</p>
                      <div class="qna-comment-slipp-articles">
                      <c:forEach items="${answers}" var="list">
                          <article class="article">
                              <div class="article-header">
                                  <div class="article-header-thumb">
                                      <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
                                  </div>
                                  <div class="article-header-text">
                                      <a href="/users/${list.writer}/자바지기" class="article-author-name">${list.writer}</a>
                                      <a href="#answer-1434" class="article-header-time" title="퍼머링크">
                                          ${list.createDate}
                                      </a>
                                  </div>
                              </div>
                              <div class="article-doc comment-doc">
                                  <p>${list.content}</p>
                              </div>
                              <div class="article-util">
                                  <ul class="article-util-list">
                                      <li>
                                          <a class="link-modify-article" href="/questions/${list.answerId}/answers/${list.writer}/form">수정</a>
                                      </li>
                                      <li>
                                          <button type="button" data-question-id="${board.questionId}" data-answer-id="${list.answerId}" class="link-delete-article">삭제</button>
                                      </li>
                                  </ul>
                              </div>
                              </article>
                          </c:forEach>
                          <form class="submit-write" id="answerForm">
                          <input type="hidden" name="questionId" value="<c:out value='${board.questionId}'/>"/>
                          <input type="hidden" name="writer" value="customer"/>
                              <div class="form-group" style="padding:14px;">
                                  <textarea class="form-control" name="content" placeholder="Update your status"></textarea>
                              </div>
                              <button class="btn btn-success pull-right" onclick="javascript:addAnswer();" type="button">Post</button>
                              <div class="clearfix" />
                          </form>
                      </div>
                  </div>
              </div>
          </div>
        </div>
    </div>
</div>
<script type="text/template" id="answerTemplate">
      <article class="article">
          <div class="article-header">
              <div class="article-header-thumb">
                  <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
              </div>
              <div class="article-header-text">
                  <a href="/users/{0}/자바지기" class="article-author-name">{0}</a>
                  <a href="#answer-1434" class="article-header-time" title="퍼머링크">
                      {1}
                  </a>
              </div>
          </div>
          <div class="article-doc comment-doc">
              <p>{2}</p>
          </div>
          <div class="article-util">
              <ul class="article-util-list">
                  <li>
                      <a class="link-modify-article" href="/questions/{3}/answers/{0}/form">수정</a>
                  </li>
                  <li>
                      <button type="button" data-answer-id="{3}" data-question-id="${board.questionId}" class="link-delete-article">삭제</button>
                  </li>
              </ul>
          </div>
      </article>
</script>
<%@ include file="/include/footer.jspf" %>
	</body>
</html>
