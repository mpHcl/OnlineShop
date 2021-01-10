function closeBottom() {
    var bottom = document.getElementById("bottomid");
    bottom.parentNode.removeChild(bottom);
}

function imgButton(new_src) {
    var img = document.getElementById("big_image")
    img.src=new_src;
}