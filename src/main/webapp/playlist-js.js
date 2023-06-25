let createPlayListInput = document.getElementById("create-playlist-input-field")
let createPlayListBtn = document.getElementById("create-playlist-btn")

let homeVideoSearchInput = document.getElementById("home-video-search-input")
let homeSearchBtn = document.getElementById("home-search-btn")
let homeSearchResults = document.getElementById("home-results")

let playListVideoSearchInput = document.getElementById("playlist-video-search-input")
let searchResultsInPlayList = document.getElementById("search-results-in-playlist")

let playListModal = document.getElementById("playlist-modal")
let logout = document.getElementById("logout-btn")


let deletePlayListBtn = document.getElementById("delete-playlist-btn")

var playLists = [];
var currentPlayListName = "";
var currentPlayList= [];
var playListSearchResults = [];
let currentPlayListVideoTitle = [];

function getCurrentPlayListName(){
    let searchParams = new URLSearchParams(window.location.search);
    currentPlayListName = searchParams.get("playListName")
}

getCurrentPlayListName()


function goHome(){
    window.location.href = `${url}home.html`
}

createPlayListInput.addEventListener('keyup',checkCreatePlayList)
createPlayListBtn.addEventListener('click',function(){
    createPlayListInput.value = ""
})

// searchResultsInPlayList.addEventListener('onclick',function(){
//     playListVideoSearchInput.value = ""
// })

// playListVideoSearchInput.addEventListener('keyup',function(){
//     if(playListVideoSearchInput.value.length > 2 && playListVideoSearchInput.value.toString().trim()!="") {
//         searchResultsInPlayList.style.display = "block"
//         requests.getPlayListVideo()
//     }else {
//         searchResultsInPlayList.innerHTML = "";
//         searchResultsInPlayList.style.display = "none"
//     }
// })

function showPlayLists(){
    let playLists = document.getElementById("select-playlist")
    if(window.getComputedStyle(playLists).display == "block"){
        playLists.style.display = "none"
    }else{
        playLists.style.display = "block"
    }
    
    
}
// playListModal.addEventListener('click',function(){
//     searchResultsInPlayList.style.display = "none"
// })

// async function checkAndAddItemToPlayList(videoTitle){

//     console.log(videoTitle,currentPlayList)
//     if(currentPlayList.length == 0 ) {
//         requests.addItemToPlayList(videoTitle)
//     }else{
//         for(x in currentPlayList){
//             console.log(currentPlayList[x].videoTitle.toString(), videoTitle.toString())
//             if(currentPlayList[x].videoTitle == videoTitle){

//                 animateAlreadyFound()
//                 return;
//             }
//          }
//          requests.addItemToPlayList(videoTitle)
//          return new Promise(resolve => resolve())
    
//     }
    // if(currentPlayList[].includes(videoTitle)){
    //     console.log("saf")
    //     animateAlreadyFound()
    // }else{
    //     requests.addItemToPlayList(videoTitle)
    // }




homeVideoSearchInput.addEventListener('keyup',function(){

    let searchResultsInPlayList = document.getElementById("search-results-in-playlist")
    let searchKeyword = homeVideoSearchInput.value
    if( searchKeyword.length>=3){

        searchResultsInPlayList.style.display = "block"
        getSearchResults(searchKeyword)
    }else{
        searchResultsInPlayList.value = ""
        searchResultsInPlayList.style.display = "none"
    }
})




async function getSearchResults(searchKeyword){
    
      let response = await fetch(`${url}SearchController`,{
            headers : {
                "Content-Type" : "application/json",
                "Authorization" : `bearer ${jwtToken}`
            },
            method : "POST",
            Accept : "/*",
            body : JSON.stringify({
                "searchKeyword" : `${searchKeyword}`
            })
    })

    let writeDocument = document.getElementById("search-results-in-playlist")

    let writeData = "";
    if(response.status == 404){
    
         writeData = "<h6>No Video Found</h6>";

    }else{

        let data = await response.json()

        playListSearchResults = data

    ///need some cahnges
    for(x in data){
            console.log(data[x].videoTitle)
                if(!currentPlayListVideoTitle.includes(data[x].videoTitle)){
                    writeData = writeData+`<div><button    value="" >${data[x].videoTitle}<span onclick='clearPlayListSearchInput();requests.addItemToPlayList(\"${data[x].videoTitle}\")' class="add-to-playlist" >Add To PlayList</span></button></div>`
                }else{
                    writeData = writeData+`<div><button    value="" >${data[x].videoTitle}<span onclick='clearPlayListSearchInput();requests.removeItemFromPlayList(\"${data[x].videoTitle}\")' class="remove-from-playlist" >Remove From PlayList</span></button></div>`
                }
               
                // writeData = writeData+"<div><button onclick=checkAndAddItemToPlayList(\'"+`${data[x].videoTitle}`+"\')  >"+`${data[x].videoTitle}`+"</button></div>"
        
            }
    }

    writeDocument.innerHTML = writeData
    
}



function animateAlreadyFound(){
    let element = document.getElementById("already-found-item")
    element.setAttribute("class","animate")
    // element.classList.add("animate")
    setTimeout(removeAnimate,4000,element)
}

function removeAnimate(element){
    
    element.classList.remove("animate")   
}



function removeItem(videoTitle){

    for(x in currentPlayList){
        if(currentPlayList[x].videoTitle == videoTitle){
            console.log("removing ",currentPlayList[x].videoTitle)
            currentPlayList.splice(x,1)
        }
    }
    return currentPlayList
}



function addItem(videoTitle){
    for(x in playListSearchResults){
        if(playListSearchResults[x].videoTitle == videoTitle){
            currentPlayList.push(playListSearchResults[x])
        }
    }
    return currentPlayList
}


function checkCreatePlayList(){
    if(createPlayListInput.value.length >= 3 && !containsSamePlayList()) createPlayListBtn.disabled = false
    else createPlayListBtn.disabled = true
}

function containsSamePlayList(){
    if(playLists.includes(createPlayListInput.value.toString().trim())) return true;
    else return false
}

