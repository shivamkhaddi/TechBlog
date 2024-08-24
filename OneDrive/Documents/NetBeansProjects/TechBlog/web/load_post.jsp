<%@page import="com.tech.blog.entities.User"%>
<%@page import="com.tech.blog.dao.LikeDao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tech.blog.entities.Post"%>
<%@page import="com.tech.blog.dao.PostDao"%>
<%@page import="com.tech.blog.helper.ConnectionProvider"%>

<div class="row">

<% 
   User user =(User)session.getAttribute("currentUser");
    
   PostDao d= new PostDao(ConnectionProvider.getConnection());
   
   int cid =Integer.parseInt(request.getParameter("cid"));
   
   ArrayList<Post> posts = null;
   if(cid==0){
      posts=d.getAllPosts();
    }else{
      posts =d.getPostByCid(cid);
    }
   
    
    if(posts.size()==0){
       out.println("<h3 class='display-3 text-center'>No Posts in this category...</h3>");
       return;
    }
   
  
   
for(Post p:posts){
%>

<div class="col-md-6 mt-2">
    <div class="card">
        <img class="card-img-top" src="blog_pics/<%= p.getpPic() %>" alt="Card image cap"> 
        <div class="card-body">
            <b><%= p.getpTitle()  %></b>
            <p><%= p.getpContent()  %></p>
       <!-- <pre><%= p.getpCode() %></pre> -->
        </div>
        <div class="card-footer primary-background text-center" >
            <% 
             LikeDao ld = new LikeDao(ConnectionProvider.getConnection());
            %>
           <a href="#!" onclick="doLike(<%= p.getPid() %>, <%= user.getId() %>)" class="btn btn-outline-light btn-sm"><i class="fa fa-thumbs-o-up"></i><span class="like-counter"><%= ld.countLikeOnPost(p.getPid()) %></span></a>
            <a href="#!" class="btn btn-outline-light btn-sm"><i class="fa fa-commenting-o" ></i><span>20</span></a>
            <a href="show_blog_page.jsp?post_id=<%= p.getPid() %>" class="btn btn-outline-light btn-sm">Read More...</a>
        </div>
    </div>
</div>

<%
    }
%>

</div>