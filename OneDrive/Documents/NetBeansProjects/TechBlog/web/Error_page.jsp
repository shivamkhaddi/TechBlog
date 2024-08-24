
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isErrorPage="true" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sorry ! Something went wrong</title>
        
        <!-- css -->
    <link href="CSS/myStyle.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        .banner-background{
         clip-path: polygon(30% 0%, 70% 0%, 100% 0, 100% 93%, 67% 99%, 33% 94%, 0 100%, 0 0);
        }
    </style>
    </head>
    <body>
        
        <div class="container text-center">
            <img src="images/message.png" class="img-fluid" >
            <h3 class="display-3"> Sorry! Something went wrong...</h3>
              <%=exception %>
            <a href="index.jsp" class="btn primary-background btn-lg text-white mt-3">Home</a>
        </div>  
        
        
    </body>
</html>
