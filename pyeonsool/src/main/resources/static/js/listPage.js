function addBestSlidingEvent(){
    const bestAlcoholCards = document.querySelector(".best-list__ul");
    const IMAGE_COUNT = bestAlcoholCards.childElementCount;
    const SLIDE_DISTANCE = 305;
    const leftButton = document.querySelector(".best-button__left");
    const rightButton = document.querySelector(".best-button__right");

    // const bestSlideController = new BestSlideController(10,bestAlcoholCards,IMAGE_COUNT,SLIDE_DISTANCE,leftButton,rightButton);
    const bestSlideController = new SlideController(0, bestAlcoholCards,IMAGE_COUNT, 1, SLIDE_DISTANCE, leftButton,rightButton);
    
    leftButton.addEventListener("click",function(event){
        bestSlideController.slide(event.target, 4);
    });
    rightButton.addEventListener("click",function(event){
        bestSlideController.slide(event.target, 4);
    });
}
function findType(){
    const urlParams = new URL(location.href).searchParams;

    const type = urlParams.get('alcoholType');

    console.log(type);
    let typeTxt;
    if(type=="SOJU"){
        typeTxt = "소주";
    }
     else if(type=="BEER"){
        typeTxt = "맥주";
    }
    else if(type=="WINE"){
         typeTxt = "와인";
    }
    console.log("typetxt = "+typeTxt);
    document.getElementById('alcoholTitle').innerHTML=typeTxt;

}
 findType();
addBestSlidingEvent();