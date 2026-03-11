<%--
  Created by IntelliJ IDEA.
  User: vincenzo
  Date: 06/07/24
  Time: 15:22
  To change this template use File | Settings | File Templates.
--%>
<div class="footer">
    <div class="footer-top">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/pages/index.jsp">
                <img src="${pageContext.request.contextPath}/assets/img/logo/logo.png" alt="Logo" style="width: 100px; height: auto;">
            </a>
        </div>
        <div class="social-media">
            <a href="https://www.facebook.com">
                <i class="fab fa-facebook icon"></i>
                Facebook
            </a>
            <a href="https://www.instagram.com">
                <i class="fab fa-instagram icon"></i>
                Instagram
            </a>
        </div>
        <div class="brands">
            <a href="${pageContext.request.contextPath}/category?category=Fumetti&skip=0&limit=9">
                <img src="${pageContext.request.contextPath}/assets/img/fumetti.jpg" alt="Fumetti">
            </a>
            <a href="${pageContext.request.contextPath}/category?category=Manga&skip=0&limit=9">
                <img src="${pageContext.request.contextPath}/assets/img/manga.jpg" alt="Manga">
            </a>
            <a href="${pageContext.request.contextPath}/category?category=Gadget&skip=0&limit=9">
                <img src="${pageContext.request.contextPath}/assets/img/gadget.jpg" alt="Gadget">
            </a>
        </div>
    </div>
    <div class="footer-bottom">
        <div class="footer-links">
            <a href="${pageContext.request.contextPath}/pages/index.jsp">
                <i class="fas fa-home icon"></i>
                HOME
            </a>
        </div>
        <div class="copyright">
            © Copyright 2024 | ComicShop
        </div>
    </div>
</div>
