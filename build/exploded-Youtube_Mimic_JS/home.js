

let createPlayListInput = document.getElementById("create-playlist")
let createPlayListBtn = document.getElementById("create-playlist-btn")


checkCreatePlayListInput()

function checkCreatePlayListInput(){
    
  let newPlayListName = document.getElementById("create-playlist").value
    if(newPlayListName.length >= 3 && newPlayListName.trim()!=""){
        createPlayListBtn.disabled = false
    }else{
        createPlayListBtn.disabled = true
    }
}

