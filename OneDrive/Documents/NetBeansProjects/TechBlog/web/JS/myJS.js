function doLike(pid, userId) {
    console.log(pid + "," + userId);
    
    const d = {
        userId: userId,
        pid: pid,
        operation: 'like'
    };
    
    $.ajax({
        url: "LikeServlet", // URL of your servlet endpoint
        
        data: d,         // Data to be sent to the server
        success: function(data, textStatus, jqXHR) {
            console.log(data); // Log response data to console on success
            // You can perform further actions here based on the response
            if(data.trim()=='true'){
                let c =$(".like-counter").html();
                c++;
                $(".like-counter").html(c);
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error("Error:", textStatus, errorThrown);
            // Handle errors, log or display an error message to the user
        }
    });
}



