
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
        
        <link href="CSS/myStyle.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.css">

        <style>
           .banner-background{
            clip-path: polygon(30% 0%, 70% 0%, 100% 0, 100% 93%, 67% 99%, 33% 94%, 0 100%, 0 0);
        }
         </style>
    </head>
    <body>
        
        <%@include file="normal_navbar.jsp" %>
        
        <main class="primary-background p-5 banner-background" style="padding-bottom: 80px;">
            <div class="container">
                <div class="col-md-6 offset-md-3">
                    <div class="card">
                        <div class="card-header text-center primary-background text-white">
                            <span class="fa fa-3x fa-user-circle"></span>
                            <br>
                            Register here
                        </div>
                        <div class="card-body">
                            <form id="reg-form" action="RegisterServlet" method="POST">
                                 <div class="form-group">
                                    <label for="user_name">User Name</label>
                                    <input name="user_name"  type="text" class="form-control" id="user_name" aria-describedby="emailHelp" placeholder="Enter Name">
                                   
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputEmail1">Email address</label>
                                    <input name="user_email" type="email" class="form-control" id="user_email" aria-describedby="emailHelp" placeholder="Enter email">
                                    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputPassword1">Password</label>
                                    <input name="user_password" type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
                                </div>
                                <div class="form-group">
                                    <label for="gender">Select gender</label>
                                    <br>
                                    <input type="radio"  id="male" name="gender" value="Male" >Male
                                    <input type="radio"   id="female" name="gender" value="Female">Female
                                </div>
                                <div class="form-group">
                                    
                                    <textarea name="about" class="form-control" id=""  rows="3" placeholder="Enter something about yourself"></textarea>
                                    
                                </div>
                                <div class="form-check">
                                    <input name="check" type="checkbox" class="form-check-input" id="exampleCheck1">
                                    <label class="form-check-label" for="exampleCheck1">Agree terms and conditions</label>
                                </div>
                                <br>
                                <div class="container text-center" id="loader" style="display:none;">
                                <span class="fa fa-refresh fa-spin fa-4x"></span>
                                <h4>Please wait...</h4>
                                </div>
                                <button id="submit-btn" type="submit" class="btn btn-primary ">Submit</button>
                            </form>
                        </div>
                       
                    </div>
                    
                    
                </div>
            </div>
        </main>
        
        <!--js-->
         <script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
         <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
         <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
         <script src="JS/myJS.js" type="text/javascript"></script>
         <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>

         <script>
    $(document).ready(function(){
        console.log("Document loaded...");

        $('#reg-form').on('submit', function(event){
            event.preventDefault(); // Prevent default form submission

            // Create FormData object from the form
            let form = new FormData(this);
            $("#submit-btn").hide();
            $("#loader").show();
            
            // Send data to RegisterServlet via AJAX
            $.ajax({
                url: "RegisterServlet",
                type: "POST",
                data: form,
                processData: false,  // Prevent jQuery from processing the data
                contentType: false,  // Prevent jQuery from setting contentType
                success: function(data, textStatus, jqXHR){
                    console.log("Success:", data);
                    $("#submit-btn").show();
                    $("#loader").hide();
                    
                    // Assuming RegisterServlet returns a text response
                    if (data.trim() === "Registration successful") {
                        // Redirect to login page after registration success
                        swal("Registered successfully. Redirecting to login page...")
                            .then(function(){
                                window.location = "Login_page.jsp";
                            });
                    } else {
                        // Display error message returned from server
                        swal(data);
                    }
                },
                error: function(jqXHR, textStatus, errorThrown){
                    $("#submit-btn").show();
                    $("#loader").hide();
                    
                    // Display generic error message
                    swal("Something went wrong. Please try again.");
                }
            });
        });
    });
         </script>



    </body>
</html>
