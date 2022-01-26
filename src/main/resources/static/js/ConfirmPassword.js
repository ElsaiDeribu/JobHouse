
let confirmPassword = document.getElementById("confirmPassword");

confirmPassword.onfocus = function(){
    let validate = document.getElementById("validate");
    let password = document.getElementById("password");
    if (confirmPassword.value != password.value){
        validate.textContent = "Passwords doesn't match";
    }
    else{
        validate.textContent = "valid";
    }
}
