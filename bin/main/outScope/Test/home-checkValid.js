
let url = "http://localhost:8080/Youtube_Mimic_JS/";

(async function checkSession(){
    if(window.localStorage.getItem("jwtToken") == null){
        window.location.href = `${url}welcome.html`
    }
})();