console.log("this is script file")

const toggleSidebar = () => {
    const $sidebar = $(".sidebar");
    const $content = $(".content");

    if ($sidebar.is(":visible")) {
        $sidebar.hide();
        $content.css("margin-left", "0");
    } else {
        $sidebar.show();
        $content.css("margin-left", "20%");// Adjust with the appropriate width
    }
};